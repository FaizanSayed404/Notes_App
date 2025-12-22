package com.example.notesapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.database.Database

class AddNoteActivity : AppCompatActivity() {
    private lateinit var titleEt: EditText
    private lateinit var descriptionEt: EditText
    private lateinit var addBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        titleEt = findViewById(R.id.titleEt)
        descriptionEt = findViewById(R.id.descriptionEt)
        addBtn = findViewById(R.id.addNotesBtn)
        val db = Database(this)

        addBtn.setOnClickListener {
            val title = titleEt.text.toString()
            val description = descriptionEt.text.toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this,"Note Title or Description Cannot Be Empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.addNotes(title,description)
            setResult(RESULT_OK)
            finish()
        }
    }
}