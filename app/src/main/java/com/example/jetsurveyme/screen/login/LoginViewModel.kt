package com.example.jetsurveyme.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jetsurveyme.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginViewModel (): ViewModel(){
    private val auth: FirebaseAuth = Firebase.auth
    val db = Firebase.firestore
    fun checkIfEmailExists(email:String,toSignIn:()->Unit = {},toSignUp:()->Unit = {}){

       val list =  auth.fetchSignInMethodsForEmail(email)
        if (email.contains("signup")){
            toSignUp()
        }else{
            toSignIn()
        }

//        FirebaseFirestore.getInstance().collection("users")
//            .whereEqualTo("email",email)
//            .get()
//            .addOnSuccessListener {document->
//                if(document != null){
//                    next()
//                    Log.d("listener","$document email exists")
//                }
//
//            }
//            .addOnFailureListener {
//                Log.d("listener","$it email does not exist")
//            }

//        auth.createUserWithEmailAndPassword(email, null.toString())
//            .addOnFailureListener { task->
//                if(task.message == "The email address is already in use by another account."){
//                    Log.d("failure listener","${task.message} email exists")
//                    next()
//                }else{
//                    Log.d("failure listener","${task.message} email does not exist")
//                }
//
//            }


    }

    private fun createUser(email:String){
        val userId = auth.currentUser?.uid
        val user = MUser(id=null,
            userId = userId.toString(),
            email = email)

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}