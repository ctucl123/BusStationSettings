package com.ctucl.busstationsettings.ui.screens.DeviceScreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ctucl.busstationsettings.R
import com.ctucl.busstationsettings.ui.screens.DeviceScreenViewModel
import com.ctucl.busstationsettings.ui.widgets.CustomButton

@Composable
fun SpecialDoorScreen(viewModel: DeviceScreenViewModel){
    val data by viewModel.api_data.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var operation by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0C1927))){
        Column(modifier =
        Modifier.fillMaxWidth()
                .verticalScroll(scrollState)
        ){
            Text(
                text = "Special Door",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.disability_svgrepo_com),
                    contentDescription = "puerta normal", modifier = Modifier.size(200.dp).align(Alignment.Center)
                )
            }
            CustomButton(text = "Abrir Puerta", onClick = {
                operation = "open_special_door"
                viewModel.fetchApiAction(operation)
            })
            CustomButton(text = "Cerrar Puerta", onClick = {
                operation = "close_special_door"
                viewModel.fetchApiAction(operation)
            })
            CustomButton(text = "Apagar Actuador", onClick = {
                operation = "actuador_off"
                viewModel.fetchApiAction(operation)
            })
            CustomButton(text = "Generar Pase Especial", onClick = {
                operation = "generate_special_pass"
                viewModel.fetchApiAction(operation)
            })

        }

    }
    if(isLoading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp // Puedes ajustar el grosor si lo prefieres
            )
        }
    }
    data?.let {

        Log.d("MyViewModel", "Response JSON: $it")
        Toast.makeText(context, "${it.result}", Toast.LENGTH_SHORT).show()
        viewModel.clearData()
    }
}