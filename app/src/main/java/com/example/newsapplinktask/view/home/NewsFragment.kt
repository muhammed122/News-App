package com.example.newsapplinktask.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplinktask.R
import com.example.newsapplinktask.databinding.FragmentNewsBinding
import com.example.newsapplinktask.model.response.article.ArticlesItem
import com.example.newsapplinktask.view.BasicFragment
import com.example.newsapplinktask.view.details.NewsFragmentDetails
import com.example.newsapplinktask.view.home.adapter.NewsAdapter
import com.example.newsapplinktask.viewmodel.BaseViewModel
import com.example.newsapplinktask.viewmodel.article.ArticleViewModel
import com.example.newsapplinktask.view.util.ProgressLoading
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : BasicFragment(), NewsAdapter.NewsClickListener {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!


    private var newsAdapter: NewsAdapter? = null

    private fun setUpRecycler() {
        newsAdapter = NewsAdapter(this)
        binding.newsRecycler.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNews()

    }

    private val viewModel by viewModel<ArticleViewModel>()
    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)
        observe()
        setUpRecycler()
    }


    private fun observe() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            newsAdapter?.addData(it)
        }
    }

    override fun showLoading() {

        if (!ProgressLoading.isVisible())
        ProgressLoading.show(requireActivity())
    }

    override fun hideLoading() {
        ProgressLoading.dismiss()
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun showNewsDetails(newsItem: ArticlesItem) {
        val bundle = Bundle()
        bundle.putSerializable("news", newsItem)
        addFragment(
            NewsFragmentDetails(),
            (activity as MainActivity).getContainerId(),
            true,
            bundle = bundle
        )

    }

}