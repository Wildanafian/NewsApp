package com.base.myapplication.feature.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rvitem.view.*
import com.base.myapplication.R
import com.base.myapplication.base.BaseRecycleView
import com.base.myapplication.data.model.Article

class NewsAdapter : BaseRecycleView<Article, NewsAdapter.ViewHolder>() {

    var gotoDetail: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getView(parent, R.layout.rvitem)!!)
    }

    override fun getBindViewHolder(holder: ViewHolder, position: Int, data: Article) {
        holder.bind(data)
        holder.itemView.setOnClickListener {
            gotoDetail?.invoke(data)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Article) {
            data.title.let { itemView.tvNewsTitle.text = it }
            data.publishedAt.let { itemView.tvNewsTime.text = it }
            Glide.with(itemView.imgNews.context)
                .load(data.urlToImage)
                .thumbnail(0.25f)
                .into(itemView.imgNews)
        }
    }
}