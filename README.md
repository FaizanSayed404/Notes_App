# Note App 📝

A clean, efficient Android application designed to help users manage their daily tasks and thoughts. This app utilizes **SQLite** for local data persistence and follows modern Android UI patterns using **RecyclerView** and **CardView**.

## ✨ Features

* **View Notes:** A streamlined Home Page using `RecyclerView` to display all saved notes in a list.
* **Create Notes:** Add new notes with a Title and Description via a dedicated `AddNoteActivity`.
* **Update Notes:** Edit existing notes easily through the `UpdateNoteActivity`.
* **Delete Functionality:** * Delete individual notes directly from the list using the delete icon.
* Clear your entire list at once using the "Delete All" option in the toolbar.


* **User Interface:**
* Floating Action Button (FAB) for quick note entry.
* Toolbar with Menu options for global actions.
* CardView layout for a modern, elevated look.



---

## 🛠 Tech Stack

* **Language:** Kotlin (Android)
* **Database:** SQLite (Local Storage)
* **UI Components:** * `RecyclerView` (for efficient list handling)
* `CardView` (for item styling)
* `FloatingActionButton`
* `Toolbar` with Menu inflation


---

## 🏗 Database Schema

The app uses a single table named `notes` within the SQLite database:

| Column | Type | Description |
| --- | --- | --- |
| `id` | INTEGER | Primary Key (Auto-increment) |
| `title` | TEXT | Title of the note |
| `description` | TEXT | Detailed content of the note |

---

## 🚀 How to Run the App

1. **Clone the Repository:**
```bash
git clone https://github.com/FaizanSayed404/Notes_App.git

```

2. **Open in Android Studio:**
* Go to `File > Open` and select the cloned folder.

3. **Sync Project:**
* Wait for Gradle to finish syncing the dependencies.

4. **Run:**
* Click the **Run** button (green play icon) or press `Shift + F10`.

---

## 📂 Project Structure

* **`MainActivity`**: Displays the `RecyclerView` and handles the Toolbar menu.
* **`AddNoteActivity`**: Handles user input for new notes.
* **`UpdateNoteActivity`**: Pre-populates fields with existing data for editing.
* **`DatabaseHelper`**: Manages SQLite connections, `onCreate`, `onUpgrade`, and CRUD operations.
* **`NoteAdapter`**: Binds the note data to the `CardView` items in the list.
