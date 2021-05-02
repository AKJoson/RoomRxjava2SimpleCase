package com.yp.www

import addTo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.yp.www.database.database.AppDataBase
import com.yp.www.database.entity.DeviceEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import log

class MainActivity : AppCompatActivity() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListView()
        tvInsert.setOnClickListener {
            testInsert()
        }
        tvDelete.setOnClickListener {
            testDelete()
        }
        testQuery()
    }

    private val dataLists = mutableListOf<String>()
    var adapter: ArrayAdapter<String>? = null

    private fun initListView() {
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataLists)
        listView.adapter = adapter
    }

    var num = 0

    private fun testInsert() {
        num++
        val deviceEntity = DeviceEntity(
            (num*10000*Math.random()).toInt(),
            "name:${num}",
            (num * Math.random() * 10).toInt(),
            Math.random() > 0.5
        )
        AppDataBase.getInstance(BaseApplication.context)
            .deviceDao()
            .insertDevice(deviceEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show()
            }, { error -> MainActivity::class.java.simpleName.log(error.message) })
            .addTo(compositeDisposable)
    }

    private fun testQuery() {
        AppDataBase.getInstance(BaseApplication.context)
            .deviceDao()
            .getAllDevice()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { deviceLists -> processDevices(deviceLists) },
                { error -> MainActivity::class.java.simpleName.log(error.message) })
            .addTo(compositeDisposable)
    }

    private fun processDevices(deviceLists: List<DeviceEntity>?) {
        dataLists.clear()
        deviceLists?.map { "${it.id},${it.name}" }?.let {
            dataLists.addAll(it)
        }
        adapter?.notifyDataSetChanged()
    }

    private fun testDelete() {
        AppDataBase.getInstance(BaseApplication.context)
            .deviceDao()
            .deleteAllDevice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Toast.makeText(this, "Delete success!", Toast.LENGTH_SHORT).show()
                    num = 0
                },
                { error -> MainActivity::class.java.simpleName.log(error.message) })
            .addTo(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}