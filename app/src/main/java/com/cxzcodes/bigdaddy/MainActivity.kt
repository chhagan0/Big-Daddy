package com.cxzcodes.bigdaddy

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cxzcodes.bigdaddy.databinding.ActivityMainBinding
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import com.nkomapp.rupeequiz.Utils.Utils
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var coin = 0
    var pos = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         val previouscoin=Utils.getData(this,"coin")
        binding.avCoin.setText("Available Coin:"+previouscoin)
        binding.tcCoin.setText(previouscoin)
        binding.btnSpinnow.setOnClickListener {
            val random = Random.nextInt(1, 14)
            spinNow(random, pos)
        }
        binding.spinBtn.setOnClickListener {
            val random = Random.nextInt(1, 14)
            spinNow(random, pos)
        }
        binding.ivBack.setOnClickListener {
            binding.layoutGame.visibility= View.VISIBLE
        binding.layoutWithdraw.visibility=View.GONE}
        binding.btnWithdraw.setOnClickListener { binding.layoutGame.visibility= View.GONE
        binding.layoutWithdraw.visibility=View.VISIBLE}
        binding.btnRedeem.setOnClickListener { redeem() }
    }

    private fun redeem() {
        val coin=Utils.getData(this,"coin")!!.toInt()
        if (binding.etNumber.text.isEmpty()){
            Toast.makeText(this, "Enter Your Number", Toast.LENGTH_SHORT).show()
        }else if (binding.etCoin.text.toString().toInt() <= coin){
            val avCoin=coin-binding.etCoin.text.toString().toInt()
            Utils.saveData(this,"coin",avCoin.toString())
            binding.tcCoin.setText(avCoin.toString())
            redeemdalog(binding.etCoin.text.toString())
            val previouscoin=Utils.getData(this,"coin")
            binding.avCoin.setText("Available Coin"+previouscoin)

        }else{
            Toast.makeText(this, "Your Coin is less", Toast.LENGTH_SHORT).show()
        }
    }

    private fun spinNow(randomInt: Int, isUpiAvailable: String) {
        val spinOption = 15
        val SpinOptionDeg = 360 / spinOption
        val rotate = RotateAnimation(
            0f,
            (360 - SpinOptionDeg * randomInt).toFloat(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = (SpinOptionDeg * (spinOption - randomInt) + 1000).toLong()
        rotate.interpolator = DecelerateInterpolator()
        rotate.fillAfter = true
        binding.spinWheel?.setDrawingCacheEnabled(true)
        val spinAnim =
            AnimationUtils.loadAnimation(this, R.anim.spinrotate) as RotateAnimation
        spinAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.spinWheel?.startAnimation(rotate)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                Log.e("value for spin : ", randomInt.toString() + "")

                when (randomInt) {
                    0 -> coin = resources.getString(R.string.case_1).toInt()
                    1 -> coin = resources.getString(R.string.case_2).toInt()
                    2 -> coin = resources.getString(R.string.case_3).toInt()
                    3 -> coin = resources.getString(R.string.case_4).toInt()
                    4 -> coin = resources.getString(R.string.case_5).toInt()
                    5 -> coin = resources.getString(R.string.case_6).toInt()
                    6 -> coin = resources.getString(R.string.case_7).toInt()
                    7 -> coin = resources.getString(R.string.case_8).toInt()
                    8 -> coin = resources.getString(R.string.case_9).toInt()
                    9 -> coin = resources.getString(R.string.case_10).toInt()
                    10 -> coin = resources.getString(R.string.case_11).toInt()
                    11 -> coin = resources.getString(R.string.case_12).toInt()
                    12 -> coin = resources.getString(R.string.case_13).toInt()
                    13 -> coin = resources.getString(R.string.case_14).toInt()
                    14 -> coin = resources.getString(R.string.case_15).toInt()
                }
                 if (coin == -10 || coin == -25) {
                    showLooseDialog(coin.toString(), isUpiAvailable)
                } else {
                    showWinningDialog(coin.toString(), isUpiAvailable)

                }


            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("@@@", "onAnimationRepeat: ")
            }
        })
        binding.spinWheel?.startAnimation(spinAnim)


    }
    private fun showWinningDialog(amount: String, isUpiAvailable: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.win_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<TextView>(R.id.text1).text = "Congrats!! you have won $amount"
        val btn:TextView=dialog.findViewById(R.id.btn_spin_more)
        btn.setOnClickListener {
            updatecoin()
            dialog.dismiss() }
        dialog.create()
        dialog.show()


    }
    private fun redeemdalog(amount: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.win_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<TextView>(R.id.text1).text = "Congrats!! You successfully withdraw coin $amount"
        val btn:TextView=dialog.findViewById(R.id.btn_spin_more)
        btn.setText("OK")
        btn.setOnClickListener {
            binding.layoutGame.visibility= View.VISIBLE
            binding.layoutWithdraw.visibility=View.GONE
            dialog.dismiss() }
        dialog.create()
        dialog.show()


    }

    private fun showLooseDialog(amount: String, isUpiAvailable: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.win_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<TextView>(R.id.text1).text = "Oops!! you have loose $amount"
        val btn:TextView=dialog.findViewById(R.id.btn_spin_more)
        btn.setOnClickListener {
            updatecoin()
            dialog.dismiss() }
        dialog.create()
        dialog.show()


    }
    fun closeapp(){
        val dialog= Dialog(this)
        dialog.setContentView( R.layout.close_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        val btncancel:TextView=dialog.findViewById( R.id.btnCancel)
        val btnExit:TextView=dialog.findViewById( R.id.btnExit)

        btncancel.setOnClickListener { dialog.dismiss() }
        btnExit.setOnClickListener { finishAffinity() }
        dialog.create()
        dialog.show()

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        closeapp()
    }
    fun updatecoin(){
        val previouscoin=Utils.getData(this,"coin")!!.toInt()
        val totalcoin=previouscoin+coin
        Utils.saveData(this,"coin",totalcoin.toString())
    binding.tcCoin.setText(totalcoin.toString())
    }
}