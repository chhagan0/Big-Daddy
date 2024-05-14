package com.nkomapp.rupeequiz.Utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity


object Utils {
    fun saveData(context: Context,key:String,value:String){
        val sharedpref=context.getSharedPreferences("mySharedPref",Context.MODE_PRIVATE)
        val editor=sharedpref.edit()
        editor.putString(key,value)
        editor.apply()
    }
    fun getData(context: Context,key: String):String?{
        val sharedpref=context.getSharedPreferences("mySharedPref",Context.MODE_PRIVATE)
        return sharedpref.getString(key,null)

    }


}