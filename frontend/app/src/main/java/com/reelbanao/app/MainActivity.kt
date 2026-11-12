package com.reelbanao.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.reelbanao.app.navigation.ReelBanaoNavGraph
import com.reelbanao.app.theme.ReelBanaoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReelBanaoTheme {
                val navController = rememberNavController()
                ReelBanaoNavGraph(navController = navController)
            }
        }
    }
}

