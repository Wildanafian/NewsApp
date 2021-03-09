package com.base.myapplication.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.myapplication.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class BaseFragment : Fragment(), View.OnClickListener {

    private var loadingDialog: Dialog? = null
    private var popupDialog: PopUpDialog? = null

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

    fun moveTo(directions: NavDirections) = findNavController().navigate(directions)

    fun toJson(data: Any): String = Gson().toJson(data)

    inline fun <reified T> fromJson(json: String?): T = Gson().fromJson(json, object : TypeToken<T>() {}.type)

    fun <T:Fragment, RV : RecyclerView.ViewHolder?> T.initRecycleView(rv: RecyclerView, adapterRV: RecyclerView.Adapter<RV>) =
        setRv(rv, adapterRV, LinearLayoutManager(context))

    private fun <RV : RecyclerView.ViewHolder?> setRv(rv: RecyclerView, adapterRV: RecyclerView.Adapter<RV>, linearLayoutManager: LinearLayoutManager) {
        rv.apply {
            layoutManager = linearLayoutManager
            adapter = adapterRV
        }
    }

    fun gone(view : View) {
        view.visibility = View.GONE
    }

    fun visible(view : View) {
        view.visibility = View.VISIBLE
    }

    fun finishedLoading() = loadingDialog?.dismiss()

    fun failureTask(message: String) = generateDialog("Error", message, "OK")

    fun warningTask(message: String) = generateDialog("Warning", message, "OK")

    fun infoTask(message: String) = generateDialog("Info", message, "OK")

    fun toast(message: String?) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    fun toastLong(message: String?) =
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

    private fun initLoading() {
        loadingDialog = Dialog(requireContext())
        loadingDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog?.setContentView(
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_loading_dialog, null)
        )
        loadingDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingDialog?.setCancelable(false)
    }

    private fun initDialog(title: String, message: String, button: String) {
        popupDialog = PopUpDialog(requireContext(), title, message, button)
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

    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .thumbnail(0.25f)
            .into(view)
    }
}