package com.yp.www.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yp.www.database.dao.DeviceDao
import com.yp.www.database.entity.DeviceEntity

@Database(entities = [DeviceEntity::class],version = 1,exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao

    companion object {
        @Volatile
        var appDataBase: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return if (appDataBase == null) {
                buildRoomDataBase(context)
            } else {
                appDataBase!!
            }
        }

        private fun buildRoomDataBase(context: Context): AppDataBase {
            appDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "databasename")
//                .allowMainThreadQueries()
                .build()
            return appDataBase!!
        }
    }

}