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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelbanao.app.components.GradientButton
import com.reelbanao.app.models.Template
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.repositories.TemplateRepository
import com.reelbanao.app.templates.TemplateEngine
import com.reelbanao.app.theme.*

@Composable
fun TemplatePreviewScreen(
    templateId: String,
    templateRepository: TemplateRepository,
    projectRepository: ProjectRepository,
    onBackClick: () -> Unit,
    onLaunchEditor: (String) -> Unit
) {
    val template = templateRepository.getTemplateById(templateId)
        ?: templateRepository.getAllCategories().let {
            // Fallback to first template
            com.reelbanao.app.data.DummyData.templates.first()
        }

    var isFavorite by remember { mutableStateOf(template.isFavorite) }
    var isPlaying by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = BackgroundDark
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Main Vertical 9:16 Preview Player
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { isPlaying = !isPlaying }
            ) {
                AsyncImage(
                    model = template.coverUrl,
                    contentDescription = template.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark Gradient Overlays (Top & Bottom)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .align(Alignment.TopCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Black.copy(alpha = 0.8f), Color.Transparent)
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.95f))
                            )
                        )
                )

                // Top Bar with Back, Title & Share
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 16.dp)
                        .align(Alignment.TopCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }

                    Row {
                        IconButton(onClick = {
                            isFavorite = !isFavorite
                            templateRepository.toggleFavorite(template.id)
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) AccentPink else TextWhite
                            )
                        }
                    }
                }

                // Center Play Icon if Paused
                if (!isPlaying) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.65f))
                            .align(Alignment.Center),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = TextWhite,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Bottom Content Info Overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    // Category Tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryPurple)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = template.categoryName, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = template.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextWhite,
                            fontSize = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Music info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.GraphicEq, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = template.musicName, color = TextGray, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Slots preview info (Required Clips)
                    Text(
                        text = "Required Clips (${template.slots.size} क्लिप्स चाहिए):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(template.slots) { index, slot ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CardDark)
                                    .border(1.dp, CardStroke, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${slot.label} • ${slot.durationMs / 1000f}s",
                                    fontSize = 10.sp,
                                    color = AccentCyan
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Big "Use Template" CTA Button
                    GradientButton(
                        text = "Use Template • ${template.slots.size} क्लिप्स से बनाएं",
                        onClick = {
                            val newProject = TemplateEngine.instantiateProjectFromTemplate(
                                template = template,
                                userSelectedMediaUris = listOf(
                                    "demo_clip_1",
                                    "demo_clip_2",
                                    "demo_clip_3"
                                )
                            )
                            projectRepository.saveProject(newProject)
                            onLaunchEditor(newProject.id)
                        }
                    )
                }
            }
        }
    }
}

