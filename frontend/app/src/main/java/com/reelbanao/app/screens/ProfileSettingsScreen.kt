package com.reelbanao.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.AppBottomBar
import com.reelbanao.app.data.DummyData
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    projectRepository: ProjectRepository,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    val user = DummyData.currentUser
    var showThemeDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = "profile",
                onNavigate = onNavigate,
                onCenterActionClick = {
                    val newProj = projectRepository.createNewProject("New Reel")
                    onNavigate("video_editor/${newProj.id}")
                }
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Card Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(CardDark)
                        .border(1.dp, CardStroke, RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(ThemeManager.currentTheme.primaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = TextWhite, modifier = Modifier.size(36.dp))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = user.name, fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(AccentGreen.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(text = "FREE VIP", fontSize = 9.sp, color = AccentGreen, fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(text = user.username, color = TextGray, fontSize = 12.sp)
                        Text(text = user.email, color = TextMuted, fontSize = 11.sp)
                    }
                }
            }

            // Stats Row (Projects, Exports, Storage)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(title = "Projects", value = "${user.totalProjects}", modifier = Modifier.weight(1f))
                    StatCard(title = "Exports", value = "${user.totalExports}", modifier = Modifier.weight(1f))
                    StatCard(title = "Storage", value = "${user.storageUsedMb} MB", modifier = Modifier.weight(1f))
                }
            }

            // Storage Meter
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(CardDark)
                        .padding(14.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Device Cache & Drafts Storage", fontSize = 12.sp, color = TextWhite)
                            Text(text = "${user.storageUsedMb} MB / ${user.maxStorageMb} MB", fontSize = 12.sp, color = AccentCyan)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { user.storageUsedMb.toFloat() / user.maxStorageMb.toFloat() },
                            color = ThemeManager.currentTheme.primaryColor,
                            trackColor = DividerDark,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }
                }
            }

            // Settings Group
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(CardDark)
                ) {
                    // Theme Switcher Item
                    SettingItem(
                        icon = Icons.Default.Palette,
                        title = "App Theme (थीम बदलें)",
                        value = ThemeManager.currentTheme.hindiTitle,
                        tint = ThemeManager.currentTheme.primaryColor,
                        onClick = { showThemeDialog = true }
                    )
                    SettingItem(
                        icon = Icons.Default.Language,
                        title = "App Language (भाषा)",
                        value = "Hinglish / हिंदी"
                    )
                    SettingItem(
                        icon = Icons.Default.HighQuality,
                        title = "Default Export Quality",
                        value = "1080p @ 30FPS"
                    )
                    SettingItem(
                        icon = Icons.Default.CloudUpload,
                        title = "Cloud Project Backup",
                        value = "Phase 2 (Node.js API)"
                    )
                    SettingItem(
                        icon = Icons.Default.DeleteSweep,
                        title = "Clear Video Cache",
                        value = "Free 140 MB"
                    )
                    SettingItem(
                        icon = Icons.Default.Info,
                        title = "About Reel Banao",
                        value = "v1.0.0 Free Edition"
                    )
                    SettingItem(
                        icon = Icons.Default.Logout,
                        title = "Switch Account / Logout",
                        value = "",
                        tint = AccentPink,
                        onClick = onLogout
                    )
                }
            }
        }
    }

    // Theme Selection Dialog
    if (showThemeDialog) {
        BasicAlertDialog(
            onDismissRequest = { showThemeDialog = false }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(CardDark)
                    .border(1.dp, CardStroke, RoundedCornerShape(20.dp))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "Choose Theme • मनपसंद थीम चुनें",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontSize = 16.sp
                        )
                    )

                    AppThemeMode.entries.forEach { theme ->
                        val isSelected = ThemeManager.currentTheme == theme
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) theme.primaryColor.copy(alpha = 0.2f) else Color(0xFF1B1D28))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) theme.primaryColor else CardStroke,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    ThemeManager.setTheme(theme)
                                    showThemeDialog = false
                                }
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Color preview swatches
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(theme.primaryColor)
                                        .border(2.dp, theme.backgroundColor, CircleShape)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = theme.title,
                                        fontWeight = FontWeight.Bold,
                                        color = TextWhite,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = theme.hindiTitle,
                                        color = TextGray,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = theme.primaryColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .border(1.dp, CardStroke, RoundedCornerShape(14.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = title, color = TextGray, fontSize = 11.sp)
        }
    }
}

@Composable
private fun SettingItem(
    icon: ImageVector,
    title: String,
    value: String,
    tint: Color = AccentCyan,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(14.dp))
            Text(text = title, fontSize = 13.sp, color = TextWhite)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (value.isNotEmpty()) {
                Text(text = value, fontSize = 12.sp, color = TextGray)
                Spacer(modifier = Modifier.width(4.dp))
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
        }
    }
}
