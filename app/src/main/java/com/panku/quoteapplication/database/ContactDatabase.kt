package com.panku.quoteapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.panku.quoteapplication.converters.DateConverter
import com.panku.quoteapplication.data.ContactData
import com.panku.quoteapplication.interfaces.ContactDao

@Database(entities = [ContactData::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object{

        val migration_1_2 = object :Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE contact ADD COLUMN isActive INTEGER NOT NULL DEFAULT(1)")
            }

        }

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            if (INSTANCE == null)
            {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,ContactDatabase::class.java,"contactDB")
                        //.addMigrations(migration_1_2)
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}