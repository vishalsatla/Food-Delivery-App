package uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.login_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.w9617154.foodapp.R
import uk.ac.tees.mad.w9617154.foodapp.navigation.Screen
import uk.ac.tees.mad.w9617154.foodapp.ui.auth_screen.AuthViewModel
import uk.ac.tees.mad.w9617154.foodapp.ui.components.CustomAppBar
import uk.ac.tees.mad.w9617154.foodapp.ui.components.CustomProgressDialog
import uk.ac.tees.mad.w9617154.foodapp.ui.components.CustomTextField
import uk.ac.tees.mad.w9617154.foodapp.ui.theme.FoodApp
import uk.ac.tees.mad.w9617154.foodapp.ui.theme.neutral50
import uk.ac.tees.mad.w9617154.foodapp.ui.theme.poppins
import uk.ac.tees.mad.w9617154.foodapp.util.InputValidator
import uk.ac.tees.mad.w9617154.foodapp.util.WindowType
import uk.ac.tees.mad.w9617154.foodapp.util.rememberWindowInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val windowInfo = rememberWindowInfo()
    val coroutineScope = rememberCoroutineScope()

    val authState = authViewModel.authState.collectAsState(initial = null)
    val isNewUserState = authViewModel.isNewUserState.collectAsState(initial = null)
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailErrMsg: String? by rememberSaveable { mutableStateOf(null) }
    var passErrMsg: String? by rememberSaveable { mutableStateOf(null) }
    var passwordVisibility by rememberSaveable { mutableStateOf(true) }

    val icon =
        if (passwordVisibility) ImageVector.vectorResource(R.drawable.ic_eye_open)
        else ImageVector.vectorResource(R.drawable.ic_eye_closed)

    val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
            coroutineScope.launch {
                authViewModel.handleActivityResult(it)
            }
        }

    if (authState.value?.isLoading == true || isNewUserState.value?.isLoading == true) {
        CustomProgressDialog()
    }

    LaunchedEffect(key1 = authState.value?.success) {
        if(authState.value?.success != null) {
            coroutineScope.launch {
                authViewModel.isNewUser()
            }
        }
    }

    LaunchedEffect(key1 = authState.value?.fail) {
        if (authState.value?.fail != null) {
            Toast.makeText(context, authState.value?.fail, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = isNewUserState.value?.isNewUser) {
        if (isNewUserState.value?.isNewUser != null) {
            if (isNewUserState.value?.isNewUser == true) {
                navController.popBackStack()
                navController.navigate(Screen.CreateProfileScreen.route) {
                    popUpTo(Screen.OnboardingScreen.route) {
                        inclusive = true
                    }
                }
            } else {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.OnboardingScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = isNewUserState.value?.fail) {
        if (isNewUserState.value?.fail != null) {
            Toast.makeText(context, isNewUserState.value?.fail, Toast.LENGTH_LONG).show()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .padding(
                vertical =
                if (windowInfo.height == WindowType.COMPACT) 8.dp
                else 20.dp,
                horizontal = 8.dp
            )
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (
            topBarRef,
            descriptionRef,
            inputContainerRef,
            forgetPassRef,
            loginBtnRef,
            googleBtnRef,
            dividerRef,
            registerContainerRef,
            footerRef
        ) = createRefs()

        CustomAppBar(
            modifier = Modifier.constrainAs(topBarRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            navController = navController,
            title = stringResource(R.string.welcome_back),
        )

        Text(
            modifier = Modifier.constrainAs(descriptionRef) {
                top.linkTo(topBarRef.bottom, 20.dp)
                start.linkTo(parent.start)
            },
            text = stringResource(R.string.login_sub_title),
            color = neutral50
        )

        Column(
            modifier = Modifier
                .constrainAs(inputContainerRef) {
                    top.linkTo(
                        anchor = descriptionRef.bottom,
                        margin =
                        if (windowInfo.height == WindowType.COMPACT) 30.dp
                        else 50.dp
                    )
                }
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = {
                    email = it
                    emailErrMsg = InputValidator.validateEmail(it)
                },
                placeholder = stringResource(R.string.email_placeholder),
                errorMessage = emailErrMsg,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    passErrMsg = InputValidator.validatePassword(it)
                },
                placeholder = stringResource(R.string.password_placeholder),
                errorMessage = passErrMsg,
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "icon_eye",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                isPassword = passwordVisibility,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
            )
        }

        ClickableText(
            modifier = Modifier.constrainAs(forgetPassRef) {
                top.linkTo(inputContainerRef.bottom)
                end.linkTo(parent.end)
            },
            text = AnnotatedString(stringResource(R.string.forgot_password)),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium
            ),
            onClick = {
                navController.navigate(Screen.ForgetPasswordScreen.route)
            }
        )

        Button(
            modifier = Modifier
                .constrainAs(loginBtnRef) {
                    top.linkTo(
                        anchor = forgetPassRef.bottom,
                        margin =
                        if (windowInfo.height == WindowType.COMPACT) 30.dp
                        else 50.dp
                    )
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(50.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                coroutineScope.launch(Dispatchers.Main) {
                    authViewModel.loginWithEmailAndPassword(email, password)
                }
            },
            enabled =
                emailErrMsg == null &&
                passErrMsg == null &&
                email.isNotEmpty() &&
                password.isNotEmpty()
        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }

        Row(
            modifier = Modifier.constrainAs(dividerRef) {
                top.linkTo(
                    anchor = loginBtnRef.bottom,
                    margin =
                        if (windowInfo.height == WindowType.COMPACT) 25.dp
                        else 50.dp
                )
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 2.dp
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.or_login_with),
                fontWeight = FontWeight(600),
                color = Color(0xFF6A707C),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 2.dp
            )
        }

        Button(
            modifier = Modifier
                .constrainAs(googleBtnRef) {
                    top.linkTo(
                        anchor = dividerRef.bottom,
                        margin =
                        if (windowInfo.height == WindowType.COMPACT) 25.dp
                        else 50.dp
                    )
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(50.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            onClick = { authViewModel.googleLoginOnClick(context, launcher) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_google),
                contentDescription = "google_icon",
                tint = Color.Unspecified
            )
        }

        Row(
            modifier = Modifier.constrainAs(registerContainerRef) {
                top.linkTo(googleBtnRef.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(text = stringResource(R.string.don_t_have_an_account))
            ClickableText(
                text = AnnotatedString(stringResource(R.string.register_now)),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium
                ),
                onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }

        Text(
            modifier = Modifier.constrainAs(footerRef) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = buildAnnotatedString {
                append(stringResource(R.string.read))
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )) {
                    append(stringResource(R.string.privacy))
                }
                append(stringResource(R.string.and))
                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )) {
                    append(stringResource(R.string.policy))
                }
            }
        )
    }
}

//@Preview(showBackground = true, name = "NEXUS_7", device = Devices.NEXUS_7)
//@Preview(showBackground = true, name = "NEXUS_7_2013", device = Devices.NEXUS_7_2013)
@Preview(showBackground = true, name = "NEXUS_5", device = Devices.NEXUS_5)
//@Preview(showBackground = true, name = "NEXUS_6", device = Devices.NEXUS_6)
//@Preview(showBackground = true, name = "NEXUS_9", device = Devices.NEXUS_9)
//@Preview(showBackground = true, name = "NEXUS_10", device = Devices.NEXUS_10)
//@Preview(showBackground = true, name = "NEXUS_5X", device = Devices.NEXUS_5X)
//@Preview(showBackground = true, name = "NEXUS_6P", device = Devices.NEXUS_6P)
//@Preview(showBackground = true, name = "PIXEL_C", device = Devices.PIXEL_C)
//@Preview(showBackground = true, name = "PIXEL", device = Devices.PIXEL)
//@Preview(showBackground = true, name = "PIXEL_XL", device = Devices.PIXEL_XL)
//@Preview(showBackground = true, name = "PIXEL_2", device = Devices.PIXEL_2)
//@Preview(showBackground = true, name = "PIXEL_2_XL", device = Devices.PIXEL_2_XL)
//@Preview(showBackground = true, name = "PIXEL_3", device = Devices.PIXEL_3)
//@Preview(showBackground = true, name = "PIXEL_3_XL", device = Devices.PIXEL_3_XL)
//@Preview(showBackground = true, name = "PIXEL_3A", device = Devices.PIXEL_3A)
//@Preview(showBackground = true, name = "PIXEL_3A_XL", device = Devices.PIXEL_3A_XL)
//@Preview(showBackground = true, name = "PIXEL_4", device = Devices.PIXEL_4)
@Preview(showBackground = true, name = "PIXEL_4_XL", device = Devices.PIXEL_4_XL)
//@Preview(showBackground = true, name = "AUTOMOTIVE_1024p", device = Devices.AUTOMOTIVE_1024p)
@Composable
fun LoginScreenPreview() {
    FoodApp {
        LoginScreen(navController = rememberNavController())
    }
}