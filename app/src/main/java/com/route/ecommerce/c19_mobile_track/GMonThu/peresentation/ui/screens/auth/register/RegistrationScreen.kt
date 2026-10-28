package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.ecommerce.c19_mobile_track.GMonThu.R
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.LocalNavController
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.navigation.AppRoutes
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.utils.AuthButton
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils.AuthTextField

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val colorScheme = MaterialTheme.colorScheme
    val viewModel: RegistrationContract.ViewModel = hiltViewModel<RegistrationViewModel>()
    val state = viewModel.states.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                RegistrationContract.Event.NavigateToHome -> navController.navigate(AppRoutes.Main.Home) {
                    popUpTo(AppRoutes.Auth.Login) {
                        inclusive = true
                    }
                }

                is RegistrationContract.Event.ShowToast ->
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.primary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Image(
            painter = painterResource(R.drawable.logo_white),
            contentDescription = stringResource(R.string.app_development_image),
            modifier = Modifier
                .padding(top = 48.dp, bottom = 32.dp)
                .fillMaxHeight(0.1F),
        )
        // Name
        AuthTextField(
            text = name,
            onValueChange = {
                name = it
                viewModel.invokeActions(RegistrationContract.Action.UpdateName(it))
            },
            placeholder = stringResource(R.string.enter_your_full_name),
            label = stringResource(R.string.full_name),
            modifier = Modifier,
        )
        // Phone
        AuthTextField(
            text = phone,
            onValueChange = {
                phone = it
                viewModel.invokeActions(RegistrationContract.Action.UpdatePhone(it))
            },
            placeholder = stringResource(R.string.enter_your_phone),
            label = stringResource(R.string.phone),
            modifier = Modifier.padding(top = 20.dp),
        )
        // Email Address
        AuthTextField(
            text = email,
            onValueChange = {
                email = it
                viewModel.invokeActions(RegistrationContract.Action.UpdateEmail(it))
            },
            placeholder = stringResource(R.string.enter_your_mail_address),
            label = stringResource(R.string.email_address),
            modifier = Modifier.padding(top = 20.dp),
        )
        // Password
        AuthTextField(
            text = password,
            onValueChange = {
                password = it
                viewModel.invokeActions(RegistrationContract.Action.UpdatePassword(it))
            },
            placeholder = stringResource(R.string.enter_your_password),
            label = stringResource(R.string.password),
            modifier = Modifier.padding(top = 20.dp),
        )
        // Register Button
        AuthButton(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.register),
            isLoading = state.value is RegistrationContract.State.Loading,
        ) {
            viewModel.invokeActions(RegistrationContract.Action.SubmitRegister)
        }
    }
}
