package com.example.Bhagavad_gita_app.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shreebhagavatgita.databinding.AllverceslayoutBinding

class VersesAdapter(val versesClicked: (String, Int) -> Unit) : RecyclerView.Adapter<VersesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: AllverceslayoutBinding) : RecyclerView.ViewHolder(binding.root)

    val diffUtil = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AllverceslayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val verse = differ.currentList[position]
        holder.binding.apply {
            verceNumber.text = "Verse ${position+1}"
            verceContent.text = verse

            Verses.setOnClickListener{
                versesClicked(verse,position+1)
            }
        }
    }
}