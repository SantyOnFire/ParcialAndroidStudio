package com.example.tiendaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiendaapp.navigation.NavGraph
import com.example.tiendaapp.ui.theme.TiendaAppTheme
import com.example.tiendaapp.viewmodel.CarritoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TiendaAppTheme {
                val viewModel: CarritoViewModel = viewModel()
                NavGraph(viewModel = viewModel, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
