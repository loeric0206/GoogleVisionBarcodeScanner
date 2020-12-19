package com.example.googlevisiontest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class FirstPage: AppCompatActivity(), View.OnClickListener {
    private var nextBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page)
        nextBtn = findViewById(R.id.btn_next)
        nextBtn?.setOnClickListener(this)
        getPermission()
    }

    private fun getPermission() {
        val cameraPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    (this as Activity?)!!,
                    Manifest.permission.CAMERA
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    (this as Activity?)!!, arrayOf(Manifest.permission.CAMERA),
                    0
                )
            }
        }    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_next -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}