package com.example.Bhagavad_gita_app.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Bhagavad_gita_app.View.Adapters.VersesAdapter
import com.example.Bhagavad_gita_app.viewModel.MainViewModel
import com.example.NetworkManger
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentVersesBinding
import kotlinx.coroutines.launch

class VersesFragment : Fragment() {

    private lateinit var binding:FragmentVersesBinding
    private val ViewModel : MainViewModel by viewModels()
    private lateinit var versesAdapter: VersesAdapter
    private  var chapterNumber:Int = 0
    private var readMoreCount:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVersesBinding.inflate(layoutInflater)
        binding.shimmerLayout.visibility = View.VISIBLE

        getAndSetChapterDetails()
        getData()
        ClickLisners()
        return binding.root
    }

    private fun getData() {
        val bundle = arguments
        // false if redirected from Api  // true if redirected from RoomDB
        val showDataFromRoom = bundle?.getBoolean("RoomDB",false)
        if (showDataFromRoom==true){
            getDataFromRoom()
        }else{
            checkInternet()
        }
    }

    private fun getDataFromRoom() {
        binding.NoInternetCardView.visibility = View.GONE
        ViewModel.getaParticularChapter(chapterNumber).observe(viewLifecycleOwner){
            binding.ChapterNumber.text = "Chapter ${it.chapter_number}"
            binding.ChapterContent.text = it.chapter_summary
            binding.VerseNumber.text= "${it.verses_count} Verses"
            binding.ChapterName.text = it.name_translated

            showListInAdapter(it.verses,true)
        }
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
                val versesList = arrayListOf<String>()
                for (currentVerses in it){
                    for (verses in currentVerses.translations){
                        if (verses.language == "english"){
                            versesList.add(verses.description)
                            break
                        }
                    }
                }
                showListInAdapter(versesList,false)
            }
        }
    }

    private fun showListInAdapter(versesList: List<String>,onClickByRoomDB:Boolean) {
        versesAdapter = VersesAdapter(::onVersesItemClicked,onClickByRoomDB)
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.adapter = versesAdapter
        versesAdapter.differ.submitList(versesList)
        binding.RecyclerView.visibility = View.VISIBLE
        binding.shimmerLayout.visibility = View.GONE
    }

    private fun checkInternet(){
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){
            if (it == true){
                binding.RecyclerView.visibility = View.VISIBLE
                binding.NoInternetCardView.visibility = View.GONE
                getAllVerses()
            }else{
                binding.shimmerLayout.visibility = View.GONE
                binding.RecyclerView.visibility = View.GONE
                binding.NoInternetCardView.visibility = View.VISIBLE
            }
        }
    }
    private fun onVersesItemClicked(verses:String,versesNumber:Int){
        val bundle = Bundle()
        bundle.putInt("chapterNumber",chapterNumber)
        bundle.putInt("versesNumber",versesNumber)
        findNavController().navigate(R.id.action_versesFragment_to_fullVersesDetail,bundle)
    }
}