package com.example.jetsurveyme.repository.impl

import android.util.Log
import com.example.jetsurveyme.repository.AuthRepository
import com.example.jetsurveyme.util.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :AuthRepository{
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    fun checkIfEmailExists(email:String){

        firebaseAuth.signInWithEmailAndPassword(email,"")
            .addOnFailureListener { task->
                if(task.message == "email-already-in-use"){
                    Log.d("failure listener","${task.message} email exists")
                }else{
                    Log.d("failure listener","${task.message} email does not exist")
                }

            }


    }


}