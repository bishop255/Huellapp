package com.example.huellapp.view

import MisPerrosScreen
import com.example.huellapp.view.PaseadoresScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huellapp.viewmodel.PerroViewModel
import com.example.huellapp.viewmodel.PerroViewModelFactory
import com.example.huellapp.repository.PerroRepository
import com.example.huellapp.database.AppDatabase
import com.example.huellapp.view.MisPaseosScreen
import com.example.huellapp.view.PerfilScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DirectionsWalk

@Composable
fun MainScreenDueno(navController: NavHostController) {
    val bottomNavController = rememberNavController()


    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { PerroRepository(database.perroDao()) }
    val factory = remember { PerroViewModelFactory(repository) }


    val perroViewModel: PerroViewModel = viewModel(factory = factory)

    // Obtener la lista de perros UNA SOLA VEZ aquí
    val listaPerros by perroViewModel.perros.collectAsState()
    val listaNombresPerros = listaPerros.mapNotNull { it.nombre }

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
            composable("mis_perros") {
                MisPerrosScreen()
            }
            composable("paseadores") {
                // Simplemente pasar la lista ya obtenida
                PaseadoresScreen(listaPerros = listaNombresPerros)
            }
            composable("mis_paseos") {
                MisPaseosScreen()
            }
            composable("perfil") {
                PerfilScreen(navController)
            }
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
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)