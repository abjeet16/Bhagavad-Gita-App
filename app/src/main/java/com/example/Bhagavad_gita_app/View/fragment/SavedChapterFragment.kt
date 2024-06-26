package com.example.Bhagavad_gita_app.View.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Bhagavad_gita_app.View.Adapters.ChaptersAdapter
import com.example.Bhagavad_gita_app.viewModel.MainViewModel
import com.example.models.ChaptersItem
import com.example.shreebhagavatgita.R
import com.example.shreebhagavatgita.databinding.FragmentSavedChapterBinding

class SavedChapterFragment : Fragment() {

    private lateinit var binding : FragmentSavedChapterBinding
    private val viewModel:MainViewModel by viewModels()
    private lateinit var chaptersAdapter:ChaptersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedChapterBinding.inflate(layoutInflater)

        getSavedChapters()
        return binding.root
  }

    private fun getSavedChapters() {
        viewModel.getSavedChapters().observe(viewLifecycleOwner){
            val chaptersList = arrayListOf<ChaptersItem>()
            for (i in it){
                val chapterItem = ChaptersItem(i.chapter_number,i.chapter_summary,i.chapter_summary_hindi,i.id,i.name,i.name_meaning,i.name_translated,i.name_transliterated,i.slug,i.verses_count)
                chaptersList.add(chapterItem)
            }
            if (chaptersList.isEmpty()){
                binding.noSavedChapters.visibility = View.VISIBLE
                binding.RecyclerView.visibility = View.GONE
            }
            chaptersAdapter = ChaptersAdapter(::onSavedChapterViewClicked,::deleteFromSaved)
            binding.RecyclerView.adapter = chaptersAdapter
            binding.RecyclerView.layoutManager = LinearLayoutManager(context)
            binding.RecyclerView.visibility = View.VISIBLE
            chaptersAdapter.differ.submitList(chaptersList)
        }
    }
    fun onSavedChapterViewClicked(chaptersItem: ChaptersItem){
        val bundle = Bundle()
        bundle.putInt("chapterNumber",chaptersItem.chapter_number)
        bundle.putBoolean("RoomDB",true)

        findNavController().navigate(R.id.action_savedChapterFragment_to_versesFragment,bundle)
    }
    fun deleteFromSaved(chaptersItem: ChaptersItem){

    }
}