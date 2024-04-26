package com.example.chatbot.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.models.ChaptersItem
import com.example.shreebhagavatgita.databinding.VerseItemLayoutBinding

class ChaptersAdapter(val onChapterItemCLicked: (ChaptersItem) -> Unit) : RecyclerView.Adapter<ChaptersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:VerseItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<ChaptersItem>(){
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(VerseItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter = differ.currentList[position]

        holder.binding.apply {
            chapterNumber.text = "Chapter ${chapter.chapter_number}"
            ChapterName.text = chapter.name_translated
            ChapterContent.text = chapter.chapter_summary
            ChapterVerse.text = "${chapter.verses_count} Verses"
        }
        holder.binding.CardView.setOnClickListener{
            onChapterItemCLicked(chapter)
        }
    }
}