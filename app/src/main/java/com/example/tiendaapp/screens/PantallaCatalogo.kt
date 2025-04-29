package com.example.tiendaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tiendaapp.R
import com.example.tiendaapp.model.Producto
import com.example.tiendaapp.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(
    viewModel: CarritoViewModel,
    onNavigateToAgregarProducto: () -> Unit,
    onNavigateToCarrito: () -> Unit,
    onNavigateToDetalle: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Productos") },
                actions = {
                    IconButton(onClick = onNavigateToCarrito) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Ir al carrito"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAgregarProducto) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Text(
                text = "Total del carrito: $${String.format("%.2f", viewModel.totalCarrito)}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )


            if (viewModel.productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos en la tienda",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn {
                    items(viewModel.productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onClick = { onNavigateToDetalle(producto.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(producto.imagenUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.ic_image_placeholder)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

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