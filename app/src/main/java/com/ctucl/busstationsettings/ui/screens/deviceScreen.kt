package com.ctucl.busstationsettings.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ctucl.busstationsettings.navigation.NavScreen
import com.ctucl.busstationsettings.ui.screens.DeviceScreens.CustomsScreen
import com.ctucl.busstationsettings.ui.screens.DeviceScreens.NormalDoorScreen
import com.ctucl.busstationsettings.ui.screens.DeviceScreens.ParametersScreen
import com.ctucl.busstationsettings.ui.screens.DeviceScreens.SpecialDoorScreen


@Composable
fun DeviceScreen(navController: NavController,viewModel: DeviceScreenViewModel){
    val dashboardNavController = rememberNavController()
    val items = listOf(
        NavScreen.NormalDoorScreen,
        NavScreen.SpecialDoorScreen,
        NavScreen.ParametersScreen,
        NavScreen.CustomsScreen
    )
    Scaffold(
        modifier = Modifier.background(Color(0xFF0C1927)),
        bottomBar = {
            BottomNavigationBar(navController = dashboardNavController, items = items)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ChangeCircle,
                    contentDescription = "Agregar",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    ){
            innerPadding ->
        NavHost(
            navController = dashboardNavController,
            startDestination = NavScreen.NormalDoorScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavScreen.NormalDoorScreen.route) { NormalDoorScreen(viewModel = viewModel) }
            composable(NavScreen.SpecialDoorScreen.route) { SpecialDoorScreen(viewModel = viewModel) }
            composable(NavScreen.ParametersScreen.route) { ParametersScreen(viewModel = viewModel) }
            composable(NavScreen.CustomsScreen.route) { CustomsScreen(viewModel = viewModel) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, items: List<NavScreen>) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color(0xFF131619),
        contentColor = Color(0xFF58d68d),
        tonalElevation = 5.dp
    ) {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title, fontSize = 12.sp) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF2c3e50),
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                )
            )
        }
    }
}