package com.base.myapplication.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.pop_up.*
import com.base.myapplication.R

class PopUpDialog(val activity: Context, val title: String, val message: String, val button: String) : Dialog(activity) {

//    val confirmListener : ((PopUpDialog)->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setContentView(R.layout.pop_up)

        initView()
        initListeners()
    }

    private fun initView() {
        title.let { popup_title.text = it }
        message.let { popup_message.text = it }
        button.let { popup_button_confirm_text.text = it }
    }

    private fun initListeners() {
        popup_button_confirm.setOnClickListener{
            dismiss()
        }
    }

    fun myToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}