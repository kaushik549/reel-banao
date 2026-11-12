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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.reelbanao.app.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TextSubtitleScreen(
    onBackClick: () -> Unit,
    onAddText: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("New Viral Reel ✨") }
    var selectedFont by remember { mutableStateOf("Roboto Bold") }
    var selectedColor by remember { mutableStateOf(Color.White) }
    var selectedAnimation by remember { mutableStateOf("Pop In") }

    // Auto Subtitle state
    var isGeneratingCaptions by remember { mutableStateOf(false) }
    var captionSuccess by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val fonts = listOf("Roboto Bold", "Bebas Neue", "Montserrat", "Khand (Hindi)", "Pacifico Script")
    val colors = listOf(Color.White, Color(0xFFFFEB3B), AccentCyan, AccentPink, AccentGreen, Color(0xFFFF9800))
    val animations = listOf("Pop In", "Typewriter", "Smooth Fade", "Bounce", "Word By Word")

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Text & Subtitles • टेक्स्ट व सबटाइटल",
                onBackClick = onBackClick
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Live Preview of Text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(CardDark)
                    .border(1.dp, CardStroke, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (textInput.isBlank()) "Type something..." else textInput,
                    color = selectedColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Input Field
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Enter Subtitle / Caption") },
                singleLine = false,
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AccentCyan,
                    unfocusedBorderColor = CardStroke,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedContainerColor = CardDark,
                    unfocusedContainerColor = CardDark
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Font Selector
            Column {
                Text(text = "Choose Font (फॉन्ट चुनें):", fontSize = 12.sp, color = TextWhite, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(fonts) { font ->
                        FilterChipPill(
                            label = font,
                            isSelected = selectedFont == font,
                            onClick = { selectedFont = font }
                        )
                    }
                }
            }

            // Color Selector
            Column {
                Text(text = "Text Color (कलर चुनें):", fontSize = 12.sp, color = TextWhite, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    colors.forEach { col ->
                        val isSelected = selectedColor == col
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(col)
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) TextWhite else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedColor = col }
                        )
                    }
                }
            }

            // Animation Selector
            Column {
                Text(text = "Caption Animation (एनिमेशन):", fontSize = 12.sp, color = TextWhite, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(animations) { anim ->
                        FilterChipPill(
                            label = anim,
                            isSelected = selectedAnimation == anim,
                            onClick = { selectedAnimation = anim }
                        )
                    }
                }
            }

            // AI Auto Subtitle / Speech-to-Text Generator Card (CapCut style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF1F2232))
                    .border(1.dp, PrimaryPurpleLight, RoundedCornerShape(14.dp))
                    .clickable {
                        isGeneratingCaptions = true
                        coroutineScope.launch {
                            delay(1500)
                            isGeneratingCaptions = false
                            captionSuccess = true
                            textInput = "🔥 AI Auto Captions: Ye reel mast bani hai!"
                        }
                    }
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AccentCyan)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "AI Auto Subtitles (ऑटो सबटाइटल)", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 13.sp)
                            Text(text = "आवाज़ सुनकर अपने आप हिंदी/English सबटाइटल बनाएगा", color = TextGray, fontSize = 10.sp)
                        }
                    }
                    if (isGeneratingCaptions) {
                        CircularProgressIndicator(color = AccentCyan, strokeWidth = 2.dp, modifier = Modifier.size(20.dp))
                    } else {
                        Text(text = "Generate", color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Add to Video Button
            GradientButton(
                text = "Add to Video • वीडियो में जोड़ें",
                onClick = {
                    onAddText(textInput)
                    onBackClick()
                }
            )
        }
    }
}

