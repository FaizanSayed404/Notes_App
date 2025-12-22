package com.example.notesapp

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.database.Database
import com.example.notesapp.models.NoteModel

class UpdateActivity : AppCompatActivity() {
    private lateinit var titleEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var updateNote: Button
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        titleEt = findViewById(R.id.titleEt)
        descriptionEt = findViewById(R.id.descriptionEt)
        updateNote = findViewById(R.id.updateNoteBtn)
        val db = Database(this)
        val note = intent.getSerializableExtra("Note", NoteModel::class.java)
        titleEt.text = Editable.Factory.getInstance().newEditable(note!!.title)
        descriptionEt.text = Editable.Factory.getInstance().newEditable(note.description)


        updateNote.setOnClickListener {
            val title = titleEt.text.toString()
            val description = descriptionEt.text.toString()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this,"Note Title or Description Cannot Be Empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            note.title = title
            note.description = description
            db.updateNote(note)
            setResult(RESULT_OK)
            finish()
        }
    }
}