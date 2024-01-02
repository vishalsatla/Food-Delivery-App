package uk.ac.tees.mad.w9617154.foodapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.w9617154.foodapp.viewModel.LoginViewModel
import uk.ac.tees.mad.w9617154.foodapp.views.login.BlankView
import uk.ac.tees.mad.w9617154.foodapp.views.login.TabsView
import uk.ac.tees.mad.w9617154.foodapp.views.recipies.HomeView
import com.example.recipeapp.Category
import com.example.recipeapp.CategoryDetailScreen
import com.example.recipeapp.MainViewModel
import com.example.recipeapp.RecipeScreen

@Composable
fun NavManager(loginViewModel: LoginViewModel) {
    
    val navController = rememberNavController()
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState
    NavHost(navController = navController, startDestination = "Blank" ){

        composable("Blank"){
           BlankView(navController)
        }
        composable("Login"){
            TabsView(navController,loginViewModel)
        }
        composable("Home"){
            HomeView(navController = navController)
        }
        composable("recipescreen"){
            RecipeScreen(navController, viewState = viewState) {
                //Pass data from current screen to the detail screen
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                navController.navigate("detailscreen")
            }
        }
        composable("detailscreen"){
            val category = navController.previousBackStackEntry?.savedStateHandle?.
            get<Category>("cat") ?: Category("", "", "", "")
            CategoryDetailScreen(category = category)
        }
    }
}