package com.nexters.android.pliary.view.util

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.afollestad.materialdialogs.MaterialDialog
import com.nexters.android.pliary.R


object DialogFactory {

    interface WateringDialogListener{
        fun onWatering()
        fun onDelay(day: Int)
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
        val wateringContent = view.findViewById<ConstraintLayout>(R.id.clContent)
        val delayContent = view.findViewById<ConstraintLayout>(R.id.clDelayContent)
        view.findViewById<TextView>(R.id.tvDelay).setOnClickListener {
            wateringContent.isVisible = false
            delayContent.isVisible = true
        }

        view.findViewById<TextView>(R.id.tvWatering).setOnClickListener {
            listener.onWatering()
            dialog?.dismiss()
        }
        val done = view.findViewById<TextView>(R.id.tvDelayDone)
        val picker = view.findViewById<NumberPicker>(R.id.npDate).apply {
            minValue = 1
            maxValue = 60
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            setOnValueChangedListener { picker, oldVal, newVal ->
                done.isEnabled = true
            }
        }
        done.setOnClickListener {
            listener.onDelay(picker.value)
            dialog?.dismiss()
        }
        dialog.show()

        /*val value = picker.value
        Toast.makeText(context, "$value 일 후 물주기", Toast.LENGTH_SHORT).show()*/
    }
}