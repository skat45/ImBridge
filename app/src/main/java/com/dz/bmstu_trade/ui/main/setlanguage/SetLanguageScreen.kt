package com.dz.bmstu_trade.ui.main.setlanguage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dz.bmstu_trade.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLanguage(
    navController: NavHostController,
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val currentLocale by languageViewModel.currentLocale.collectAsState()
    Column {
        TopAppBar(
            title = { Text(text = stringResource(R.string.change_language)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon_description)
                    )
                }
            }
        )
        val items = mapOf(
            "ru" to stringResource(R.string.russian),
            "en" to stringResource(R.string.english),
            "fr" to stringResource(R.string.french)
        )


        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(items.keys.size) { index ->
                val key = items.keys.elementAt(index).toString()
                Box(modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                        languageViewModel.setLanguage(key)
                    }
                    .fillMaxWidth()) {
                    ListItem(text = items.getValue(key))
                }
            }
        }

    }


}

@Composable
fun ListItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(16.dp)
    )
}
