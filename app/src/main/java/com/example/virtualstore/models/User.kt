package com.example.virtualstore.models
import android.content.ContentValues
import com.example.virtualstore.database.DatabaseHelper

data class User(var id: Int? = null, var fullname: String, var username: String, var password: String, var email: String, var status: Int) {

    fun save(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("fullname", fullname)
        values.put("username", username)
        values.put("password", password)
        values.put("email", email)
        values.put("status", status)

        if (id == null) {
            db.insert("users", null, values)
        } else {
            db.update("users", values, "id = ?", arrayOf(id.toString()))
        }
    }

    fun update(databaseHelper: DatabaseHelper, newValues: ContentValues) {
        val db = databaseHelper.writableDatabase
        db.update("users", newValues, "id = ?", arrayOf(id.toString()))
    }

    fun changeStatus(databaseHelper: DatabaseHelper, newStatus: Int) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("status", newStatus)
        db.update("users", values, "id = ?", arrayOf(id.toString()))
    }

    fun delete(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        db.delete("users", "id = ?", arrayOf(id.toString()))
    }

    fun getAll(databaseHelper: DatabaseHelper): List<User> {
        val users = ArrayList<User>()
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users", null)

        if (cursor.moveToFirst()) {
            do {
                users.add(User(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fullname")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("status"))))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return users
    }

    fun getId(databaseHelper: DatabaseHelper, userId: Int): User? {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", arrayOf(userId.toString()))

        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("fullname")),
                cursor.getString(cursor.getColumnIndexOrThrow("username")),
                cursor.getString(cursor.getColumnIndexOrThrow("password")),
                cursor.getString(cursor.getColumnIndexOrThrow("email")),
                cursor.getInt(cursor.getColumnIndexOrThrow("status")))
        }

        cursor.close()
        return user
    }
}
