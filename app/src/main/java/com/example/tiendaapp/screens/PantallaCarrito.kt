package com.example.tiendaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tiendaapp.R
import com.example.tiendaapp.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito(
    viewModel: CarritoViewModel,
    onNavigateBack: () -> Unit
) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
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
        ) {
            if (viewModel.productosEnCarrito.isEmpty()) {
                // Carrito vacío
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "El carrito está vacío",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            } else {
                // Lista de productos en el carrito
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(viewModel.productosEnCarrito) { producto ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Imagen del producto
                                AsyncImage(
                                    model = producto.imagenUrl,
                                    contentDescription = producto.nombre,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    error = painterResource(id = R.drawable.ic_broken_image)
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                // Información del producto
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = producto.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "$${String.format("%.2f", producto.precio)}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                // Resumen y botón finalizar compra
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total a pagar:",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "$${String.format("%.2f", viewModel.totalCarrito)}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { mostrarConfirmacion = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Finalizar Compra")
                        }
                    }
                }
            }
        }

        if (mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacion = false },
                title = { Text("Confirmar compra") },
                text = { Text("¿Estás seguro de que deseas finalizar la compra por $${String.format("%.2f", viewModel.totalCarrito)}?") },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.finalizarCompra()
                            mostrarConfirmacion = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { mostrarConfirmacion = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
