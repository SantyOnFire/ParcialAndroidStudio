package com.example.tiendaapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.tiendaapp.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleProducto(
    productoId: Int,
    viewModel: CarritoViewModel,
    onNavigateBack: () -> Unit
) {
    val producto = viewModel.obtenerProductoPorId(productoId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (producto != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen del producto
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(producto.imagenUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = producto.nombre,
                            contentScale = ContentScale.Fit,
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            },
                            error = {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BrokenImage,
                                        contentDescription = "Imagen no disponible",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(50.dp)
                                    )
                                    Text("Imagen no disponible")
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Información del producto
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "$${String.format("%.2f", producto.precio)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = producto.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón para agregar al carrito
                    Button(
                        onClick = {
                            viewModel.agregarAlCarrito(producto)
                            onNavigateBack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar al Carrito")
                    }
                }
            } else {
                // Producto no encontrado
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "Producto no encontrado",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Red
                    )
                }
            }
        }
    }
}
