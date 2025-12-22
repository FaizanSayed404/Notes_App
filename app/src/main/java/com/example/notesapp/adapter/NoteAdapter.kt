package com.example.notesapp.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.UpdateActivity
import com.example.notesapp.database.Database
import com.example.notesapp.models.NoteModel

class NoteAdapter(val context: Context,val launcher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private var notes = emptyList<NoteModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setNote(notes: List<NoteModel>) {
        this.notes = notes
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.items_notes,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = notes[position].title
        holder.description.text = notes[position].description
        holder.delBtn.setOnClickListener {
            val currentPos = holder.bindingAdapterPosition
            val db = Database(context)
            db.deleteNote(notes[currentPos].id)

            val note = notes.toMutableList()
            note.removeAt(currentPos)
            notes = note

            notifyItemRemoved(currentPos)
            notifyItemRangeChanged(currentPos,notes.size)

            if (context is MainActivity && notes.isEmpty()) {
                context.findViewById<TextView>(R.id.noNoteTv).visibility = View.VISIBLE
            }
            Toast.makeText(context,"Note Deleted", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("Note",notes[position])
            launcher.launch(intent)
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.homeTitleTv)
        val description: TextView = itemView.findViewById(R.id.homeDescriptionTv)
        val delBtn: ImageButton = itemView.findViewById(R.id.deleteNoteBtn)
    }
}