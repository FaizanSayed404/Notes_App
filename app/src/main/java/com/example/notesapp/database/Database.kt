package com.example.notesapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.notesapp.models.NoteModel

class Database(var context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE $TABLE_NAME( $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TITLE TEXT, $COL_DESCRIPTION TEXT)
        """.trimIndent()
        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXIST $TABLE_NAME")
        onCreate(db)
    }

    fun addNotes(title: String, description: String): Long {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("title",title)
        cv.put("description",description)
       return db.insert("$TABLE_NAME",null,cv)
    }

    fun fetchAllNotes(): List<NoteModel>{
        val noteList = mutableListOf<NoteModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("Select * from $TABLE_NAME",null,null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION))
            noteList.add(NoteModel(id,title,description))
        }
        cursor.close()
        return noteList
    }

    fun deleteAllNotes() {
        val db = writableDatabase
        val res = db.delete(TABLE_NAME,null,null)
        if (res > 0) {
            Toast.makeText(context,"Notes Successfully Deleted.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context,"No Note to be Deleted.", Toast.LENGTH_SHORT).show()
        }

    }

    fun deleteNote(id: Int) {
        val db = writableDatabase
        val res = db.delete(TABLE_NAME,"id=?", arrayOf(id.toString()))
        if (res > 0) {
            Toast.makeText(context,"Note Successfully Deleted.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context,"Failed to Delete Note.", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun updateNote(note: NoteModel) {
        val  db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE,note.title)
        cv.put(COL_DESCRIPTION,note.description)
        db.update(TABLE_NAME,cv,"id=?", arrayOf(note.id.toString()))
    }



    companion object {
        private val DB_NAME = "NoteDb.db"
        private val DB_VERSION = 1
        private val TABLE_NAME = "notes"
        private val COL_ID = "id"
        private val COL_TITLE = "title"
        private val COL_DESCRIPTION = "description"

    }
}