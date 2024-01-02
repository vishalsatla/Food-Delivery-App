package uk.ac.tees.mad.w9617154.foodapp.views.notes


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController) {

    LaunchedEffect(Unit) {
        navController.navigate("recipescreen")
    }
}