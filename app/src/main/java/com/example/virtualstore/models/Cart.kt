package com.example.virtualstore.models
import android.content.ContentValues
import com.example.virtualstore.database.DatabaseHelper

data class Cart(var id: Int? = null, var user_id: Int) {

    fun addItem(databaseHelper: DatabaseHelper, item: Item, quantity: Int) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("cart_id", id)
        values.put("product_id", item.id)
        values.put("price", item.price)
        values.put("quantity", quantity)
        values.put("total", item.price * quantity)

        db.insert("cart_items", null, values)
    }

    fun removeItem(databaseHelper: DatabaseHelper, itemId: Int) {
        val db = databaseHelper.writableDatabase
        db.delete("cart_items", "cart_id = ? AND product_id = ?", arrayOf(id.toString(), itemId.toString()))
    }

    fun clear(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        db.delete("cart_items", "cart_id = ?", arrayOf(id.toString()))
    }

    fun getTotalPrice(databaseHelper: DatabaseHelper): Double {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery("SELECT SUM(total) as total FROM cart_items WHERE cart_id = ?", arrayOf(id.toString()))

        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
        }

        cursor.close()
        return total
    }
}
