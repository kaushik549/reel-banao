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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.reelbanao.app.models.FilterEffect
import com.reelbanao.app.repositories.EffectsRepository
import com.reelbanao.app.theme.*

@Composable
fun EffectsScreen(
    effectsRepository: EffectsRepository,
    onBackClick: () -> Unit,
    onEffectApplied: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedEffectId by remember { mutableStateOf("eff_glitch") }
    var effectIntensity by remember { mutableFloatStateOf(0.8f) }

    val effects by effectsRepository.getEffectsByCategory(selectedCategory).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Effects & Filters • इफ़ेक्ट्स",
                onBackClick = onBackClick
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Category Tabs
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(effectsRepository.categories) { cat ->
                    FilterChipPill(
                        label = cat,
                        isSelected = selectedCategory == cat,
                        onClick = { selectedCategory = cat }
                    )
                }
            }

            // Grid of Filter Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(effects) { effect ->
                    val isSelected = selectedEffectId == effect.id
                    EffectGridItem(
                        effect = effect,
                        isSelected = isSelected,
                        onClick = { selectedEffectId = effect.id }
                    )
                }
            }

            // Bottom Adjust Intensity Panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark)
                    .border(1.dp, DividerDark)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Effect Intensity (इफेक्ट की तीव्रता)",
                        fontSize = 12.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${(effectIntensity * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = AccentCyan,
                        fontWeight = FontWeight.Bold
                    )
                }

                Slider(
                    value = effectIntensity,
                    onValueChange = { effectIntensity = it },
                    valueRange = 0.0f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = AccentCyan,
                        activeTrackColor = AccentCyan
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                GradientButton(
                    text = "Apply to Video • इफ़ेक्ट लागू करें",
                    onClick = {
                        val effectName = effects.find { it.id == selectedEffectId }?.name ?: "Cyber Glitch"
                        onEffectApplied(effectName)
                        onBackClick()
                    }
                )
            }
        }
    }
}

@Composable
fun EffectGridItem(
    effect: FilterEffect,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF202330))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) AccentCyan else CardStroke,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(effect.colorHex).copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = TextWhite, modifier = Modifier.size(18.dp))
                } else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = TextWhite, modifier = Modifier.size(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = effect.name,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) AccentCyan else TextWhite,
                maxLines = 1
            )
        }
    }
}

