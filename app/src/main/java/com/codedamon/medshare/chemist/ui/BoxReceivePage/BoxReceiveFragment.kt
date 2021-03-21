package com.codedamon.medshare.chemist.ui.BoxReceivePage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.codedamon.medshare.model.medicine.Medicine
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class BoxReceiveFragment : Fragment(), MedicineReceiveRvAdapter.MedReceiveBoxInterface {

    companion object {
        fun newInstance() = BoxReceiveFragment()
    }

    private lateinit var viewModel: BoxReceiveViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicineReceiveRvAdapter
    private lateinit var list: ArrayList<Medicine>
    private lateinit var navController: NavController

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



        recyclerView = view.findViewById(R.id.box_receive_rv)
        initRecycleView()
    }

    private fun initRecycleView() {
        recyclerView.setHasFixedSize(true)
        initList()
        adapter = MedicineReceiveRvAdapter(requireContext(), list, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun initList() {

        val med = Medicine("Paracetamol", 20.5, 10, "20-01-2022")
        list.add(med)
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

    fun isJson(Json: String): Boolean {
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

}