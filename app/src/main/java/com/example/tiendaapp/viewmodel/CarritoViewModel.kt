package com.example.tiendaapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tiendaapp.model.Producto

class CarritoViewModel : ViewModel() {
    var productos = mutableStateListOf<Producto>()
    private var ultimoId = 0

    val productosEnCarrito = mutableStateListOf<Producto>()
    var totalCarrito by mutableStateOf(0.0)
        private set

    fun agregarProducto(nombre: String, precio: Double, descripcion: String, imagenUrl: String) {
        val nuevoProducto = Producto(
            id = ++ultimoId,
            nombre = nombre,
            precio = precio,
            descripcion = descripcion,
            imagenUrl = imagenUrl
        )
        productos.add(nuevoProducto)
    }

    fun agregarAlCarrito(producto: Producto) {
        productosEnCarrito.add(producto)
        actualizarTotal()
    }

    fun finalizarCompra() {
        productosEnCarrito.clear()
        actualizarTotal()
    }

    private fun actualizarTotal() {
        totalCarrito = productosEnCarrito.sumOf { it.precio }
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }
}