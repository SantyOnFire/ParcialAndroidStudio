package com.example.tiendaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tiendaapp.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarProducto(
    viewModel: CarritoViewModel,
    onNavigateBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Formulario
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precioText,
                onValueChange = { precioText = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("URL de la imagen") },
                modifier = Modifier.fillMaxWidth()
            )

            if (showError) {
                Text(
                    text = "Todos los campos son obligatorios y el precio debe ser válido",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull()
                    if (nombre.isNotBlank() && precio != null && descripcion.isNotBlank() && imagenUrl.isNotBlank()) {
                        viewModel.agregarProducto(nombre, precio, descripcion, imagenUrl)
                        onNavigateBack()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Producto")
            }
        }
    }
}