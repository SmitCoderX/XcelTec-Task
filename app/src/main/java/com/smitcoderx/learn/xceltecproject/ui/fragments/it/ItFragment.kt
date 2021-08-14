package com.smitcoderx.learn.xceltecproject.ui.fragments.it

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.learn.xceltecproject.R
import com.smitcoderx.learn.xceltecproject.Utils.Constants.TAG
import com.smitcoderx.learn.xceltecproject.Utils.Resource
import com.smitcoderx.learn.xceltecproject.adapter.ItAdapter
import com.smitcoderx.learn.xceltecproject.databinding.FragmentItBinding
import com.smitcoderx.learn.xceltecproject.ui.MainActivity
import com.smitcoderx.learn.xceltecproject.ui.NewsViewModel

class ItFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    private var itAdapter: ItAdapter = ItAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentItBinding>(
            inflater,
            R.layout.fragment_it,
            container,
            false
        )
        val view = binding.root

        viewModel = (activity as MainActivity).viewModel

        binding.adapter = itAdapter

        viewModel.getNews()

        viewModel.itNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.prgBar.visibility = View.GONE
                    response.data?.let { newsResponse ->
                        itAdapter.differ.submitList(newsResponse.articles)
                        viewModel.saveNews(newsResponse.articles)
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.d(TAG, "An Error Occured: $message")
                        Snackbar.make(
                            requireView(),
                            "An Error Occured: $message",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        binding.prgBar.visibility = View.GONE
                        viewModel.allNews().observe(viewLifecycleOwner, { articles ->
                            itAdapter.differ.submitList(articles)
                        })
                    }
                }

                is Resource.Loading -> {
                    binding.prgBar.visibility = View.VISIBLE
                }
            }
        })
        return view
    }

}