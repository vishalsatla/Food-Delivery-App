package uk.ac.tees.mad.w9617154.foodapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import uk.ac.tees.mad.w9617154.foodapp.navigation.NavManager
import uk.ac.tees.mad.w9617154.foodapp.theme.MyJornalFirebaseTheme
import uk.ac.tees.mad.w9617154.foodapp.viewModel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel : LoginViewModel by viewModels()

        setContent {
            MyJornalFirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  NavManager(loginViewModel = loginViewModel)
                }
            }
        }
    }
}
