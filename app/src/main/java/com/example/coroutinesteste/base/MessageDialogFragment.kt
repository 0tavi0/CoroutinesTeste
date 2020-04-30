package com.example.coroutinesteste.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.example.coroutinesteste.R
import kotlinx.android.synthetic.main.dialog_fragment_message.view.*

class MessageDialogFragment : DialogFragment() {
    var title: String = ""
    var message: String = ""
    var onDismiss: View.OnClickListener = View.OnClickListener { }

    @DrawableRes
    var icon: Int = R.drawable.ic_error

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_fragment_message, container, false)
        isCancelable = false
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.iv_error.setImageDrawable(requireContext().getDrawable(icon))
        }
        view.tv_title_error.text = title
        view.tv_message_error.text = message
        view.bt_close_error.setOnClickListener {
            this.dismiss()
            onDismiss.onClick(it)
        }
    }
}