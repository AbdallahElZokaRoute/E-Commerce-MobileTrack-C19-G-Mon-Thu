package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.ecommerce.c19_mobile_track.GMonThu.R

@Composable
fun SearchTextField(
    text: String,
    onValueChange: (String) -> Unit,
    @DrawableRes leadingIcon: Int?,
    placeholder: String?,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(0.86F)
            .border(
                1.dp,
                colorScheme.primary,
                RoundedCornerShape(24.dp),
            ),

        leadingIcon = {
            leadingIcon?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = null
                )
            }
        },
        shape = RoundedCornerShape(24.dp),
        placeholder = {
            placeholder?.let {
                Text(
                    text = it, color = colorScheme.tertiary, fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorScheme.primary,
            unfocusedTextColor = colorScheme.primary,
            errorTextColor = colorScheme.primary,
            disabledTextColor = colorScheme.primary,
            focusedContainerColor = colorScheme.secondary,
            unfocusedContainerColor = colorScheme.secondary,
            disabledContainerColor = colorScheme.secondary,
            errorContainerColor = colorScheme.secondary
        )

    )
}

@Composable
fun AuthTextField(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String?,
    label: String?,
    modifier: Modifier = Modifier

) {
    val typography = MaterialTheme.typography
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier.fillMaxWidth(1F)
    ) {
        label?.let {
            Text(
                text = it,
                style = typography.titleMedium,
                color = colorScheme.secondary
            )
        }
        Spacer(Modifier.height(16.dp))
        TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                placeholder?.let {
                    Text(
                        text = it, color = colorScheme.tertiary, fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = colorScheme.primary,
                unfocusedTextColor = colorScheme.primary,
                errorTextColor = colorScheme.primary,
                disabledTextColor = colorScheme.primary,
                focusedContainerColor = colorScheme.secondary,
                unfocusedContainerColor = colorScheme.secondary,
                disabledContainerColor = colorScheme.secondary,
                errorContainerColor = colorScheme.secondary
            )

        )
    }
}

@Preview
@Composable
private fun AuthTextFieldPreview() {
    AuthTextField(
        "Ahmed",
        onValueChange = {},
        placeholder = "",
        label = "Name",
        modifier = Modifier
    )
}

@Preview
@Composable
private fun SearchTextFieldPreview() {
    SearchTextField(
        "Ahmed",
        onValueChange = {},
        placeholder = "", leadingIcon = R.drawable.search,
        modifier = Modifier
    )
}

