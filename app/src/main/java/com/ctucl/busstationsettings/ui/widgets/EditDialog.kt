package com.ctucl.busstationsettings.ui.widgets

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.ctucl.busstationsettings.domain.model.DeviceController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(activate:Boolean,onClose: () -> Unit,onConfirm:(DeviceController)-> Unit,selectedDevice:DeviceController){
    var IpDevice by remember { mutableStateOf(selectedDevice.ipAddress) }
    var DescriptionDevice by remember { mutableStateOf(selectedDevice.description) }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (activate) {
        AlertDialog(
            onDismissRequest = onClose,
            title = {
                Text(text = "Edita el Dispositivo")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = IpDevice,
                        onValueChange = {
                                newText ->
                            IpDevice = newText
                            isError = !isValidIP(newText)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = isError,
                        label = {
                            Text(text = "Ip")
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (isError) Color.Red else Color(0xFF58d68d ),
                            unfocusedBorderColor = if (isError) Color.Red else Color(0xFF3498db ),
                            unfocusedTextColor = Color(0xFF3498db ),
                            unfocusedLabelColor = Color(0xFF3498db ),
                            focusedTextColor = Color(0xFF58d68d ),
                            cursorColor = Color(0xFF58d68d ),
                            focusedLabelColor = Color(0xFF58d68d ),
                            errorTextColor = Color.Red
                        )
                    )
                    OutlinedTextField(
                        value = DescriptionDevice,
                        onValueChange = { DescriptionDevice = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Descripcion")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF58d68d ),
                            unfocusedBorderColor = Color(0xFF3498db ),
                            unfocusedTextColor = Color(0xFF3498db ),
                            unfocusedLabelColor = Color(0xFF3498db ),
                            focusedTextColor = Color(0xFF58d68d ),
                            cursorColor = Color(0xFF58d68d ),
                            focusedLabelColor = Color(0xFF58d68d ),
                        )
                    )
                }

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if(!isValidIP(IpDevice) || DescriptionDevice.isEmpty() ){
                            Toast.makeText(context, "Invalid IP Address o Description", Toast.LENGTH_SHORT).show()
                        }else{
                            onConfirm(DeviceController(
                                IpDevice,
                                DescriptionDevice
                            ))
                        }
                    }
                ) {
                    Text("Actualizar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClose
                ) {
                    Text("Cancel")
                }
            }
        )
    }

}

fun isValidIP(ip: String): Boolean {
    val ipRegex = Regex(pattern =
    "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$"
    )
    return ipRegex.matches(ip)
}
