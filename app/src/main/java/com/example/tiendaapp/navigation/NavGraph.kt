package com.example.tiendaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tiendaapp.screens.PantallaAgregarProducto
import com.example.tiendaapp.screens.PantallaCatalogo
import com.example.tiendaapp.screens.PantallaCarrito
import com.example.tiendaapp.screens.PantallaDetalleProducto
import com.example.tiendaapp.viewmodel.CarritoViewModel

sealed class Screen(val route: String) {
    object Catalogo : Screen("catalogo")
    object AgregarProducto : Screen("agregar_producto")
    object DetalleProducto : Screen("detalle_producto/{productoId}") {
        fun createRoute(productoId: Int) = "detalle_producto/$productoId"
    }
    object Carrito : Screen("carrito")
}

@Composable
fun NavGraph(viewModel: CarritoViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Catalogo.route,
        modifier = modifier
    ) {
        composable(Screen.Catalogo.route) {
            PantallaCatalogo(
                viewModel = viewModel,
                onNavigateToAgregarProducto = {
                    navController.navigate(Screen.AgregarProducto.route)
                },
                onNavigateToCarrito = {
                    navController.navigate(Screen.Carrito.route)
                },
                onNavigateToDetalle = { productoId ->
                    navController.navigate(Screen.DetalleProducto.createRoute(productoId))
                }
            )
        }

        composable(Screen.AgregarProducto.route) {
            PantallaAgregarProducto(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.DetalleProducto.route,
            arguments = listOf(
                navArgument("productoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            PantallaDetalleProducto(
                productoId = productoId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Carrito.route) {
            PantallaCarrito(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
