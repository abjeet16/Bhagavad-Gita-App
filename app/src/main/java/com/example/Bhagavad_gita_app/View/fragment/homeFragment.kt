package com.example.Bhagavad_gita_app.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Bhagavad_gita_app.View.Adapters.ChaptersAdapter
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedChapters
import com.example.Bhagavad_gita_app.viewModel.MainViewModel
import com.example.NetworkManger
import com.example.models.ChaptersItem
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class homeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private val viewModel :MainViewModel by viewModels()
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

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        return binding.root
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
            viewModel.getVarseDetail(RandomchapterNumber,RandomverseNumber).collect{
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
                showVerseOfTheDay()
                verseOfTheDayClicked()
            }else{
                binding.RecyclerView.visibility = View.GONE
                binding.shimmerLayout.visibility = View.GONE
                binding.NoInternetCardView.visibility = View.VISIBLE
            }
        }
    }

    private fun getAllChapter() {
        lifecycleScope.launch {
            viewModel.getAllChapter().collect{ chapterList->
                // making shimmer invisible
                binding.shimmerLayout.visibility = View.GONE

                adapter = ChaptersAdapter(::onChapterItemView,::onSaveChapterClicked)
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
    fun onSaveChapterClicked(chapterItem:ChaptersItem){
        Toast.makeText(activity,"done",Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            viewModel.getverses(chapterItem.chapter_number).collect{
                val versesList = arrayListOf<String>()
                for (currentVerses in it){
                    for (verses in currentVerses.translations){
                        if (verses.language == "english"){
                            versesList.add(verses.description)
                            break
                        }
                    }
                }
                val savedChapter = savedChapters(
                    chapter_number = chapterItem.chapter_number,
                    chapter_summary =  chapterItem.chapter_summary,
                    chapter_summary_hindi = chapterItem.chapter_summary_hindi,
                    id = chapterItem.id,
                    name = chapterItem.name,
                    name_meaning = chapterItem.name_meaning,
                    name_translated = chapterItem.name_translated,
                    name_transliterated = chapterItem.name_transliterated,
                    slug = chapterItem.slug,
                    verses_count = chapterItem.verses_count,

                    verses = versesList

                )
                viewModel.insertChapters(savedChapter)
            }
        }
    }
}