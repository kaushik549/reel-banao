package com.reelbanao.app.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.AppTopBar
import com.reelbanao.app.components.FilterChipPill
import com.reelbanao.app.components.GradientButton
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExportScreen(
    projectId: String,
    projectRepository: ProjectRepository,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val project = projectRepository.getProjectById(projectId)

    var selectedResolution by remember { mutableStateOf("1080p") } // 720p, 1080p, 4K
    var selectedFps by remember { mutableIntStateOf(30) } // 24, 30, 60
    var bitrateMb by remember { mutableFloatStateOf(12f) }

    // Export progress simulation state
    var isExporting by remember { mutableStateOf(false) }
    var exportProgress by remember { mutableFloatStateOf(0f) }
    var exportDone by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val resolutions = listOf("720p (HD)", "1080p (Full HD)", "4K (Ultra HD)")
    val fpsOptions = listOf(24, 30, 60)

    val estimatedMb = remember(selectedResolution, selectedFps, bitrateMb) {
        val mult = when (selectedResolution) {
            "4K (Ultra HD)" -> 3.2f
            "720p (HD)" -> 0.6f
            else -> 1.0f
        }
        String.format("%.1f", (bitrateMb * 2.5f * mult))
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Export Video • वीडियो एक्सपोर्ट",
                onBackClick = onBackClick
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            if (!exportDone) {
                // Resolution Selector
                Column {
                    Text(text = "Resolution (वीडियो क्वालिटी):", fontSize = 13.sp, color = TextWhite, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        resolutions.forEach { res ->
                            FilterChipPill(
                                label = res,
                                isSelected = selectedResolution == res,
                                onClick = { selectedResolution = res }
                            )
                        }
                    }
                }

                // Frame Rate (FPS) Selector
                Column {
                    Text(text = "Frame Rate (FPS - स्मूथनेस):", fontSize = 13.sp, color = TextWhite, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        fpsOptions.forEach { fps ->
                            FilterChipPill(
                                label = "${fps} FPS",
                                isSelected = selectedFps == fps,
                                onClick = { selectedFps = fps }
                            )
                        }
                    }
                }

                // Bitrate Slider
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Bitrate (बिटरेट):", fontSize = 13.sp, color = TextWhite, fontWeight = FontWeight.Bold)
                        Text(text = "${bitrateMb.toInt()} Mbps", color = AccentCyan, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = bitrateMb,
                        onValueChange = { bitrateMb = it },
                        valueRange = 4f..35f,
                        colors = SliderDefaults.colors(thumbColor = AccentCyan, activeTrackColor = AccentCyan)
                    )
                }

                // File Size Estimate Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(CardDark)
                        .border(1.dp, CardStroke, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Estimated File Size", color = TextGray, fontSize = 12.sp)
                            Text(text = "$estimatedMb MB", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 16.sp)
                        }
                        Text(
                            text = "Format: MP4 (H.264)",
                            color = AccentCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Progress Bar if Exporting
                if (isExporting) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(CardDark)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                progress = { exportProgress },
                                color = AccentGreen,
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Exporting Video... ${(exportProgress * 100).toInt()}%",
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "कृपया स्क्रीन बंद न करें",
                                color = TextGray,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Start Export Button
                GradientButton(
                    text = if (isExporting) "रेंडर हो रहा है..." else "Export Reel • एक्सपोर्ट शुरू करें",
                    enabled = !isExporting,
                    gradient = ExportGreenGradient,
                    onClick = {
                        isExporting = true
                        coroutineScope.launch {
                            for (i in 1..10) {
                                delay(220)
                                exportProgress = i / 10f
                            }
                            isExporting = false
                            exportDone = true
                        }
                    }
                )
            } else {
                // Export Completed Screen
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = AccentGreen,
                        modifier = Modifier.size(72.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Video Exported Successfully! 🎉",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "वीडियो आपकी गैलरी में सेव हो गया है।",
                        color = TextGray,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Social Share Buttons (Instagram Reels, WhatsApp, YouTube)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SharePill(label = "Instagram", color = AccentPink)
                        SharePill(label = "WhatsApp", color = AccentGreen)
                        SharePill(label = "Shorts", color = Color(0xFFFF0000))
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    GradientButton(
                        text = "Done • होम स्क्रीन पर जाएं",
                        onClick = onDoneClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SharePill(label: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(text = label, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

