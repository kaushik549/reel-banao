package com.reelbanao.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.AppBottomBar
import com.reelbanao.app.components.FilterChipPill
import com.reelbanao.app.components.TemplateCard
import com.reelbanao.app.data.DummyData
import com.reelbanao.app.models.Project
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.repositories.TemplateRepository
import com.reelbanao.app.theme.*

@Composable
fun HomeScreen(
    templateRepository: TemplateRepository,
    projectRepository: ProjectRepository,
    onNavigate: (String) -> Unit,
    onOpenTemplate: (String) -> Unit,
    onOpenEditor: (String) -> Unit,
    onOpenAiEdit: () -> Unit
) {
    val templates by templateRepository.templates.collectAsState(initial = emptyList())
    val projects by projectRepository.projects.collectAsState(initial = emptyList())
    val categories = templateRepository.getAllCategories()

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = "home",
                onNavigate = onNavigate,
                onCenterActionClick = {
                    val newProj = projectRepository.createNewProject("New Reel")
                    onOpenEditor(newProj.id)
                }
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Top App Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Reel Banao",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextWhite,
                                fontSize = 22.sp
                            )
                        )
                        Text(
                            text = "नमस्ते Rahul! क्या नया बनाना है?",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextGray,
                                fontSize = 12.sp
                            )
                        )
                    }

                    Row {
                        IconButton(onClick = { onNavigate("profile") }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TextWhite)
                        }
                    }
                }
            }

            // Hero Main Actions: New Project & AI Auto Edit
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // New Project Card
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(115.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(CapCutGradient)
                            .clickable {
                                val newProj = projectRepository.createNewProject("New Reel")
                                onOpenEditor(newProj.id)
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.25f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = TextWhite)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "New Project",
                                fontWeight = FontWeight.Bold,
                                color = TextWhite,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "मैनुअल वीडियो एडिटर",
                                color = TextWhite.copy(alpha = 0.85f),
                                fontSize = 11.sp
                            )
                        }
                    }

                    // AI Auto Edit Card
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(115.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF00C9FF), Color(0xFF92FE9D)))
                            )
                            .clickable(onClick = onOpenAiEdit)
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Black)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "AI Auto Edit",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "स्मार्ट ऑटो वीडियो मेकर",
                                color = Color.Black.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Quick Category Shortcuts Strip
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categories • श्रेणियां",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "See All (${categories.size})",
                        color = AccentCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onNavigate("template_categories") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories.take(8)) { cat ->
                        FilterChipPill(
                            label = "${cat.name} (${cat.templateCount})",
                            isSelected = false,
                            onClick = { onNavigate("templates") }
                        )
                    }
                }
            }

            // Recent Projects Strip
            if (projects.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "My Drafts • रीसेंट प्रोजेक्ट्स",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "View All",
                            color = AccentCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { onNavigate("my_projects") }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(projects) { proj ->
                            ProjectDraftCard(
                                project = proj,
                                onClick = { onOpenEditor(proj.id) }
                            )
                        }
                    }
                }
            }

            // Trending Templates Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Whatshot, contentDescription = null, tint = AccentPink, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Trending Reels Templates",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Text(
                        text = "5,000+ More",
                        color = AccentCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onNavigate("templates") }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Templates Horizontal Row / Cards
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(templates) { template ->
                        TemplateCard(
                            template = template,
                            modifier = Modifier.width(180.dp),
                            onTemplateClick = { onOpenTemplate(template.id) },
                            onFavoriteToggle = { templateRepository.toggleFavorite(template.id) },
                            onUseClick = { onOpenTemplate(template.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectDraftCard(
    project: Project,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .border(1.dp, CardStroke, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PrimaryPurple.copy(alpha = 0.3f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = project.aspectRatio, fontSize = 9.sp, color = TextWhite)
                }
                Text(text = project.resolution, fontSize = 9.sp, color = AccentCyan)
            }

            Column {
                Text(
                    text = project.title,
                    fontWeight = FontWeight.SemiBold,
                    color = TextWhite,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${project.clips.size} clips • ${project.totalDurationFormatted}",
                    fontSize = 10.sp,
                    color = TextGray
                )
            }
        }
    }
}

