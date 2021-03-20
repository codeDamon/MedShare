package com.codedamon.medshare.chemist.ui.homePageChemist

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import java.security.Permission
import java.util.jar.Manifest

class HomeChemistFragment : Fragment() {

    companion object {
        fun newInstance() = HomeChemistFragment()
    }

    private lateinit var viewModel: HomeChemistViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_chemist_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeChemistViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.scan_qr).setOnClickListener {
            openScanner()
        }
    }

    private fun openScanner() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            navController.navigate(R.id.action_homeChemistFragment_to_QRScannerFragment)
        } else {

            if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                /*Toast.makeText(
                    context,
                    "Camera Permission required to scan QR code",
                    Toast.LENGTH_SHORT
                ).show()*/
            }

            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 100){

            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                navController.navigate(R.id.action_homeChemistFragment_to_QRScannerFragment)
            else {
                Toast.makeText(context, "Camera Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}