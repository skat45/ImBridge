package com.dz.bmstu_trade.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem(stringResource(R.string.home_label), ImageVector.vectorResource(id = R.drawable.ic_home_filled_24), Routes.HOME.value),
        NavigationItem(stringResource(R.string.gallery_label), ImageVector.vectorResource(id = R.drawable.ic_gallery_24), Routes.GALLERY.value),
        NavigationItem(stringResource(R.string.settings_label), ImageVector.vectorResource(id = R.drawable.ic_settings_24), Routes.SETTINGS.value)
    )

    var selectedItem by remember { mutableStateOf<String>("home") }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { if (selectedItem == item.route) Text(item.title) },
                selected = selectedItem == item.route,
                onClick = {
                    if (selectedItem != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                    selectedItem = item.route
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                alwaysShowLabel = false
            )
        }
    }
}

data class NavigationItem(val title: String, val icon: ImageVector, val route:String)