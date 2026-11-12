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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.reelbanao.app.components.AppBottomBar
import com.reelbanao.app.models.Project
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.theme.*

@Composable
fun MyProjectsScreen(
    projectRepository: ProjectRepository,
    onNavigate: (String) -> Unit,
    onOpenEditor: (String) -> Unit,
    onOpenExport: (String) -> Unit
) {
    val projects by projectRepository.projects.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = "my_projects",
                onNavigate = onNavigate,
                onCenterActionClick = {
                    val newProj = projectRepository.createNewProject("New Reel")
                    onOpenEditor(newProj.id)
                }
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "My Projects",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextWhite,
                            fontSize = 22.sp
                        )
                    )
                    Text(
                        text = "${projects.size} Drafts • प्रोजेक्ट ड्राफ्ट्स",
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }

                // New Project Small Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CapCutGradient)
                        .clickable {
                            val newProj = projectRepository.createNewProject("New Reel")
                            onOpenEditor(newProj.id)
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = TextWhite, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "New", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Projects List
            if (projects.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "अभी कोई प्रोजेक्ट नहीं है", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "नया प्रोजेक्ट शुरू करने के लिए + बटन दबाएं", color = TextGray, fontSize = 12.sp)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(projects) { project ->
                        ProjectListItem(
                            project = project,
                            onOpen = { onOpenEditor(project.id) },
                            onExport = { onOpenExport(project.id) },
                            onDuplicate = { projectRepository.duplicateProject(project.id) },
                            onDelete = { projectRepository.deleteProject(project.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectListItem(
    project: Project,
    onOpen: () -> Unit,
    onExport: () -> Unit,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .border(1.dp, CardStroke, RoundedCornerShape(14.dp))
            .clickable(onClick = onOpen)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Thumbnail
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF282B3A)),
                contentAlignment = Alignment.Center
            ) {
                if (project.coverUri != null) {
                    AsyncImage(
                        model = project.coverUri,
                        contentDescription = project.title,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(text = project.aspectRatio, fontSize = 12.sp, color = AccentCyan, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Center Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.title,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = project.totalDurationFormatted, color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = " • ", color = TextMuted, fontSize = 11.sp)
                    Text(text = "${project.clips.size} clips", color = TextGray, fontSize = 11.sp)
                    Text(text = " • ", color = TextMuted, fontSize = 11.sp)
                    Text(text = project.resolution, color = TextGray, fontSize = 11.sp)
                }
            }

            // Right Actions Menu
            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = TextGray)
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier.background(SurfaceDark)
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit (एडिट करें)", color = TextWhite) },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = AccentCyan) },
                        onClick = {
                            menuExpanded = false
                            onOpen()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Export (एक्सपोर्ट)", color = TextWhite) },
                        leadingIcon = { Icon(Icons.Default.IosShare, contentDescription = null, tint = AccentGreen) },
                        onClick = {
                            menuExpanded = false
                            onExport()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Duplicate (कॉपी बनाएं)", color = TextWhite) },
                        leadingIcon = { Icon(Icons.Default.ContentCopy, contentDescription = null, tint = TextGray) },
                        onClick = {
                            menuExpanded = false
                            onDuplicate()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete (डिलीट)", color = AccentPink) },
                        leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = AccentPink) },
                        onClick = {
                            menuExpanded = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}

