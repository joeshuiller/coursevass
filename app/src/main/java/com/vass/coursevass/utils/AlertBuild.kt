package com.vass.coursevass.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.vass.coursevass.R

class AlertBuild(context: Context): AlertService {
    private  val context =  context
    var dialog: Dialog? = null //obj
    override fun createAlert(messages: String, title: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        //set message for alert dialog
        val viewfinal = View.inflate(context, R.layout.alert, null);
        val tvTitle: TextView = viewfinal.findViewById(R.id.title_alert)
        val tvDetail: TextView = viewfinal.findViewById(R.id.text_alert)
        val btDone: Button = viewfinal.findViewById(R.id.button_alert)
        tvDetail.text = messages
        tvTitle.text = title
        btDone.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
        })
        builder.setView(viewfinal)
        // create and show the alert dialog
        dialog = builder.create()
        dialog.show()
    }
    override fun displayLoadingWithText(text: String?, cancelable: Boolean) {
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_loading_screen)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(cancelable)
        val textView = dialog!!.findViewById<TextView>(R.id.text)
        textView.text = text
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    override fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}