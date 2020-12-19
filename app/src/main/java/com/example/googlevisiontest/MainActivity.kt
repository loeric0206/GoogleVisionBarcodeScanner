package com.example.googlevisiontest

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener, SurfaceHolder.Callback,
    Detector.Processor<Barcode> {
    private var count = 0
    private var cameraView: SurfaceView? = null
    private var cameraSource: CameraSource? = null
    private var barcodeDetector: BarcodeDetector? = null
    private var textResult: TextView? = null
    private var rescanBtn: Button? = null
    private val countFirst = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        cameraView = findViewById(R.id.view_qr_code)
        rescanBtn = findViewById(R.id.btn_rescan)
        textResult = findViewById(R.id.text_result)
        rescanBtn?.setOnClickListener(this)

        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(640, 480)
            .build()
        cameraView?.holder?.addCallback(this)
        barcodeDetector?.setProcessor(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_rescan -> {
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        try {
            cameraSource?.start(cameraView?.holder)
        } catch (ie: IOException) {
            Log.e("CAMERA SOURCE", ie.message)

        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        cameraSource!!.stop()
    }

    override fun release() {
    }

    override fun receiveDetections(detections: Detector.Detections<Barcode>) {
        val barcodes: SparseArray<Barcode> = detections.detectedItems
        Log.e("size", barcodes.size().toString() + "")

        if (barcodes.size() == 1) {
            count += 1
            if (count == countFirst) {
                val tv = barcodes.valueAt(0).displayValue
                runOnUiThread(Runnable {
                    this.textResult?.text = tv
                })
            }
        }
    }
}
