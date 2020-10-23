package com.example.chucknorrisfacts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity
import io.reactivex.subjects.PublishSubject

class InternetConnectionHandler(private val activity: FragmentActivity) {

    private val subject = PublishSubject.create<Boolean>()

    private val broadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent!!.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {

                subject.onNext(false)
            } else {

                subject.onNext(true)
            }
        }
    }

    fun isConnected() = subject

    fun register() {
        activity.registerReceiver(
            broadReceiver,
            IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        )
    }

    fun unregister() {
        activity.unregisterReceiver(broadReceiver)
    }


}