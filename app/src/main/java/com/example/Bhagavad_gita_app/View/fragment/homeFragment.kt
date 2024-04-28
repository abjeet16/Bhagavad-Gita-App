package com.example.Bhagavad_gita_app.View.fragment

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
import com.example.Bhagavad_gita_app.View.Adapters.ChaptersAdapter
import com.example.Bhagavad_gita_app.viewModel.MainViewModel
import com.example.models.ChaptersItem
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class homeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private val ViewModel :MainViewModel by viewModels()
    private lateinit var adapter:ChaptersAdapter
    private var RandomverseNumber = 0
    private var RandomchapterNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.RecyclerView.visibility = View.GONE

        changeStatusBarColor()
        checkInternet()
        showVerseOfTheDay()
        verseOfTheDayClicked()
        getVersesCount()

        return binding.root
    }

    private fun getVersesCount() {

    }

    private fun verseOfTheDayClicked() {
        binding.VerseOfTheDay.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("chapterNumber",RandomchapterNumber)
            bundle.putInt("versesNumber",RandomverseNumber)
            findNavController().navigate(R.id.action_homeFragment_to_fullVersesDetail,bundle)
        }
    }

    private fun showVerseOfTheDay() {
        RandomchapterNumber = (1 .. 18).random()
        RandomverseNumber = (1..20).random()

        lifecycleScope.launch {
            ViewModel.getVarseDetail(RandomchapterNumber,RandomverseNumber).collect{
                binding.VerseOfTheDay.text = it.text
            }
        }
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
            ViewModel.getAllChapter().collect{ chapterList->
                // making shimmer invisible
                binding.shimmerLayout.visibility = View.GONE

                adapter = ChaptersAdapter(::onChapterItemView)
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
    private fun onChapterItemView(chaptersItem: ChaptersItem){
        val bundle = Bundle()
        bundle.putInt("chapterNumber",chaptersItem.chapter_number)
        bundle.putString("chapterTitle",chaptersItem.name_translated)
        bundle.putString("chapterContent",chaptersItem.chapter_summary)
        bundle.putInt("ChapterVersesCount",chaptersItem.verses_count)
        findNavController().navigate(R.id.action_homeFragment_to_versesFragment,bundle)
    }
}