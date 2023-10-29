package com.example.virtualstore.database
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "VirtualStore.db"
    }

    override fun onCreate(db: SQLiteDatabase){
        //creamos tablas necesarias
        val userTable = """CREATE TABLE users (
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            fullname TEXT, 
            username TEXT UNIQUE, 
            password TEXT, 
            email TEXT UNIQUE, 
            status INTEGER DEFAULT 1
        )"""

        val itemTable = """CREATE TABLE items (
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            item_name TEXT, 
            description TEXT, 
            price DECIMAL(10,2), 
            status INTEGER DEFAULT 1
        )"""

        val cartTable = """CREATE TABLE cart (
            id INTEGER PRIMARY KEY AUTOINCREMENT, 
            user_id INTEGER REFERENCES users(id) ON DELETE CASCADE
        )"""

        val cartItemTable = """CREATE TABLE cart_items (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            cart_id INTEGER REFERENCES cart(id) ON DELETE CASCADE,
            product_id INTEGER REFERENCES items(id) ON DELETE CASCADE,
            price DECIMAL(10,2),
            quantity INTEGER,
            total DECIMAL(10,2)
        )"""

        val orderTable = """CREATE TABLE orders (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
            total DECIMAL(10,2),
            status INTEGER DEFAULT 0
        )"""


        db.execSQL(userTable)
        db.execSQL(itemTable)
        db.execSQL(cartTable)
        db.execSQL(cartItemTable)
        db.execSQL(orderTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){
        //elimina tablas antiguar si existen y recrea las tablas nuevamente
        db.execSQL("DROP TABLE IF EXISTS products")
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS cart")
        onCreate(db)
    }

}