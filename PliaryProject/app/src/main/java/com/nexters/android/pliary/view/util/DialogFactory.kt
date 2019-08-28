package com.nexters.android.pliary.view.util

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.nexters.android.pliary.R


object DialogFactory {

    interface WateringDialogListener{
        fun onWatering()
        fun onDelay(day: Long)
    }

    interface DialogListener{
        fun onPositive()
        fun onNegative()
    }

    fun showWateringDialog(context: Context, listener: WateringDialogListener) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dlg_watering_layout, null)

        val dialog = MaterialDialog.Builder(context)
            .autoDismiss(false)
            .customView(view, false)
            .build()
            .apply { window?.setLayout(310.toDp, WindowManager.LayoutParams.WRAP_CONTENT) }

        view.findViewById<ImageView>(R.id.ivClose).setOnClickListener{ dialog?.dismiss() }
        val picker = view.findViewById<NumberPicker>(R.id.npDate).apply {
            minValue = 1
            maxValue = 60
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        dialog.show()

        view.findViewById<TextView>(R.id.tvDelay).setOnClickListener {

        }

        view.findViewById<TextView>(R.id.tvWatering).setOnClickListener {
            listener.onWatering()
        }

        val value = picker.value
        Toast.makeText(context, "$value 일 후 물주기", Toast.LENGTH_SHORT).show()
    }
}