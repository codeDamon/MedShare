package com.codedamon.medshare.chemist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.codedamon.medshare.R
import com.codedamon.medshare.chemist.ui.BoxReceivePage.BoxReceiveFragmentArgs
import com.codedamon.medshare.donor.ui.boxDisplayPage.BoxDisplayFragmentDirections
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class QRScannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.q_r_scanner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        navController = Navigation.findNavController(view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                //Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()

                if (!isJson(it.text)) {
                    Toast.makeText(activity, "Invalid QR code", Toast.LENGTH_SHORT).show()
                } else {
                    val action =
                        QRScannerFragmentDirections.actionQRScannerFragmentToBoxReceiveFragment(it.text)
                    view.findNavController().navigate(action)
                }
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
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

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}