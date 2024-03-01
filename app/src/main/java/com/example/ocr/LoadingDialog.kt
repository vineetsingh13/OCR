package com.example.ocr

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity

class LoadingDialog(val activity: FragmentActivity) {

    private lateinit var isdialog: AlertDialog

    fun startLoading(){
        val inflater=activity.layoutInflater
        val dialogView=inflater.inflate(R.layout.loading_layout,null)
        val builder= AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog=builder.create()
        isdialog.show()
    }

    fun isDismiss(){
        isdialog.dismiss()
    }
}