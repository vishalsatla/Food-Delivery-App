package uk.ac.tees.mad.w9617154.foodapp.views.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import uk.ac.tees.mad.w9617154.foodapp.viewModel.LoginViewModel

@Composable
fun TabsView(navController: NavController,loginViewModel: LoginViewModel) {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Login","Register","ForgotPassword")

    Column {
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            //containerColor = Color.LightGray,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                )

            }) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title) }
                )

            }
        }
        when (selectedTab) {
            0 -> LoginView(navController,loginViewModel)
            1 -> RegisterView(navController,loginViewModel)
            2 -> ForgotPasswordView(navController,loginViewModel)
        }

    }
}


