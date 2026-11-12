package com.reelbanao.app.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.theme.*

@Composable
fun AppBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onCenterActionClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .background(SurfaceDark)
            .border(width = 1.dp, color = DividerDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = currentRoute == "home",
                onClick = { onNavigate("home") }
            )

            BottomNavItem(
                icon = Icons.Default.VideoLibrary,
                label = "Templates",
                isSelected = currentRoute == "templates",
                onClick = { onNavigate("templates") }
            )

            // Center Floating Action Button (New Project / AI Quick Edit)
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .offset(y = (-8).dp)
                    .shadow(12.dp, CircleShape)
                    .clip(CircleShape)
                    .background(CapCutGradient)
                    .clickable(onClick = onCenterActionClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Project",
                    tint = TextWhite,
                    modifier = Modifier.size(28.dp)
                )
            }

            BottomNavItem(
                icon = Icons.Default.Folder,
                label = "Projects",
                isSelected = currentRoute == "my_projects",
                onClick = { onNavigate("my_projects") }
            )

            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = currentRoute == "profile",
                onClick = { onNavigate("profile") }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tintColor by animateColorAsState(
        targetValue = if (isSelected) AccentCyan else TextMuted,
        label = "navColor"
    )

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tintColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = tintColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

