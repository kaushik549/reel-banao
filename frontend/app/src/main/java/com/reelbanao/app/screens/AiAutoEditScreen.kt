package com.reelbanao.app.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.AppTopBar
import com.reelbanao.app.components.FilterChipPill
import com.reelbanao.app.components.GradientButton
import com.reelbanao.app.data.DummyData
import com.reelbanao.app.models.AiEditStyle
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AiAutoEditScreen(
    projectRepository: ProjectRepository,
    onBackClick: () -> Unit,
    onVideoGenerated: (String) -> Unit
) {
    var selectedMediaCount by remember { mutableIntStateOf(5) }
    var selectedStyleId by remember { mutableStateOf("ai_beat_punch") }
    var selectedMood by remember { mutableStateOf("Trending Beats") }
    var isBeatSyncEnabled by remember { mutableStateOf(true) }

    // Generation workflow state
    var isGenerating by remember { mutableStateOf(false) }
    var generationStep by remember { mutableStateOf("") }
    var generationProgress by remember { mutableStateOf(0f) }
    var isCompleted by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val moods = listOf("Trending Beats", "Lo-Fi Aesthetic", "Phonk Gym", "Romantic Acoustic", "Bollywood Viral")

    Scaffold(
        topBar = {
            AppTopBar(
                title = "AI Auto Video Creator",
                onBackClick = onBackClick
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Explicit AI Architecture Notice
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardDark)
                        .border(1.dp, CardStroke, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = AccentCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Phase 1 UI Prototype (Frontend Model)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentCyan
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "यह स्क्रीन AI वर्कफ़्लो का पूरा इंटरएक्टिव UI दिखाती है। एक्चुअल AI कट डिटेक्शन व क्लाउड मॉडल Phase 2 (Node.js बैकएंड) में इंटीग्रेट होगा।",
                                fontSize = 11.sp,
                                color = TextGray,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // Step 1: Select Media
            item {
                Column {
                    Text(
                        text = "1. Select Photos & Videos (मीडिया चुनें)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(CardDark)
                            .border(1.dp, CardStroke, RoundedCornerShape(14.dp))
                            .clickable {
                                selectedMediaCount = if (selectedMediaCount >= 10) 3 else selectedMediaCount + 2
                            }
                            .padding(14.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryPurple.copy(alpha = 0.3f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = AccentCyan)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = "$selectedMediaCount Items Selected", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 14.sp)
                                    Text(text = "Tap to change or pick more clips", color = TextGray, fontSize = 11.sp)
                                }
                            }
                            Text(text = "Change", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Step 2: Choose AI Editing Style
            item {
                Column {
                    Text(
                        text = "2. Select AI Editing Style (स्टाइल चुनें)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    DummyData.aiStyles.forEach { style ->
                        val isSelected = selectedStyleId == style.id
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) CardDark else Color(0xFF191B24))
                                .border(
                                    1.dp,
                                    if (isSelected) AccentCyan else CardStroke,
                                    RoundedCornerShape(14.dp)
                                )
                                .clickable { selectedStyleId = style.id }
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = style.name, fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 14.sp)
                                    if (isSelected) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = style.description, color = TextGray, fontSize = 11.sp, lineHeight = 16.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    style.tags.forEach { tag ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(PrimaryPurple.copy(alpha = 0.25f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(text = tag, fontSize = 9.sp, color = AccentCyan)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Step 3: Music Mood & Beat Sync Switch
            item {
                Column {
                    Text(
                        text = "3. Select Music Mood (म्यूजिक व बीट)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(moods) { mood ->
                            FilterChipPill(
                                label = mood,
                                isSelected = selectedMood == mood,
                                onClick = { selectedMood = mood }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Beat Sync Switch
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.GraphicEq, contentDescription = null, tint = AccentPink)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(text = "Smart Beat-Sync (बीट मैचिंग)", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 13.sp)
                                    Text(text = "वीडियो कट्स को ऑटोमेटिक बीट ड्रॉप पर अलाइन करें", color = TextGray, fontSize = 10.sp)
                                }
                            }
                            Switch(
                                checked = isBeatSyncEnabled,
                                onCheckedChange = { isBeatSyncEnabled = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = TextWhite,
                                    checkedTrackColor = AccentPink
                                )
                            )
                        }
                    }
                }
            }

            // AI Generation Progress Animation Box
            if (isGenerating) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(CardDark)
                            .border(1.dp, PrimaryPurple, RoundedCornerShape(16.dp))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                progress = { generationProgress },
                                color = AccentCyan,
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(52.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = generationStep,
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${(generationProgress * 100).toInt()}% Done",
                                color = AccentCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Big Action Button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                GradientButton(
                    text = if (isGenerating) "AI Video Ban Rahi Hai..." else "Generate AI Video • ऑटो वीडियो बनाएं",
                    enabled = !isGenerating,
                    icon = Icons.Default.AutoAwesome,
                    onClick = {
                        isGenerating = true
                        coroutineScope.launch {
                            generationStep = "1/4: Analyzing audio beats & waveforms..."
                            generationProgress = 0.25f
                            delay(800)
                            generationStep = "2/4: Smart face & subject cut detection..."
                            generationProgress = 0.55f
                            delay(900)
                            generationStep = "3/4: Applying dynamic zoom & color grade..."
                            generationProgress = 0.85f
                            delay(700)
                            generationStep = "4/4: Finalizing video timeline!"
                            generationProgress = 1.0f
                            delay(500)
                            isGenerating = false

                            // Create project and open editor
                            val project = projectRepository.createNewProject("AI Auto Edit ($selectedMood)")
                            onVideoGenerated(project.id)
                        }
                    }
                )
            }
        }
    }
}

