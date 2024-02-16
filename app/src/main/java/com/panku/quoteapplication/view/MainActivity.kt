package com.panku.quoteapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.panku.quoteapplication.MainViewModelFactory
import com.panku.quoteapplication.Quote
import com.panku.quoteapplication.R
import com.panku.quoteapplication.data.ContactData
import com.panku.quoteapplication.database.ContactDatabase
import com.panku.quoteapplication.receiver.BatteryReceiver
import com.panku.quoteapplication.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
    lateinit var database : ContactDatabase
    lateinit var batteryReceiver: BatteryReceiver
    lateinit var pageBatteryTitle: TextView
    lateinit var mainViewModel: MainViewModel
    lateinit var contactBtn : Button
    lateinit var contactText : TextView
    lateinit var title : TextView
    lateinit var author : TextView
    lateinit var previousBtn : TextView
    lateinit var nextBtn : TextView
    lateinit var paymentText : TextView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pageBatteryTitle = findViewById<TextView>(R.id.title)
        contactBtn = findViewById(R.id.contact_button)
        contactText = findViewById(R.id.contact_text)
        paymentText = findViewById(R.id.payment_link)
        batteryReceiver = BatteryReceiver()
      //  database = ContactDatabase.getDatabase(this)
        //inserting contact in room database
//        GlobalScope.launch {
//            database.contactDao().insertContact((ContactData(1,"pankaj","908766555", Date(),1)))
//
//        }
        registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val intent = Intent(this,batteryReceiver::class.java)
        sendBroadcast(intent)
        title = findViewById<TextView>(R.id.quote_text)
        author = findViewById<TextView>(R.id.author_text)
        previousBtn = findViewById(R.id.previous_btn)
        nextBtn = findViewById(R.id.next_btn)
        pageBatteryTitle.text = batteryReceiver.batteryText()
        Log.e("pageBatteryTitle","${pageBatteryTitle.text}")
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)
        setQuote(mainViewModel.getQuote())
            previousBtn.setOnClickListener {
                try {
                    setQuote(mainViewModel.previousQuote())

                }
                catch (e:ArrayIndexOutOfBoundsException){
                    Toast.makeText(this,"you have reached the quote limit",Toast.LENGTH_LONG).show()
                }
            }
            nextBtn.setOnClickListener {
                try {
                    setQuote(mainViewModel.nextQuote())

                }
                catch (e:ArrayIndexOutOfBoundsException){
                    Toast.makeText(this,"you have reached the quote limit",Toast.LENGTH_LONG).show()
                }
            }

paymentText.setOnClickListener {
    val intent = Intent(this,PaymentActivity::class.java)
    startActivity(intent)
}

    }

    override fun onResume() {
        super.onResume()
        contactBtn.setOnClickListener {
            getContact()
        }
    }
    fun setQuote(quote: Quote){
        title.text = quote.text
        author.text = quote.author
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
    }
    fun getContact(){
        database.contactDao().getContact().observe(this, Observer {
            contactText.text = it.toString()
        })
    }
}