package com.ctucl.busstationsettings.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Widgets


sealed class NavScreen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object SpecialDoorScreen : NavScreen("device/special", "Especial", Icons.Filled.Accessible)
    object NormalDoorScreen : NavScreen("device/normal", "Normal", Icons.Filled.Accessibility)
    object ParametersScreen : NavScreen("device/params", "Parametros", Icons.Filled.Engineering)
    object CustomsScreen : NavScreen("device/customs", "Customs", Icons.Filled.Widgets)
}
