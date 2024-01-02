package uk.ac.tees.mad.w9617154.foodapp.viewModel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.ac.tees.mad.w9617154.foodapp.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel :ViewModel(){

    private val auth :FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)


    fun login(email:String,password:String,onSuccess:()->Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                         onSuccess()
                        }else {
                            Log.d("ERROR EN JETPACK", "Error creating user")
                           showAlert  = true
                        }
                    }
            }catch (e:Exception){
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    fun register(email:String,password:String,username:String,onSuccess:()->Unit){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            saveUser(username)
                            onSuccess()
                        }else {
                            showAlert  = true
                        }
                    }
            }catch (e:Exception){
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }
    fun forgotPassword(email:String,onSuccess:()->Unit){
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task->
                        if(task.isSuccessful){
                            onSuccess()
                        }else {
                            Log.d("ERROR EN JETPACK", "Error sending email")
                            showAlert  = true
                        }
                    }
            }catch (e:Exception){
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")

            }
        }
    }
    private  fun saveUser(username:String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email
        viewModelScope.launch(Dispatchers.IO){
            val user = UserModel(
                userId =  id.toString(),
                email = email.toString(),
                username = username
            )

            FirebaseFirestore.getInstance().collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d("saving", "saved correctly")
                }.addOnFailureListener {
                    Log.d("Error when saving", "Error when saving a fireStore")
                }
        }

    }
    fun closeAlert(){
        showAlert = false
    }
}