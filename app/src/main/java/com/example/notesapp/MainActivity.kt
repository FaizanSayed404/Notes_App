package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.database.Database
import com.example.notesapp.models.NoteModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var addBtn: FloatingActionButton
    private lateinit var noNoteTv: TextView
    private  val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
        if (result.resultCode == RESULT_OK) {
            recreate()
        }
    }
    val db = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerViewNotes)
        noNoteTv = findViewById(R.id.noNoteTv)
        addBtn = findViewById(R.id.addNotesBtn)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId)  {
                R.id.deleteAllNoteMenu -> {
                    db.deleteAllNotes()
                    noteAdapter.setNote( db.fetchAllNotes())
                    noNoteTv.visibility = View.VISIBLE
                }
                R.id.ExitMenu -> {
                    finish()
                }

            }
            return@setOnMenuItemClickListener true
        }

        noteAdapter = NoteAdapter(this,activityResultLauncher)
        recyclerView.adapter = noteAdapter
        if (db.fetchAllNotes().isEmpty()) {
            noNoteTv.visibility = View.VISIBLE
        } else {
            noNoteTv.visibility = View.GONE
        }
        noteAdapter.setNote( db.fetchAllNotes())

        addBtn.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            activityResultLauncher.launch(intent)

        }
    }
}