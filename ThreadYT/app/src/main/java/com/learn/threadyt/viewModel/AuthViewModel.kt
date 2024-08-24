package com.learn.threadyt.viewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.learn.threadyt.model.UserModel
import com.learn.threadyt.utils.SharedPref
import java.util.UUID

class AuthViewModel: ViewModel() {
    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val usersRef = db.getReference("users")

    private val storegeRef = FirebaseStorage.getInstance().reference
    private val imageRef = storegeRef.child("user/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser : LiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                }else{
                    _error.postValue(it.exception!!.message)
                }
            }
    }

    fun register(
        email: String,
        password: String,
        name: String,
        username: String,
        bio: String,
        imageuri: Uri?,
        context: Context
    ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)

                    getData(auth.currentUser!!.uid,context)
                    saveImage(email,name,username,bio,imageuri,auth.currentUser?.uid,context)
                }else{
                    _error.postValue("Somthing went wrong")
                }
            }
    }

    private fun getData(uid: String, context: Context) {
        usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(userData!!.name,userData!!.email,userData!!.bio,userData!!.username,userData!!.ImageUrl,context)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )
    }

    private fun saveImage(
        email: String,
        name: String,
        username: String,
        bio: String,
        imageuri: Uri?,
        uid: String?,
        context: Context
    ) {
        val uplodeTask = imageRef.putFile(imageuri!!)
        uplodeTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email,name,username,bio,it.toString(),uid,context)
            }
        }
    }

    private fun saveData(
        email: String,
        name: String,
        username: String,
        bio: String,
        toString: String,
        uid: String?,
        context: Context
    ){
        val userData = UserModel(email,name,username,bio,toString,uid!!)
        usersRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(name,email,bio,username,toString,context)

            }.addOnFailureListener{

            }
    }
    fun logout(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }
}