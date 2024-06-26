package com.example.Bhagavad_gita_app.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.Bhagavad_gita_app.datasource.api.RoomDB.savedVerse
import com.example.Bhagavad_gita_app.viewModel.MainViewModel
import com.example.NetworkManger
import com.example.models.Commentary
import com.example.models.Translation
import com.example.models.VercesItemItem
import com.example.shreebhagavatgita.databinding.FragmentFullVersesDetailBinding
import kotlinx.coroutines.launch


class FullVersesDetail : Fragment() {

    private lateinit var binding: FragmentFullVersesDetailBinding
    private val viewModel:MainViewModel by viewModels()
    private var ChapterNumber = 0
    private var verseNumber = 0
    private var versesDetails = MutableLiveData<VercesItemItem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullVersesDetailBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.VISIBLE
        binding.TranslationBackButton.visibility = View.GONE
        binding.CommentaryBackButton.visibility = View.GONE

        checkInternet()
        getAndsetChapterAndVerseNumber()
        return binding.root
    }

    private fun getVersesDeails() {
        lifecycleScope.launch {
            viewModel.getVarseDetail(ChapterNumber,verseNumber).collect{
                verse->
                versesDetails.postValue(verse)
                binding.apply {
                    verseSanskit.text = verse.text
                    verseEnglish1.text = verse.transliteration
                    verseEnglish2.text = verse.word_meanings

                    val TranslationList = arrayListOf<Translation>()
                    for (i in verse.translations){
                        TranslationList.add(i)
                    }

                    val CommentaryList = arrayListOf<Commentary>()
                    for (i in verse.commentaries){
                        CommentaryList.add(i)
                    }
                    val translationListSize = TranslationList.size
                    val CommentaryListSize = CommentaryList.size

                    if (TranslationList.isNotEmpty()){
                        TranslationAutherName.text = TranslationList[0].author_name
                        Translation.text = TranslationList[0].description
                        if (translationListSize == 1){
                            TranslationNextButton.visibility = View.GONE
                        }
                        var i = 0
                        TranslationNextButton.setOnClickListener{
                            if (i<translationListSize-1){
                                i++
                                TranslationAutherName.text = TranslationList[i].author_name
                                Translation.text = TranslationList[i].description.trim()
                                TranslationBackButton.visibility = View.VISIBLE
                                if (i==translationListSize-1){
                                    TranslationNextButton.visibility = View.GONE
                                }
                            }
                        }
                        TranslationBackButton.setOnClickListener{
                            if (i>0){
                                i--
                                TranslationNextButton.visibility = View.VISIBLE
                                TranslationAutherName.text = TranslationList[i].author_name
                                Translation.text = TranslationList[i].description.trim()
                                if (i==0) {
                                    TranslationBackButton.visibility = View.GONE
                                }
                            }
                        }
                    }
                    if (CommentaryList.isNotEmpty()){
                        CommentaryName.text = CommentaryList[0].author_name
                        Commentary.text = CommentaryList[0].description
                        if (translationListSize == 1){
                            CommentaryNextButton.visibility = View.GONE
                        }
                        // next and back button code
                        var i = 0
                        CommentaryNextButton.setOnClickListener{
                            if (i<CommentaryListSize-1){
                                i++
                                CommentaryName.text = CommentaryList[i].author_name
                                Commentary.text = CommentaryList[i].description.trim()
                                CommentaryBackButton.visibility = View.VISIBLE
                                if (i==CommentaryListSize-1){
                                    CommentaryNextButton.visibility = View.GONE
                                }
                            }
                        }
                        CommentaryBackButton.setOnClickListener{
                            if (i>0){
                                i--
                                CommentaryBackButton.visibility = View.VISIBLE
                                CommentaryName.text = CommentaryList[i].author_name
                                Commentary.text = CommentaryList[i].description.trim()
                                if (i==0) {
                                    CommentaryBackButton.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            }
        }
        savebuttonClicked()
    }

    private fun savebuttonClicked() {
        binding.SaveVerse.setOnClickListener {
            binding.SaveVerse.visibility = View.GONE
            saveVerse()
        }
    }

    private fun saveVerse() {
        versesDetails.observe(viewLifecycleOwner){

            val TranslationList = arrayListOf<Translation>()
            for (i in it.translations){
                TranslationList.add(i)
            }
            val CommentaryList = arrayListOf<Commentary>()
            for (i in it.commentaries){
                CommentaryList.add(i)
            }
            val saveVerses = savedVerse(
                 it.chapter_number,
                CommentaryList,
                it.id,
                it.slug,
                it.text,
                TranslationList,
                it.transliteration,
                it.verse_number,
                it.word_meanings
            )
            lifecycleScope.launch {
                viewModel.insertVerse(saveVerses)
            }
        }
    }

    private fun getAndsetChapterAndVerseNumber() {
        val bundle = arguments
        binding.progressBar.visibility = View.GONE
        binding.ScrollView.visibility = View.VISIBLE

        verseNumber = bundle?.getInt("versesNumber")!!
        ChapterNumber = bundle?.getInt("chapterNumber")!!

        binding.verseNumber.text = "||$ChapterNumber.$verseNumber||"
    }

    private fun checkInternet(){
        val networkManger = NetworkManger(requireContext())
        networkManger.observe(viewLifecycleOwner){
            if (it == true){
                binding.ScrollView.visibility = View.VISIBLE
                binding.NoInternetCardView.visibility = View.GONE
                getVersesDeails()
            }else{
                binding.progressBar.visibility = View.GONE
                binding.ScrollView.visibility = View.GONE
                binding.NoInternetCardView.visibility = View.VISIBLE
            }
        }
    }
}