package com.learn.threadyt.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

object SharedPref  {
    fun storeData(
        email: String,
        name: String,
        username: String,
        bio: String,
        imageUrl: String,
        context: Context,
    ){
        val sharedPrefrance = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = sharedPrefrance.edit()

        editor.putString("name",name)
        editor.putString("username",username)
        editor.putString("bio",bio)
        editor.putString("email",email)
        editor.putString("imageurl",imageUrl)
        editor.apply()
    }

    fun getName(context: Context) :String{
        val sharedPrefrance = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPrefrance.getString("Username","")!!
    }
    fun getEmail(context: Context) :String{
        val sharedPrefrance = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPrefrance.getString("getEmail","")!!
    }
    fun getUsername(context: Context) :String{
        val sharedPrefrance = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPrefrance.getString("getUsername","")!!
    }
    fun getBio(context: Context) :String{
        val sharedPrefrance = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPrefrance.getString("getBio","")!!
    }
    fun getImage(context: Context) :String{
        val sharedPrefrance = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPrefrance.getString("getImage","")!!
    }

}