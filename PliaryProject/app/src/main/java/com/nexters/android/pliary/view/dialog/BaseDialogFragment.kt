package com.nexters.android.pliary.view.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment(), View.OnClickListener {

    abstract fun getLayoutRes(): Int
    abstract fun onDismiss()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutRes(), container)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return view
    }



    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        onDismiss()
        super.onDismiss(dialog)
    }

    override fun onClick(v: View?) {
        dialogDismiss()
    }

    protected fun dialogDismiss() {
        activity?.let{
            val isDestroy = it.isDestroyed || it.isFinishing
            if(!isDestroy) {
                dialog?.run {
                    if (isShowing) dismiss()
                }
            }

        }
    }
}