package com.example.newsapplinktask.view.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.newsapplinktask.R
import com.example.newsapplinktask.databinding.FragmentNewsDetailsBinding
import com.example.newsapplinktask.model.response.article.ArticlesItem
import com.example.newsapplinktask.view.BaseFragment


class NewsFragmentDetails : BaseFragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    var newsItem : ArticlesItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        newsItem = it["news"] as ArticlesItem?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentNewsDetailsBinding.bind(view)
        initUI()
    }


    @SuppressLint("SetTextI18n")
    private fun initUI(){
        newsItem?.let {
               binding.apply {
                   Glide.with(newsImg.context)
                       .load(it.urlToImage)
                       .placeholder(R.drawable.placeholder)
                       .into(newsImg)
                   newsTitle.text = it.title
                   author.text = "By "+ it.author
                   description.text = it.description

                   viewWebsiteBtn.setOnClickListener {
                       openWebSite(newsItem?.url!!)
                   }
               }
        }
    }


    private fun openWebSite(link :String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }


}