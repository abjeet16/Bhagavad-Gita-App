package com.example.chatbot.View.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.NetworkManger
import com.example.chatbot.View.Adapters.ChaptersAdapter
import com.example.chatbot.viewModel.MainViewModel
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class homeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private val ViewModel :MainViewModel by viewModels()
    private lateinit var adapter:ChaptersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.shimmerLayout.visibility = View.VISIBLE

        changeStatusBarColor()
        checkInternet()

        return binding.root
    }

    private fun checkInternet(){
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){
            if (it == true){
                binding.RecyclerView.visibility = View.VISIBLE
                binding.NoInternetCardView.visibility = View.GONE
                getAllChapter()
            }else{
                binding.RecyclerView.visibility = View.GONE
                binding.shimmerLayout.visibility = View.GONE
                binding.NoInternetCardView.visibility = View.VISIBLE
            }
        }
    }

    private fun getAllChapter() {
        lifecycleScope.launch {
            ViewModel.getAllChapter().collect{
                chapterList->
                // making shimmer invisible
                binding.shimmerLayout.visibility = View.GONE

                adapter = ChaptersAdapter()
                binding.RecyclerView.layoutManager = LinearLayoutManager(context)
                binding.RecyclerView.adapter = adapter
                adapter.differ.submitList(chapterList)
            }
        }
    }

    private fun changeStatusBarColor() {
        val window = activity?.window
        if (window != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        if (window != null) {
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        if (window != null) {
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }
}