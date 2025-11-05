package com.example.huellapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huellapp.ui.theme.HuellappTheme
import com.example.huellapp.view.LoginScreen
import com.example.huellapp.view.MainScreenDueno
import com.example.huellapp.view.RegisterScreen
import com.example.huellapp.view.SplashScreen
import com.example.huellapp.view.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HuellappTheme {
                AppNavigation()
            }
        }
    }
}

// Navegación Compose
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable ("Welcome"){ WelcomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable ("register") { RegisterScreen(navController) }
        composable ("home") { MainScreenDueno(navController) }
        // Agregar más pantallas como Home, RegistrarPerro, etc.
    }
}
