package com.example.chatbot.View.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.chatbot.viewModel.MainViewModel
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentFullVersesDetailBinding
import com.example.shreebhagavatgita.databinding.FragmentVersesBinding


class FullVersesDetail : Fragment() {

    private lateinit var binding: FragmentFullVersesDetailBinding
    private val viewModel:MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullVersesDetailBinding.inflate(layoutInflater)

        getAndsetChapterAndVerseNumber()
        return binding.root
    }

    private fun getAndsetChapterAndVerseNumber() {
        val bundle = arguments
        val verseNumber = bundle?.getInt("versesNumber")
        val ChapterNumber = bundle?.getInt("chapterNumber")

        binding.verseNumber.text = "||${verseNumber}.${ChapterNumber}||"
    }

}