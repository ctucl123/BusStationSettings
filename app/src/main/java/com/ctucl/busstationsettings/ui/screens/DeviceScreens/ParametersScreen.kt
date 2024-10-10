package com.ctucl.busstationsettings.ui.screens.DeviceScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ctucl.busstationsettings.domain.model.DeviceParamsPost
import com.ctucl.busstationsettings.ui.screens.DeviceScreenViewModel
import com.ctucl.busstationsettings.ui.widgets.CustomButton
import com.ctucl.busstationsettings.ui.widgets.CustomNumField
import com.ctucl.busstationsettings.ui.widgets.CustomTextfield
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ParametersScreen (viewModel: DeviceScreenViewModel){
    val parametersDevice by viewModel.parametersDevice
    var timePuertaGeneral by remember { mutableStateOf(parametersDevice.time_turnstile) }
    var timePuertaEspecial by remember { mutableStateOf(parametersDevice.time_special_door) }
    var timeOpenSpecial by remember { mutableStateOf(parametersDevice.time_open_actuator) }
    var timeCloseSpecial by remember { mutableStateOf(parametersDevice.time_close_actuator) }
    var timeDelayTurnsTile by remember { mutableStateOf(parametersDevice.time_delay_turnstile) }
    var timeDelaySpecial by remember { mutableStateOf(parametersDevice.time_delay_special) }
    var uuid by remember { mutableStateOf(parametersDevice.uuid) }
    var place by remember { mutableStateOf(parametersDevice.place) }
    var lat by remember { mutableStateOf(parametersDevice.lat) }
    var lon by remember { mutableStateOf(parametersDevice.lon) }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0C1927))
    ){
        Column(
            modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
        ){
            Text(
                text = "Configuraciones Parada",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Puerta Normal",
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp) ,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                CustomNumField(
                    label = "Tiempo",
                    modifier = Modifier.weight(1f),
                    onChange = { timePuertaGeneral = it },
                    value = timePuertaGeneral
                    )

                CustomNumField(
                    label = "Retraso",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        timeDelayTurnsTile = it
                    },
                    value = timeDelayTurnsTile
                )

            }

            Text(
                text = "Puerta Especial",
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
                ){
                CustomNumField(
                    label = "Apertura",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        timeOpenSpecial = it
                    },
                    value = timeOpenSpecial
                )
                CustomNumField(
                    label = "Cierre",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        timeCloseSpecial = it
                    },
                    value = timeCloseSpecial
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                CustomNumField(
                    label = "Espera",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        timePuertaEspecial = it
                    },
                    value = timePuertaEspecial
                )
                CustomNumField(
                    label = "Retraso",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        timeDelaySpecial = it
                    },
                    value = timeDelaySpecial
                )
            }

            Text(
                text = "Base de Datos",
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

                CustomTextfield(
                    label = "ID",
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    onChange = {
                        uuid = it
                    },
                    value = uuid
                )

                CustomTextfield(label = "Lugar",
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    onChange = {
                        place = it
                    },
                    value = place
                )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                CustomTextfield(
                    label = "Latitud",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        lat = it
                    },
                    value = lat
                )
                CustomTextfield(
                    label = "Longitud",
                    modifier = Modifier.weight(1f),
                    onChange = {
                        lon = it
                    },
                    value = lon
                )
            }
            CustomButton(text = "Cargar Parametros", onClick = {
                val currentDateTime = LocalDateTime.now()
                val formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val data = DeviceParamsPost(
                    place = place,
                    time_turnstile = timePuertaGeneral.toIntOrNull() ?: 10,
                    time_open_actuator = timeOpenSpecial.toIntOrNull() ?: 5,
                    time_close_actuator = timeCloseSpecial.toIntOrNull() ?: 5,
                    time_special_door = timePuertaEspecial.toIntOrNull() ?: 5,
                    time_delay_turnstile = timeDelayTurnsTile.toIntOrNull() ?:1,
                    time_delay_special = timeDelaySpecial.toIntOrNull() ?:1,
                    date = formattedDateTime,  // Puedes agregar la fecha actual si lo necesitas
                    uuid = uuid,
                    lat = lat,
                    lon = lon
                )
                viewModel.sendParamsDeviceApi(data)
            })


        }


    }
}

