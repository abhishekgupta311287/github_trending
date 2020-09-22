package com.abhishekgupta.trending.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhishekgupta.trending.R
import com.abhishekgupta.trending.model.Resource
import com.abhishekgupta.trending.view.adapter.TrendingRepoAdapter
import com.abhishekgupta.trending.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.shimmer_effect_layout.*
import kotlinx.android.synthetic.main.swipe_to_refresh_layout.*
import org.koin.android.ext.android.get

class TrendingFragment : Fragment() {
    private val adapter by lazy { TrendingRepoAdapter(requireContext()) }

    companion object {
        fun newInstance() = TrendingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.trending_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initTrendingView()
        handleRetryButtonAction()
        fetchRepos()
    }

    private fun initTrendingView() {
        trendingView.layoutManager = LinearLayoutManager(requireContext())
        trendingView.setHasFixedSize(true)

        trendingView.adapter = adapter

        trendingView.addItemDecoration(
            DividerItemDecoration(
                trendingView.context,
                (trendingView.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    private fun handleRetryButtonAction() {
        retry.setOnClickListener {
            shimmerLayout.showShimmer(true)
            shimmerLayout.visibility = View.VISIBLE

            errorLayout.visibility = View.GONE

            fetchRepos()
        }
    }

    private fun fetchRepos() {
        val viewModel: TrendingViewModel = get() // by koin dependency injection
        viewModel
            .requestTrendingRepositories()
            .observe(viewLifecycleOwner, Observer {

                when(it) {
                    is Resource.Success ->  {
                        hideShimmerLoading()

                        swipeRefresh.visibility = View.VISIBLE
                        adapter.repositories.addAll(it.data as ArrayList)
                        adapter.notifyDataSetChanged()
                    }
                    is Resource.Loading -> {
                        // not required as of now
                        // shimmer loading shown by default
                    }
                    is Resource.Error -> {
                        // show error
                        hideShimmerLoading()
                        errorLayout.visibility = View.VISIBLE
                    }
                }

            })
    }

    private fun hideShimmerLoading() {
        shimmerLayout.hideShimmer()
        shimmerLayout.visibility = View.GONE
    }
}