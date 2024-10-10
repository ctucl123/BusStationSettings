package com.ctucl.busstationsettings.ui.screens.DeviceScreens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ctucl.busstationsettings.domain.model.TransactionResponse
import com.ctucl.busstationsettings.ui.screens.DeviceScreenViewModel
import com.ctucl.busstationsettings.ui.widgets.CustomButton
import com.ctucl.busstationsettings.ui.widgets.TransactionCard

@Composable
fun CustomsScreen(viewModel: DeviceScreenViewModel){
    val data by viewModel.api_db_data.collectAsState(initial = emptyList<TransactionResponse>())
    val isLoading by viewModel.isLoading.collectAsState()
    var operation by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0C1927))){
        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Funciones Adicionales",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            CustomButton(
                text = "Ultimas 10 transacciones",
                onClick = {
                    operation = "last_transactions"
                    viewModel.fetchApiDb(operation)
                }
            )
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
                if (it.isNotEmpty()) {
                    Log.d("MyViewModel", "Response JSON: $it")
                    Box ()
                    {

                        LazyColumn {
                            items(it) { transaction ->
                                TransactionCard(transaction)
                            }
                        }
                    }
                }


            }
            
        }

    }

}


