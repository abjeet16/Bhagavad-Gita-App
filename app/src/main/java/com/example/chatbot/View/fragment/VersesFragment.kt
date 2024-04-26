package com.example.chatbot.View.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.NetworkManger
import com.example.chatbot.View.Adapters.VersesAdapter
import com.example.chatbot.viewModel.MainViewModel
import com.example.shreebhagavatgita.databinding.FragmentVersesBinding
import kotlinx.coroutines.launch

class VersesFragment : Fragment() {

    private lateinit var binding:FragmentVersesBinding
    private val ViewModel : MainViewModel by viewModels()
    private lateinit var versesAdapter: VersesAdapter
    private  var chapterNumber:Int = 0
    private var readMoreCount:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVersesBinding.inflate(layoutInflater)
        binding.shimmerLayout.visibility = View.VISIBLE

        getAndSetChapterDetails()
        checkInternet()
        getAllVerses()
        ClickLisners()
        return binding.root
    }

    private fun ClickLisners() {
        binding.ReadMore.setOnClickListener{
            if (readMoreCount==true) {
                binding.ChapterContent.maxLines = 20
                readMoreCount=false
                binding.ReadMore.text = "Hide"
            }else{
                binding.ChapterContent.maxLines = 5
                readMoreCount=true
                binding.ReadMore.text = "Read More"
            }
        }
    }

    private fun getAndSetChapterDetails() {
        val bundle = arguments
        chapterNumber = bundle?.getInt("chapterNumber")!!
        binding.apply {
            ChapterName.text = bundle?.getString("chapterTitle")
            ChapterNumber.text = "Chapter ${chapterNumber}"
            ChapterContent.text = bundle?.getString("chapterContent")
            VerseNumber.text= "${bundle?.getInt("ChapterVersesCount").toString()} Verses"
        }
    }

    private fun getAllVerses() {
        lifecycleScope.launch {
            ViewModel.getverses(chapterNumber).collect{
                versesAdapter = VersesAdapter()
                binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.RecyclerView.adapter = versesAdapter
                val versesList = arrayListOf<String>()
                for (currentVerses in it){
                    for (verses in currentVerses.translations){
                        if (verses.language == "english"){
                            versesList.add(verses.description)
                            break
                        }
                    }
                }
                versesAdapter.differ.submitList(versesList)
                binding.RecyclerView.visibility = View.VISIBLE
                binding.shimmerLayout.visibility = View.GONE
            }
        }
    }

    private fun checkInternet(){
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){
            if (it == true){
                binding.RecyclerView.visibility = View.VISIBLE
                binding.NoInternetCardView.visibility = View.GONE
            }else{
                binding.shimmerLayout.visibility = View.GONE
                binding.RecyclerView.visibility = View.GONE
                binding.NoInternetCardView.visibility = View.VISIBLE
            }
        }
    }
}