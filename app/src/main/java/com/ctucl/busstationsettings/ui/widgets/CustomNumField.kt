package com.ctucl.busstationsettings.ui.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumField(label: String, modifier: Modifier, onChange: (String) -> Unit, value:String) {

    OutlinedTextField(
        value = value,
        onValueChange = onChange ,
        label = { Text(label) },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(

            focusedBorderColor = Color(0xFF58d68d ),
            unfocusedBorderColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color(0xFF58d68d ),
            cursorColor = Color(0xFF58d68d ),
            focusedLabelColor = Color(0xFF58d68d ),
        )
    )
}