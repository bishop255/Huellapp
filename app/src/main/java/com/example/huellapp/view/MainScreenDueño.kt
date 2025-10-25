package com.example.huellapp.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsWalk


@Composable
fun MainScreenDueno(navController: NavHostController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "mis_perros",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("mis_perros") { MisPerrosScreen() }
            composable("paseadores") { PaseadoresScreen() }
            composable("mis_paseos") { MisPaseosScreen() }
            composable("perfil") { PerfilScreen(navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Mis Perros", Icons.Filled.Pets, "mis_perros"),
        BottomNavItem("Paseadores", Icons.Filled.DirectionsWalk, "paseadores"),
        BottomNavItem("Mis Paseos", Icons.Filled.CalendarMonth, "mis_paseos"),
        BottomNavItem("Perfil", Icons.Filled.Person, "perfil")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)

@Composable
fun MisPerrosScreen() {
    Text("Aquí verás a tus perros 🐾")
}

@Composable
fun PaseadoresScreen() {
    Text("Lista de paseadores disponibles 🚶‍♂️")
}

@Composable
fun MisPaseosScreen() {
    Text("Historial y próximos paseos 📅")
}

@Composable
fun PerfilScreen(navController: NavHostController) {
    Text("Tu perfil y configuración 👤")
}
