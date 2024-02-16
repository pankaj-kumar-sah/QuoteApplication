package com.panku.quoteapplication.view

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.panku.quoteapplication.R

class DeepLinkActivity : AppCompatActivity() {
    lateinit var myText : TextView
    lateinit var messageTV : TextView
    lateinit var myUri : Uri
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deep_link)

        myText = findViewById(R.id.my_text)
        messageTV = findViewById(R.id.message_text)
        myUri = intent.data!!

        if (myUri!=null)
        {
            val parameters: List<String> = myUri.getPathSegments()


            // after that we are extracting string
            // from that parameters.
            val param = parameters[parameters.size - 1]

            // on below line we are setting that string
            // to our text view which we got as params.
            messageTV.setText(param)
        }
    }
}