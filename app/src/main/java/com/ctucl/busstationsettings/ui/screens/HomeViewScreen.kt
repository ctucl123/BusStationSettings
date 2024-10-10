package com.ctucl.busstationsettings.ui.screens
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ctucl.busstationsettings.data.local.DeviceEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Power
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.ctucl.busstationsettings.domain.model.DeviceController
import com.ctucl.busstationsettings.domain.model.DeviceParams
import com.ctucl.busstationsettings.ui.widgets.EditDialog


@Composable
fun HomeViewScreen(homeScreenViewModel: HomeScreenViewModel, navController: NavHostController,viewModel: DeviceScreenViewModel) {
    val devices by homeScreenViewModel.allDevices.collectAsState(initial = emptyList())
    val data by viewModel.data.collectAsState()
    var showRegisterDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedDevice by remember { mutableStateOf(DeviceController(ipAddress = "192.168.0.2", description = "none")) }

    Spacer(modifier = Modifier.height(16.dp))
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxWidth()){
            Text(text = "v1.3", color = Color.White)

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(devices) { item ->

                    ListItem(
                        item = item,
                        onClick = {
                                    viewModel.updateRetrofitBaseUrl(item.ip)
                                    Toast.makeText(context, "ip: ${item.ip}", Toast.LENGTH_SHORT).show()
                                    viewModel.fetchData()
                                },
                        onEdit = {
                            selectedDevice =  DeviceController(ipAddress = item.ip, description = item.description)
                            Log.d("AppParadas", "Selected: $item")
                            showEditDialog = true

                        },
                        onDelete = {
                            homeScreenViewModel.deleteDevice(item)
                        }
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(26.dp),
            onClick = {
                showRegisterDialog = true
            },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }

    }
    AlertDialogCustom(activate = showRegisterDialog, onClose = {showRegisterDialog = false}, onConfirm = {
        showRegisterDialog = false
        homeScreenViewModel.insert(DeviceEntity(description = it.description, ip = it.ipAddress))
    })
    EditDialog(activate = showEditDialog, onClose = { showEditDialog = false }, onConfirm ={} , selectedDevice = selectedDevice)
    if(isLoading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
        }
    }
    data?.let {
        viewModel.updateDevice( DeviceParams(
            time_turnstile = it.time_turnstile,
            time_special_door = it.time_special_door,
            time_open_actuator = it.time_open_actuator,
            time_close_actuator = it.time_close_actuator,
            time_delay_turnstile = it.time_delay_turnstile,
            time_delay_special = it.time_delay_special,
            place = it.place,
            uuid =  it.uuid,
            lat = it.lat,
            lon = it.lon
        ))
        Log.d("AppParadas", "Response JSON: $it")
        viewModel.clearData()
        navController.navigate("device")
    }

}

@Composable
fun ListItem(item: DeviceEntity,onClick: () -> Unit,onEdit:()->Unit,onDelete:()-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2e4053 ) // Color personalizado
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.ip,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ubicacion",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF3399FF)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =item.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF58d68d)
                )
            }
            Column{
                Button(
                    onClick = onClick,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector =Icons.Filled.Power,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp) // Tamaño del ícono
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Conectarse")
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                ){
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Icon",
                            tint = Color(0xfff1c40f)
                        )
                    }
                    IconButton(
                        onClick =  onDelete,
                        modifier = Modifier
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Icon",
                            tint = Color(0xfff1948a)
                        )
                    }
                }
            }


        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogCustom(activate:Boolean,onClose: () -> Unit,onConfirm:(DeviceController)-> Unit){
    var IpDevice by remember { mutableStateOf("") }
    var DescriptionDevice by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (activate) {
        AlertDialog(
            onDismissRequest = onClose,
            title = {
                Text(text = "Ingresa Dispositivo")
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
                            onConfirm(DeviceController(IpDevice,DescriptionDevice))
                            IpDevice = ""
                            DescriptionDevice = ""
                        }
                    }
                ) {
                    Text("Registrar")
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
    }else{
        IpDevice = ""
        DescriptionDevice = ""
    }

}

fun isValidIP(ip: String): Boolean {
    val ipRegex = Regex(pattern =
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\$"
    )
    return ipRegex.matches(ip)
}

