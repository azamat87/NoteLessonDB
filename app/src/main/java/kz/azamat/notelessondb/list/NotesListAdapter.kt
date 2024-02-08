package kz.azamat.notelessondb.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.azamat.notelessondb.Note
import kz.azamat.notelessondb.databinding.ItemNoteBinding

class NotesListAdapter (val noteListener: NoteItemListener) : ListAdapter<Note, NotesListAdapter.ViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.titleTv.text = note.title
            binding.notesTv.text = note.text
            binding.root.setOnClickListener {
                noteListener.onNoteClick(note)
            }
            binding.deleteIcon.setOnClickListener {
                noteListener.deleteNote(note)
            }
        }
    }

    private companion object {
        val callback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem == newItem

        }
    }

    interface NoteItemListener {
        fun onNoteClick(note: Note)
        fun deleteNote(note: Note)
    }
}