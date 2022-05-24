package com.example.tha.ui.noteslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tha.data.models.Note
import com.example.tha.databinding.ModelItemEntityBinding
import com.example.tha.ui.noteslist.NotesListFragmentDirections


class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<Note>()

    class MyViewHolder(private val binding: ModelItemEntityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.titleTextView.text=note.title
            binding.descriptionTextView.text=note.description
            binding.root.setOnClickListener {
                val action =
                    NotesListFragmentDirections.actionListFragmentToUpdateNoteFragment(note)
                it.findNavController().navigate(action)
            }
            binding.notesImage.setImageBitmap(note.image)
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ModelItemEntityBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(
            parent
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(Note: List<Note>) {
        val notesDiffUtil = NotesDiffUtil(dataList, Note)
        val notesDiffResult = DiffUtil.calculateDiff(notesDiffUtil)
        this.dataList = Note
        notesDiffResult.dispatchUpdatesTo(this)
    }

}