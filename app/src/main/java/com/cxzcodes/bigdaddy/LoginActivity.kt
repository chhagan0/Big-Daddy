package com.cxzcodes.bigdaddy

import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.cxzcodes.bigdaddy.databinding.ActivityLoginBinding
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.firestore.FirebaseFirestore
import com.nkomapp.rupeequiz.Utils.Utils
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var base="https://www.bdggame.in/#/main"
     var teligram="https://www.bdggame.in/#/main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onesignal()
        updatelayout()
        fetchData()
        fbAdEvent()
        binding.button.setOnClickListener {
            if (binding.email.text.isEmpty()) {
                Toast.makeText(this, "Enter Your number", Toast.LENGTH_SHORT).show()
            } else if (binding.password.text.isEmpty()) {
                Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            } else {
                Utils.saveData(this, "name", "user")
                Utils.saveData(this, "coin", "0")

                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        binding.cvLogin.setOnClickListener { openCustomChromeTab(base) }
        binding.cvSignup.setOnClickListener { openCustomChromeTab(base) }
         binding.cvTeligram.setOnClickListener { openCustomChromeTab(teligram) }
    }

    private fun onesignal() {
          val ONESIGNAL_APP_ID = "16643f5e-6d36-43be-b2bb-5ac7d3668f5e"
        OneSignal.Debug.logLevel = LogLevel.VERBOSE

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

    }

    fun openCustomChromeTab(url:String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    fun closeapp() {
        val dialog = Dialog(this)
        dialog.setContentView(com.cxzcodes.bigdaddy.R.layout.close_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val btncancel: TextView = dialog.findViewById(com.cxzcodes.bigdaddy.R.id.btnCancel)
        val btnExit: TextView = dialog.findViewById(com.cxzcodes.bigdaddy.R.id.btnExit)
        btncancel.setOnClickListener { dialog.dismiss() }
        btnExit.setOnClickListener { finishAffinity() }
        dialog.create()
        dialog.show()

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        closeapp()
    }
    fun updatelayout(){
        val spam=Utils.getData(this,"spam")
        if(spam=="true"){
            binding.layoutUser.visibility=View.VISIBLE
            binding.layoutlogin.visibility=View.GONE
        }else{
            binding.layoutUser.visibility=View.GONE
            binding.layoutlogin.visibility=View.VISIBLE
            val email = binding.email
            val password = binding.password
            val loginButton = binding.button
            val forgotPassword = binding.forgotPassword

            email.translationX = 800f
            password.translationX = 800f
            forgotPassword.translationX = 800f
            loginButton.translationY = 0f

            email.alpha = 0f
            password.alpha = 0f
            forgotPassword.alpha = 0f
            loginButton.alpha = 0f

            email.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(300).start()
            password.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500).start()
            forgotPassword.animate().translationX(0f).alpha(1f).setDuration(1000).setStartDelay(500)
                .start()
            loginButton.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(700)
                .start()
        }
    }
    fun fetchData(){
        val firebase=FirebaseFirestore.getInstance()
       val ref= firebase.collection("Url").document("url")
        ref.get().addOnSuccessListener {
            if (it!=null){
                val baseurl=it.getString("baseurl")
                 val teligramgroup=it.getString("teligram")
                base=baseurl.toString()
                 teligram=teligramgroup.toString()
             }

        }
            .addOnFailureListener {
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
    fun fbAdEvent(){
        AppEventsLogger.activateApp(application)

        val logger = AppEventsLogger.newLogger(this)
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION);
        logger.logEvent("BattleTheMonster")


        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);


        if (FacebookSdk.isInitialized() && FacebookSdk.getAutoLogAppEventsEnabled()) {
            Log.d("FACEBOOK", "Facebook SDK connected successfully")
        } else {
            Log.d("FACEBOOK", "Facebook SDK connection failed")
        }
    }

}