package com.ctucl.busstationsettings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import com.ctucl.busstationsettings.data.local.DeviceDatabase
import com.ctucl.busstationsettings.data.local.DeviceRepositoryImpl

import com.ctucl.busstationsettings.ui.screens.DeviceScreen
import com.ctucl.busstationsettings.ui.screens.DeviceScreenViewModel
import com.ctucl.busstationsettings.ui.screens.DevicesViewModelFactory
import com.ctucl.busstationsettings.ui.screens.HomeScreenViewModel
import com.ctucl.busstationsettings.ui.screens.HomeViewScreen

class MainActivity : ComponentActivity() {
    private lateinit var deviceViewModel: HomeScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = DeviceDatabase.getDatabase(this)
        val repository = DeviceRepositoryImpl(database.deviceDao())
        deviceViewModel = DevicesViewModelFactory(repository).create(HomeScreenViewModel::class.java)
        val darkBlue = Color(0xFF0C1927)
        setContent {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = darkBlue
                ){
                    MyApp(homeScreenViewModel = deviceViewModel)
                }
        }
    }
}

@Composable
fun MyApp(homeScreenViewModel:HomeScreenViewModel) {
    val navController = rememberNavController()
    val deviceViewModel = DeviceScreenViewModel()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeViewScreen(homeScreenViewModel = homeScreenViewModel, navController = navController,viewModel =deviceViewModel )
        }
        composable("device") {
            DeviceScreen(navController = navController,viewModel =deviceViewModel )
        }
    }
}


