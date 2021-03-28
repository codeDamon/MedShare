package com.codedamon.medshare.chemist.ui.BoxReceivePage

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.chemist.adapter.MedicineReceiveRvAdapter
import com.codedamon.medshare.chemist.model.ChemistTransaction
import com.codedamon.medshare.model.Transaction
import com.codedamon.medshare.model.medicine.Medicine
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class BoxReceiveFragment : Fragment(), MedicineReceiveRvAdapter.MedReceiveBoxInterface {

    companion object {
        fun newInstance() = BoxReceiveFragment()
    }

    private lateinit var viewModel: BoxReceiveViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicineReceiveRvAdapter
    private lateinit var list: ArrayList<Medicine>
    private lateinit var navController: NavController
    lateinit var loadingDialog: Dialog

    private val db = Firebase.firestore

    val args: BoxReceiveFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        list = ArrayList()
        return inflater.inflate(R.layout.box_receive_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoxReceiveViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        args.list?.let { extractStringToObj(it) }


        view.findViewById<FloatingActionButton>(R.id.approve).setOnClickListener{

            val alertDialog = AlertDialog.Builder(
                context
            )
            alertDialog.setMessage("Enter Donor Name")

            val input = EditText(context)
            alertDialog.setView(input)

            alertDialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, _ ->
                if(input.text.isNullOrEmpty()) {
                    Toast.makeText(context,"Failed! Please enter donor name", Toast.LENGTH_SHORT).show()
                }else{
                    showLoadingDialog()
                    approveMedicines(input.text.trim().toString())
                    Handler().postDelayed({

                        loadingDialog.dismiss()
                        navController.navigate(R.id.action_boxReceiveFragment_to_homeChemistFragment)
                    }, 1000)
                }
            })
            alertDialog.setNegativeButton("Cancel",DialogInterface.OnClickListener{ dialog, which ->
                dialog.cancel()
            })
            alertDialog.show()

        }

        recyclerView = view.findViewById(R.id.box_receive_rv)
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerView.setHasFixedSize(true)
        adapter = MedicineReceiveRvAdapter(requireContext(), list, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun approveMedicines(user: String){
        val sdf = SimpleDateFormat("dd-MMMM,yyyy-HH:mm")
        val c: Calendar = Calendar.getInstance()

        val dateTime = sdf.format(c.time)

        val hearts = calculateHeart()
        val trans = Transaction("Local Chemist", hearts, calculatePoints(hearts), dateTime)
        addTranToFirebase(user, trans, dateTime)
    }

    private fun calculateHeart():Int{
        var hearts : Int = 0
        for (med in list){
            hearts+=((med.quantity*med.price)*0.2).roundToInt()
        }
        return hearts
    }

    private fun calculatePoints(hearts: Int):Int{
        return when{
            (hearts <20) ->
                100
            (hearts<50) ->
                200
            (hearts<100)->
                500
            else ->
                700
        }
    }

    private fun addTranToFirebase(user: String, transaction: Transaction, date: String) {

        val rootNode = FirebaseDatabase.getInstance();
        val tranRef = rootNode.getReference("user_transactions")
        val chemistTranRef = rootNode.getReference("chemist_transactions")

        tranRef.child(user).child(date).setValue(transaction)


        val chemistTransaction = ChemistTransaction(user,transaction)
        chemistTranRef.child("Local Chemist").setValue(chemistTransaction)

        updateDonorPoints(user,transaction.points)
    }

    private fun updateDonorPoints(user:String,newPoints:Int){

        var points = 0
        val docRef = db.collection("donors").document(user)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG11", "DocumentSnapshot data: ${document.data}")
                    points=newPoints+document["points"].toString().toInt()
                    db.collection("donors").document(user)
                        .update(mapOf(
                            "points" to points,
                        ))

                } else {
                    Log.d("TAG11", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG11", "get failed with ", exception)
            }


    }

    private fun showLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        loadingDialog.setCancelable(true)

        loadingDialog.setContentView(R.layout.dialog_loading_success)
        loadingDialog.show()
    }

    private fun extractStringToObj(s: String) {

        if(!isJson(s)){
            Toast.makeText(context, "Invalid QR code", Toast.LENGTH_SHORT).show()
            navController.navigateUp()
            return
        }

        val convertedJsonArray: JsonArray = Gson().fromJson(s, JsonArray::class.java)

        for (jsonObject in convertedJsonArray) {
            val obj = jsonObject.asString
            val convertedObject: Medicine = Gson().fromJson(obj, Medicine::class.java)
            list.add(convertedObject)
            Log.d("Parse", convertedObject.name)
        }
    }

    private fun isJson(Json: String): Boolean {
        try {
            JSONObject(Json)
        } catch (ex: JSONException) {
            try {
                JSONArray(Json)
            } catch (ex1: JSONException) {
                return false
            }
        }
        return true
    }

    override fun onExpandClicked() {

    }

    override fun deleteMed(position: Int) {
        list.remove(list[position])
        adapter.notifyDataSetChanged()
    }


}