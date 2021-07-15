package com.example.noteapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.room.NoteEntity

class NoteAdapter(private val onCheckboxClick: (NoteEntity) -> Unit, val onBellClick: (NoteEntity) -> Unit) : ListAdapter<NoteEntity, ViewHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onCheckboxClick, onBellClick)
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.note_list_title)
    val desc = itemView.findViewById<TextView>(R.id.note_list_desc)
    val checkbox = itemView.findViewById<CheckBox>(R.id.list_checkbox)
    val bell = itemView.findViewById<ImageButton>(R.id.note_notif)

    fun bind(note: NoteEntity,onCheckboxClick: (NoteEntity) -> Unit, onBellClick: (NoteEntity) -> Unit) {
        title.text = note.noteTitle
        desc.text = note.noteDesc
        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onCheckboxClick(note)
                buttonView.isChecked = false
            }
        }
        bell.setOnClickListener {
            onBellClick(note)
        }
    }
}

class ItemComparator: DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem.uid == newItem.uid
    }

}