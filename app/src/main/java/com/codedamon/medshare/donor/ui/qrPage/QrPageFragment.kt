package com.codedamon.medshare.donor.ui.qrPage

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.navigation.fragment.navArgs
import com.codedamon.medshare.R
import com.codedamon.medshare.chemist.ui.BoxReceivePage.BoxReceiveFragmentArgs
import com.google.zxing.WriterException

class QrPageFragment : Fragment() {

    val args: QrPageFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.qr_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var medStr : String? = ""
        medStr = args.medicinesList

        val qrImg : ImageView = view.findViewById(R.id.qr)
        val qrgEncoder = medStr?.let {  QRGEncoder(medStr, null, QRGContents.Type.TEXT, 1000)}
        qrgEncoder?.colorBlack = Color.WHITE
        qrgEncoder?.colorWhite = Color.BLACK
        try {
            // Getting QR-Code as Bitmap
            val bitmap = qrgEncoder?.bitmap
            // Setting Bitmap to ImageView
            qrImg.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v("QR", e.toString())
        }
    }
}