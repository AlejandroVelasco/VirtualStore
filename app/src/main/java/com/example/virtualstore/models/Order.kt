package com.example.virtualstore.models
import android.content.ContentValues
import com.example.virtualstore.database.DatabaseHelper

data class Order(var id: Int? = null, var user_id: Int, var total: Double, var status: Int) {

    fun processOrder(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("user_id", user_id)
        values.put("total", total)
        values.put("status", 1)  // Por ejemplo, 1 podría representar "En Proceso"

        if (id == null) {
            db.insert("orders", null, values)
        } else {
            db.update("orders", values, "id = ?", arrayOf(id.toString()))
        }
    }

    fun cancelOrder(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("status", -1)  // Por ejemplo, -1 podría representar "Cancelado"

        db.update("orders", values, "id = ?", arrayOf(id.toString()))
    }

    fun completeOrder(databaseHelper: DatabaseHelper) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put("status", 2)  // Por ejemplo, 2 podría representar "Completado"

        db.update("orders", values, "id = ?", arrayOf(id.toString()))
    }
}
