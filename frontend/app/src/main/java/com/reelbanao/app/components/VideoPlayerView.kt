package com.reelbanao.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelbanao.app.models.TextLayer
import com.reelbanao.app.models.VideoClip
import com.reelbanao.app.theme.*
import com.reelbanao.app.utils.AspectRatio

@Composable
fun VideoPlayerView(
    activeClip: VideoClip?,
    aspectRatio: AspectRatio,
    isPlaying: Boolean,
    currentTimeMs: Long,
    filterName: String,
    textLayers: List<TextLayer>,
    modifier: Modifier = Modifier,
    onTogglePlay: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        // Aspect ratio container (framed like CapCut / VN player)
        Box(
            modifier = Modifier
                .aspectRatio(aspectRatio.floatRatio)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1E212D))
                .border(1.dp, CardStroke, RoundedCornerShape(12.dp))
                .clickable(onClick = onTogglePlay),
            contentAlignment = Alignment.Center
        ) {
            // Simulated video content
            if (activeClip != null) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800",
                    contentDescription = activeClip.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Filter overlay simulation
            if (filterName != "Normal") {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            when (filterName) {
                                "Cyber Glitch" -> AccentPink.copy(alpha = 0.25f)
                                "VHS Retro 90s" -> AccentCyan.copy(alpha = 0.2f)
                                "Cinematic 35mm" -> AccentAmber.copy(alpha = 0.2f)
                                else -> PrimaryPurple.copy(alpha = 0.15f)
                            }
                        )
                )
            }

            // Visible Text Layers at current playback time
            textLayers.forEach { layer ->
                if (currentTimeMs in layer.startMs..layer.endMs) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = layer.text,
                            color = Color(layer.colorHex),
                            fontSize = layer.fontSizeSp.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Big Center Play / Pause Indicator on Tap
            AnimatedVisibility(
                visible = !isPlaying,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.65f))
                        .border(1.dp, TextWhite.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = TextWhite,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

