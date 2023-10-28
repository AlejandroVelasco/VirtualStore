package com.example.virtualstore.models
import android.content.ContentValues
import com.example.virtualstore.database.DatabaseHelper

data class Item(var id: Int? = null, var item_name: String, var description: String, var price: Double, var status: Int) {

    fun save(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("item_name", item_name)
        values.put("description", description)
        values.put("price", price)
        values.put("status", status)

        if (id == null) {
            db.insert("items", null, values)
        } else {
            db.update("items", values, "id = ?", arrayOf(id.toString()))
        }
    }

    fun update(databaseHelper: DatabaseHelper, newValues: ContentValues) {
        val db = databaseHelper.writableDatabase
        db.update("items", newValues, "id = ?", arrayOf(id.toString()))
    }

    fun changeStatus(databaseHelper: DatabaseHelper, newStatus: Int) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("status", newStatus)
        db.update("items", values, "id = ?", arrayOf(id.toString()))
    }

    fun delete(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        db.delete("items", "id = ?", arrayOf(id.toString()))
    }

    fun getAll(databaseHelper: DatabaseHelper): List<Item> {
        val items = ArrayList<Item>()
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM items", null)

        if (cursor.moveToFirst()) {
            do {
                items.add(Item(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("item_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("status"))))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return items
    }

    fun getId(databaseHelper: DatabaseHelper, itemId: Int): Item? {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM items WHERE id = ?", arrayOf(itemId.toString()))

        var item: Item? = null
        if (cursor.moveToFirst()) {
            item = Item(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                cursor.getString(cursor.getColumnIndexOrThrow("item_name")),
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                cursor.getInt(cursor.getColumnIndexOrThrow("status")))
        }

        cursor.close()
        return item
    }
}
