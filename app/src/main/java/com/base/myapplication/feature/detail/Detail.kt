package com.base.myapplication.feature.detail

import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import com.base.myapplication.R
import com.base.myapplication.base.BaseActivity
import com.base.myapplication.data.model.Article

class Detail : BaseActivity(R.layout.activity_detail) {

    lateinit var data: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra("data").let { data = Gson().fromJson(it, Article::class.java) }

        loadImage(imgNews_detail, data.urlToImage)
        tvNews_content.text = data.content
    }

}