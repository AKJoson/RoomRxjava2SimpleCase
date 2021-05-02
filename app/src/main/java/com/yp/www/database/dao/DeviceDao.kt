package com.yp.www.database.dao

import androidx.room.*
import com.yp.www.database.entity.DeviceEntity
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface DeviceDao {

    @Query("SELECT * FROM DeviceEntity")
    fun getAllDevice(): Flowable<List<DeviceEntity>>

    @Query("DELETE FROM DeviceEntity")
    fun deleteAllDevice(): Maybe<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevices(lists: List<DeviceEntity>): Maybe<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(device: DeviceEntity): Maybe<Long>

    @Update
    fun updateDevice(device: DeviceEntity): Maybe<Int>

}