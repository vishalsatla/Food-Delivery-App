package uk.ac.tees.mad.w9617154.foodapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.AuthViewModel
import uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.forget_password.ForgetPasswordScreen
import uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.forget_password.ForgetPasswordSuccessScreen
import uk.ac.tees.mad.w9617154.foodapp.ui.home_screen.HomeScreen
import uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.login_screen.LoginScreen
import uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.register_screen.RegisterScreen
import uk.ac.tees.mad.w9617154.foodapp.ui.create_profile_screen.CreateProfileScreen
import uk.ac.tees.mad.w9617154.foodapp.ui.onboarding_screen.OnBoardingViewModel
import uk.ac.tees.mad.w9617154.foodapp.ui.onboarding_screen.OnboardingScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navHostController,
        startDestination =
            if (authViewModel.isUserLoggedIn()) {
                if (onBoardingViewModel.readOnBoardingState()) Screen.HomeScreen.route
                else Screen.CreateProfileScreen.route
            } else Screen.OnboardingScreen.route
    ) {
        composable(route = Screen.OnboardingScreen.route) {
            OnboardingScreen(navController = navHostController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navHostController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navHostController)
        }
        composable(route = Screen.ForgetPasswordScreen.route) {
            ForgetPasswordScreen(navController = navHostController)
        }
        composable(route = Screen.ForgetPasswordSuccessScreen.route) {
            ForgetPasswordSuccessScreen(navController = navHostController)
        }
        composable(route = Screen.CreateProfileScreen.route) {
            CreateProfileScreen(navController = navHostController)
        }

    }
}