package com.ctucl.busstationsettings.ui.screens.DeviceScreens
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun NormalDoorScreen(viewModel: DeviceScreenViewModel){
    val data by viewModel.api_data.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var operation by remember { mutableStateOf("") }
    var serialData by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0C1927))){
        Column(modifier =
                        Modifier.fillMaxWidth()
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
        ){
            Text(
                text = "Puerta Normal",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            Image(painter = painterResource(id = R.drawable.people_svgrepo_com), contentDescription = "puerta normal", modifier = Modifier.fillMaxWidth() )

            CustomButton(text = "Testear Cerradura", onClick = {
                operation = "test_lock"
                viewModel.fetchApiAction("test_lock")

            })
            CustomButton(text = "Testear Luz Led", onClick = {
                operation = "test_arrow"
                viewModel.fetchApiAction("test_arrow")
            })
            CustomButton(text = "Leer Sensor",
                onClick = {
                operation = "read_sensor"
                viewModel.fetchApiAction("read_sensor")
            })
            CustomButton(text = "Leer RS232",
                onClick = {
                operation = "read_serial"
                viewModel.fetchApiAction("read_serial")
            })
            CustomButton(text = "Generar Pase",
                onClick = {
                operation = "generate_normal_pass"
                viewModel.fetchApiAction("generate_normal_pass")
            }
            )

            SensorCard(
                message = serialData,
            )

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
        if(operation == "read_serial"){
            serialData = it.result.toString()
        }
        viewModel.clearData()
    }
}

@Composable
fun SensorCard(message: String) {
    Card(
        modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5)) // Color azul
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Serial",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Data: $message",
                fontSize = 16.sp,
                color = Color.White
            )

        }
    }
}