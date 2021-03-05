package com.base.myapplication.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.base.myapplication.R

abstract class BaseActivity(layout: Int) : AppCompatActivity(layout), View.OnClickListener {

    private var toolbar: Toolbar? = null
    private var loadingDialog: Dialog? = null
    private var popupDialog: PopUpDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun setContentView(view: View?, layoutParams: ViewGroup.LayoutParams?) {
        super.setContentView(view, layoutParams)
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        if (toolbar != null) {
            setSupportActionBar(toolbar)
            toolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
            toolbar!!.title = ""
        }
    }

    fun setDisplayHome(showBackButton: Boolean) {
        val actionBar = supportActionBar ?: return
        actionBar.setHomeButtonEnabled(showBackButton)
        actionBar.setDisplayHomeAsUpEnabled(showBackButton)
    }

    fun setHomeAsUpIndicator(@DrawableRes drawable: Int) {
        val actionBar = supportActionBar ?: return
        actionBar.setHomeAsUpIndicator(drawable)
    }

    fun setDisplayLogo(@DrawableRes drawable: Int) {
        val actionBar = supportActionBar ?: return
        actionBar.setLogo(drawable)
    }

    fun setIcon(@DrawableRes drawable: Int) {
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayShowTitleEnabled(false)
        actionBar.setIcon(ContextCompat.getDrawable(this, drawable))
        actionBar.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    fun setTitle(title: String?) {
        val toolbarTitle = findViewById<TextView>(R.id.tvToolbarName)
//        toolbarTitle!!.typeface = Typeface.createFromAsset(this.assets, "segoe_ui.ttf")
        toolbarTitle.text = title
    }

    fun setSubtitle(subtitle: String?) {
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayShowTitleEnabled(!TextUtils.isEmpty(subtitle))
        actionBar.subtitle = subtitle
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupToolbar()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        setupToolbar()
    }

    override fun onPause() {
        super.onPause()
        loadingDialog?.dismiss()
        popupDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
        popupDialog?.dismiss()
    }

    fun startLoading() {
        if (loadingDialog == null) {
            initLoading()
            loadingDialog?.show()
        } else if (!loadingDialog?.isShowing!!) {
            loadingDialog?.show()
        }
    }

    fun finishedLoading() = loadingDialog?.dismiss()

    fun failureTask(message: String) = generateDialog("Error", message, "OK")

    fun warningTask(message: String) = generateDialog("Warning", message, "OK")

    fun infoTask(message: String) = generateDialog("Info", message, "OK")

    fun toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun toastLong(message: String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun initLoading() {
        loadingDialog = Dialog(this)
        loadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog?.setContentView(LayoutInflater.from(this).inflate(R.layout.custom_loading_dialog, null))
        loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog?.setCancelable(false)
    }

    private fun initDialog(title: String, message: String, button: String) {
        popupDialog = PopUpDialog(this, title, message, button)
        popupDialog?.show()
    }

    private fun generateDialog(title: String, message: String, button: String) {
        if (popupDialog == null) {
            initDialog(title, message, button)
        } else if (popupDialog?.isShowing!!) {
            popupDialog?.dismiss()
            initDialog(title, message, button)
        }
    }

    override fun onClick(p0: View?) {}

    fun loadImage(view: ImageView, url: String){
        Glide.with(view.context)
            .load(url)
            .thumbnail(0.25f)
            .into(view)
    }
}