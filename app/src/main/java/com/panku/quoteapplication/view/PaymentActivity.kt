package com.panku.quoteapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.panku.quoteapplication.R
import com.panku.quoteapplication.databinding.ActivityPaymentBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.Theme_AppCompat)
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.payBtn.setOnClickListener {
            makePayment()
        }
    }

    private fun makePayment() {
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Pankaj corp")
            options.put("description","SUBSCRIBE NOW")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount","100")//pass amount in currency subunits



            val prefill = JSONObject()
            prefill.put("email","")
            prefill.put("contact","")

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment successful $p0" ,Toast.LENGTH_LONG).show()
        binding.payBtn.visibility = View.GONE
        binding.paymentImage.visibility = View.GONE



    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Error $p1 ",Toast.LENGTH_LONG).show()
    }
}