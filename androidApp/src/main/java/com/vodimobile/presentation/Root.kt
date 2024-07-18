package com.vodimobile.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.vodimobile.navigation.NavGraph
import com.vodimobile.presentation.components.BottomBar
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Root() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        NavGraph(
            navHostController = navController,
        )
    }
}

@Preview
@Composable
private fun RootPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.onPrimary) {
            Root()
        }
    }
}
