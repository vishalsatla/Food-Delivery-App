package uk.ac.tees.mad.w9617154.foodapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.w9617154.foodapp.R
import uk.ac.tees.mad.w9617154.foodapp.ui.theme.FoodApp
import uk.ac.tees.mad.w9617154.foodapp.util.InputValidator

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    maxLines: Int = 1,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorMessage: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(horizontalAlignment = Alignment.Start) {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            shape = RoundedCornerShape(10.dp),
            placeholder = { Text(text = placeholder) },
            maxLines = maxLines,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = errorMessage != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            visualTransformation =
            if (isPassword) PasswordVisualTransformation()
            else VisualTransformation.None
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage != null) Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = errorMessage,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    FoodApp(darkTheme = true) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var emailErrorMsg: String? by remember { mutableStateOf(null) }
        var passwordErrorMsg: String? by remember { mutableStateOf(null) }
        var passwordVisibility by remember { mutableStateOf(true) }

        val icon = if (passwordVisibility) ImageVector.vectorResource(R.drawable.ic_eye_open)
        else ImageVector.vectorResource(R.drawable.ic_eye_closed)

        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .fillMaxSize()) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = {
                    email = it
                    emailErrorMsg = InputValidator.validateEmail(it)
                },
                placeholder = "Email",
                errorMessage = emailErrorMsg,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                    passwordErrorMsg = InputValidator.validatePassword(it)
                },
                placeholder = "Password",
                errorMessage = passwordErrorMsg,
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "ic_arrow_right",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                isPassword = passwordVisibility,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CustomTextFieldDisabledPreview() {
    FoodApp {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        Column {
            CustomTextField(
                modifier = Modifier.padding(10.dp),
                value = password,
                onValueChange = { password = it },
                placeholder = "Email",
                enabled = true,
            )
            CustomTextField(
                modifier = Modifier.padding(10.dp),
                value = email,
                onValueChange = { email = it },
                placeholder = "Password",
                enabled = true,
            )
        }
    }
}