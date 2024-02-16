package com.panku.quoteapplication.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BatteryReceiver(): BroadcastReceiver() {
     private var batteryString : String =""


    override fun onReceive(p0: Context?, p1: Intent?) {
        val batteryLevel = p1?.getIntExtra("level",0)

        batteryString = (if(batteryLevel?:0>0) batteryLevel.toString() else "").toString()

        Log.e("mybattery",batteryLevel.toString())
        Log.e("mybatterystring",batteryString)
        when(p1?.action){
            Intent.ACTION_BOOT_COMPLETED->{
                Toast.makeText(p0,"Boot completed",Toast.LENGTH_LONG).show()
            }
        }

    }



      fun batteryText(): String {
        return batteryString
    }

}