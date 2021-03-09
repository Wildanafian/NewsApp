package com.base.myapplication.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.base.myapplication.base.BaseFragment
import com.base.myapplication.data.model.Article
import com.base.myapplication.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private var data = Article()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data = fromJson(args.data)

        loadImage(binding.imgDetail, data.urlToImage)
        binding.tvDetailTitle.text = data.title
        binding.tvDetailAuthor.text = data.author + "  |  " + data.publishedAt
        binding.tvDetailContent.text = data.description
    }
}