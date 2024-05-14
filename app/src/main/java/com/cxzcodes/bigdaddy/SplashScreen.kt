package com.cxzcodes.bigdaddy

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nkomapp.rupeequiz.Utils.Utils
import org.w3c.dom.Text


class SplashScreen : AppCompatActivity() {
    var installedPackageNameList = arrayListOf<String>()

    var topAnimation: Animation? = null
    var bottomAnimation: Animation? = null
    var middleAnimation: Animation? = null

    // Hooks
    var firstLine: View? = null
    var secondLine: View? = null
    var thirdLine: View? = null
    var fourthLine: View? = null
    var fifthLine: View? = null
    var sixthLine: View? = null
    var txtAppName: LinearLayout? = null
     var fxLogo: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)

        // Loading animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)

        // Loading hooks
        firstLine = findViewById(R.id.first_line) as View
        secondLine = findViewById(R.id.second_line) as View
        thirdLine = findViewById(R.id.third_line) as View
        fourthLine = findViewById(R.id.fourth_line) as View
        fifthLine = findViewById(R.id.fifth_line) as View
        sixthLine = findViewById(R.id.sixth_line) as View
        txtAppName = findViewById<View>(R.id.txt_app_name) as LinearLayout
         fxLogo = findViewById<View>(R.id.fx_logo) as TextView

        // Setting animations
        firstLine!!.animation = topAnimation
        secondLine!!.animation = topAnimation
        thirdLine!!.animation = topAnimation
        fourthLine!!.animation = topAnimation
        fifthLine!!.animation = topAnimation
        sixthLine!!.animation = topAnimation
        txtAppName!!.animation = middleAnimation
        fxLogo!!.animation = middleAnimation
         val pm = packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (packageInfo in packages) {
            if (packageInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                installedPackageNameList.add(packageInfo.packageName)
            }
        }

        if (installedPackageNameList.contains("com.google.android.apps.nbu.paisa.user") ||
            installedPackageNameList.contains("com.phonepe.app") ||
            installedPackageNameList.contains("net.one97.paytm") ||
            installedPackageNameList.contains("com.paytmmall") ||
            installedPackageNameList.contains("in.org.npci.upiapp") ||
            installedPackageNameList.contains("in.amazon.mShop.android.shopping") ||
            installedPackageNameList.contains("com.csam.icici.bank.imobile") ||
            installedPackageNameList.contains("com.sbi.upi") ||
            installedPackageNameList.contains("com.myairtelapp") ||
            installedPackageNameList.contains("n.code.cashtime") ||
            installedPackageNameList.contains("com.icicibank.pockets")
        )
        {
            com.nkomapp.rupeequiz.Utils.Utils.saveData(this, "spam", "true")

            val Hander = Handler()


            Hander.postDelayed(Runnable {
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
            }, 2000)


        }
        else {
            com.nkomapp.rupeequiz.Utils.Utils.saveData(this, "spam", "false")

            val Hander = Handler()
            val username =  Utils.getData(this, "name")
            if (username != null) {
                Hander.postDelayed(Runnable {
                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )
                }, 2000)
            } else {
                Hander.postDelayed(Runnable {
                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        )
                    )
                }, 2000)
            }


        }


    }

    companion object {
        // Splash
        private const val SPLASH_TIME_OUT = 4000
    }
}