package com.example.newsapplinktask.view.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplinktask.R
import com.example.newsapplinktask.databinding.ItemNewsLayoutBinding
import com.example.newsapplinktask.model.response.article.ArticlesItem

class NewsAdapter(private val newsClickListener: NewsClickListener) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {


    private val newsList = ArrayList<ArticlesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newsList: List<ArticlesItem>) {
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = ItemNewsLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = newsList[position]
        with(holder) {
            binding.apply {
                Glide.with(newsImg.context)
                    .load(newsItem.urlToImage)
                    .placeholder(R.drawable.placeholder)
                    .into(newsImg)
                newsTitle.text = newsItem.title
                author.text = "By "+ newsItem.author
                newsDate.text = newsItem.publishedAt

            }
        }
    }

    override fun getItemCount(): Int = newsList.size


    inner class NewsHolder(val binding: ItemNewsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener {
                    newsClickListener.showNewsDetails(newsList[layoutPosition])
                }
            }
        }


    interface NewsClickListener{
        fun showNewsDetails(newsItem: ArticlesItem)
    }

}