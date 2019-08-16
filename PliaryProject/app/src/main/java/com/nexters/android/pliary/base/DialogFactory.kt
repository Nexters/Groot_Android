package com.nexters.android.pliary.base

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.nexters.android.pliary.R


object DialogFactory {
    fun showHouseHoldConfirmDlg(context: Context) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.number_picker, null)

        val dialog = MaterialDialog.Builder(context)
            .autoDismiss(false)
            .customView(view, false)
            .build()

        view.findViewById<ImageView>(R.id.ivClose).setOnClickListener{ dialog?.dismiss() }
        val picker = view.findViewById<NumberPicker>(R.id.npDate).apply {
            minValue = 1
            maxValue = 60
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        dialog.show()

        val value = picker.value
        Toast.makeText(context, "$value 일 후 물주기", Toast.LENGTH_SHORT).show()
    }
}