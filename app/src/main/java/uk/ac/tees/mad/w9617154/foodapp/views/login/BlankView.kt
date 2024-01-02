package uk.ac.tees.mad.w9617154.foodapp.views.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BlankView(navController: NavController) {

    LaunchedEffect(Unit) {
            navController.navigate("Login")
    }
}