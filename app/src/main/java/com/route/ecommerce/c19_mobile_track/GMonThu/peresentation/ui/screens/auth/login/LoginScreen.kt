package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.LocalNavController
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.utils.AuthButton
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils.AuthTextField


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val viewModel: LoginContract.ViewModel = hiltViewModel<LoginViewModel>()
    val states = viewModel.states.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val navController = LocalNavController.current
    LaunchedEffect(states.value) {
        val state = states.value
        if (state is LoginContract.State.Error) {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(Unit) {
        val event = viewModel.events
        event.collect {
            when (it) {
                LoginContract.Event.NavigateToHome -> navController.navigate(AppRoutes.Main.Home) {
                    popUpTo(AppRoutes.Auth.Login) {
                        inclusive = true
                    }
                }

                LoginContract.Event.NavigateToRegistration -> navController.navigate(AppRoutes.Auth.Registration)
                is LoginContract.Event.ShowToast -> Toast.makeText(
                    context,
                    it.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.primary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_white),
            contentDescription = stringResource(R.string.app_development_image),
            modifier = Modifier.padding(top = 60.dp)
        )
        Text(
            text = stringResource(R.string.welcome_back_to_route),
            style = typography.titleLarge,
            color = colorScheme.secondary,
            modifier = Modifier
                .padding(top = 60.dp)
                .align(Alignment.Start)

        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.please_sign_in_with_your_mail),
            modifier = Modifier.align(Alignment.Start),
            style = typography.displaySmall,
            color = colorScheme.secondary,
        )
        // Email
        AuthTextField(
            text = email,
            onValueChange = {
                viewModel.invokeActions(LoginContract.Action.UpdateEmail(it))
                email = it
            },
            label = stringResource(R.string.email_address),
            placeholder = stringResource(R.string.enter_your_mail_address),
            modifier = Modifier.padding(vertical = 16.dp)
        )
        // Password Text Field
        AuthTextField(
            text = password,
            label = stringResource(R.string.password),
            onValueChange = {
                viewModel.invokeActions(LoginContract.Action.UpdatePassword(it))
                password = it
            }, placeholder = stringResource(R.string.enter_your_password),
            modifier = Modifier
        )
        AuthButton(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 20.dp)
                .fillMaxWidth(),
            isLoading = states.value is LoginContract.State.Loading,
            text = stringResource(R.string.login)
        ) {
            viewModel.invokeActions(LoginContract.Action.SubmitLogin)
        }
        Text(
            text = stringResource(R.string.don_t_have_an_account_create_account),
            style = typography.titleMedium,
            color = colorScheme.secondary,
            modifier = Modifier.clickable(true) {
                viewModel.invokeActions(LoginContract.Action.ClickedOnRegisterText)
            }
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}