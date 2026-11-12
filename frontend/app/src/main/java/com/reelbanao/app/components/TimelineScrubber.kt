package com.reelbanao.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.models.AudioTrack
import com.reelbanao.app.models.TextLayer
import com.reelbanao.app.models.VideoClip
import com.reelbanao.app.theme.*
import com.reelbanao.app.utils.TimeUtils

@Composable
fun TimelineScrubber(
    clips: List<VideoClip>,
    selectedClipIndex: Int,
    currentPositionMs: Long,
    totalDurationMs: Long,
    audioTracks: List<AudioTrack>,
    textLayers: List<TextLayer>,
    onSelectClip: (Int) -> Unit,
    onSeek: (Long) -> Unit,
    onAddClipClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark)
            .border(1.dp, DividerDark)
            .padding(vertical = 8.dp)
    ) {
        // Timecode and Scrubber Slider
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = TimeUtils.formatMsToDetailedTime(currentPositionMs),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AccentCyan
            )

            Text(
                text = TimeUtils.formatMsToDetailedTime(totalDurationMs),
                fontSize = 12.sp,
                color = TextGray
            )
        }

        // Scrub Slider
        Slider(
            value = if (totalDurationMs > 0) currentPositionMs.toFloat() / totalDurationMs else 0f,
            onValueChange = { fraction ->
                onSeek((fraction * totalDurationMs).toLong())
            },
            colors = SliderDefaults.colors(
                thumbColor = AccentCyan,
                activeTrackColor = AccentCyan,
                inactiveTrackColor = CardDark
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(28.dp)
        )

        // Tracks Area (Scrollable Horizontal Timeline)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Video Track
                clips.forEachIndexed { index, clip ->
                    val isSelected = index == selectedClipIndex
                    val clipWidthDp = ((clip.durationMs / 1000f) * 28f).coerceIn(70f, 220f).dp

                    Box(
                        modifier = Modifier
                            .height(56.dp)
                            .width(clipWidthDp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) CardDark else Color(0xFF1E202B))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) AccentCyan else CardStroke,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectClip(index) }
                            .padding(6.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.SpaceBetween) {
                            Text(
                                text = clip.name,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = TextWhite,
                                maxLines = 1
                            )
                            Text(
                                text = "${clip.durationMs / 1000f}s",
                                fontSize = 10.sp,
                                color = AccentCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                }

                // Add Clip Button (+)
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CardDark)
                        .border(1.dp, CardStroke, RoundedCornerShape(8.dp))
                        .clickable(onClick = onAddClipClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Clip",
                        tint = TextWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(40.dp))
            }
        }

        // Secondary Track Summary (Audio & Text indicators)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (audioTracks.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(AccentGreen.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.GraphicEq, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${audioTracks.size} Audio Track", fontSize = 10.sp, color = AccentGreen)
                    }
                }
            }

            if (textLayers.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(PrimaryPurple.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.TextFields, contentDescription = null, tint = PrimaryPurpleLight, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${textLayers.size} Text Subtitle", fontSize = 10.sp, color = PrimaryPurpleLight)
                    }
                }
            }
        }
    }
}

