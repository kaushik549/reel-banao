package com.reelbanao.app.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.reelbanao.app.components.TimelineScrubber
import com.reelbanao.app.components.VideoPlayerView
import com.reelbanao.app.editor.EditorTool
import com.reelbanao.app.editor.EditorViewModel
import com.reelbanao.app.models.Project
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.theme.*
import com.reelbanao.app.utils.AspectRatio

@Composable
fun VideoEditorScreen(
    projectId: String,
    projectRepository: ProjectRepository,
    editorViewModel: EditorViewModel,
    onBackClick: () -> Unit,
    onOpenExport: (String) -> Unit,
    onOpenMusicLibrary: () -> Unit,
    onOpenEffects: () -> Unit,
    onOpenTextSubtitle: () -> Unit
) {
    // Load or initialize active project
    LaunchedEffect(projectId) {
        val proj = projectRepository.getProjectById(projectId)
            ?: projectRepository.createNewProject("My New Reel")
        editorViewModel.loadProject(proj)
    }

    var showAspectRatioDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundDark
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Editor Top Bar
            EditorTopBar(
                title = editorViewModel.activeProject?.title ?: "Video Editor",
                currentRatio = editorViewModel.currentAspectRatio.label,
                onBack = onBackClick,
                onRatioClick = {
                    editorViewModel.activeTool = if (editorViewModel.activeTool == EditorTool.RATIO) EditorTool.NONE else EditorTool.RATIO
                },
                onExportClick = {
                    onOpenExport(projectId)
                }
            )

            // Video Preview Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(BackgroundDark)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayerView(
                    activeClip = editorViewModel.currentClip,
                    aspectRatio = editorViewModel.currentAspectRatio,
                    isPlaying = editorViewModel.isPlaying,
                    currentTimeMs = editorViewModel.currentPositionMs,
                    filterName = editorViewModel.selectedFilterName,
                    textLayers = editorViewModel.textLayers,
                    onTogglePlay = { editorViewModel.togglePlayPause() }
                )
            }

            // Interactive Multi-Track Timeline Scrubber
            TimelineScrubber(
                clips = editorViewModel.clips,
                selectedClipIndex = editorViewModel.selectedClipIndex,
                currentPositionMs = editorViewModel.currentPositionMs,
                totalDurationMs = editorViewModel.totalDurationMs,
                audioTracks = editorViewModel.audioTracks,
                textLayers = editorViewModel.textLayers,
                onSelectClip = { index -> editorViewModel.selectedClipIndex = index },
                onSeek = { pos -> editorViewModel.seekTo(pos) },
                onAddClipClick = {
                    editorViewModel.addClip("New Clip ${editorViewModel.clips.size + 1}.mp4", 3500L)
                }
            )

            // Contextual Tool Sub-Drawer (Sliders for Speed, Volume, Aspect Ratio, etc.)
            AnimatedVisibility(
                visible = editorViewModel.activeTool != EditorTool.NONE,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                EditorSubToolDrawer(
                    activeTool = editorViewModel.activeTool,
                    viewModel = editorViewModel,
                    onClose = { editorViewModel.activeTool = EditorTool.NONE }
                )
            }

            // Bottom Horizontal Tool Dock (VN & CapCut style)
            EditorBottomToolDock(
                activeTool = editorViewModel.activeTool,
                onToolSelect = { tool ->
                    when (tool) {
                        EditorTool.AUDIO -> onOpenMusicLibrary()
                        EditorTool.FILTER -> onOpenEffects()
                        EditorTool.TEXT, EditorTool.SUBTITLE -> onOpenTextSubtitle()
                        else -> {
                            editorViewModel.activeTool = if (editorViewModel.activeTool == tool) EditorTool.NONE else tool
                        }
                    }
                },
                onSplit = { editorViewModel.splitCurrentClip() },
                onDuplicate = { editorViewModel.duplicateCurrentClip() },
                onDelete = { editorViewModel.deleteCurrentClip() }
            )
        }
    }
}

@Composable
fun EditorTopBar(
    title: String,
    currentRatio: String,
    onBack: () -> Unit,
    onRatioClick: () -> Unit,
    onExportClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(SurfaceDark)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = TextWhite,
                maxLines = 1,
                modifier = Modifier.width(110.dp)
            )
        }

        // Center Aspect Ratio Pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(CardDark)
                .border(1.dp, CardStroke, RoundedCornerShape(12.dp))
                .clickable(onClick = onRatioClick)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AspectRatio, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = currentRatio, fontSize = 11.sp, color = TextWhite, fontWeight = FontWeight.Bold)
            }
        }

        // Export Button
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(CapCutGradient)
                .clickable(onClick = onExportClick)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.IosShare, contentDescription = "Export", tint = TextWhite, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Export", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }
    }
}

@Composable
fun EditorSubToolDrawer(
    activeTool: EditorTool,
    viewModel: EditorViewModel,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark)
            .border(1.dp, DividerDark)
            .padding(14.dp)
    ) {
        when (activeTool) {
            EditorTool.SPEED -> {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Speed Control (गति)", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 13.sp)
                        Text(text = "${String.format("%.1f", viewModel.currentSpeed)}x", color = AccentCyan, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = viewModel.currentSpeed,
                        onValueChange = { viewModel.setSpeed(it) },
                        valueRange = 0.2f..5.0f,
                        colors = SliderDefaults.colors(thumbColor = AccentCyan, activeTrackColor = AccentCyan)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(0.5f, 1.0f, 2.0f, 3.0f).forEach { speed ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (viewModel.currentSpeed == speed) PrimaryPurple else CardDark)
                                    .clickable { viewModel.setSpeed(speed) }
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(text = "${speed}x", fontSize = 11.sp, color = TextWhite)
                            }
                        }
                    }
                }
            }
            EditorTool.RATIO -> {
                Column {
                    Text(text = "Aspect Ratio (कैनवास का साइज़)", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AspectRatio.entries.forEach { ratio ->
                            val isSelected = viewModel.currentAspectRatio == ratio
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PrimaryPurple else CardDark)
                                    .border(1.dp, if (isSelected) AccentCyan else CardStroke, RoundedCornerShape(8.dp))
                                    .clickable { viewModel.currentAspectRatio = ratio }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(text = ratio.label, fontSize = 12.sp, color = TextWhite, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            EditorTool.TRIM -> {
                Column {
                    Text(text = "Trim & Cut (शुरुआत और अंत काटें)", fontWeight = FontWeight.Bold, color = TextWhite, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "क्लिप के किनारों को ड्रैग करके ट्रिम करें या Split बटन दबाएं।", color = TextGray, fontSize = 11.sp)
                }
            }
            else -> {}
        }
    }
}

@Composable
fun EditorBottomToolDock(
    activeTool: EditorTool,
    onToolSelect: (EditorTool) -> Unit,
    onSplit: () -> Unit,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(BackgroundDark)
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ToolDockItem(
            icon = Icons.Default.ContentCut,
            label = "Split (काटें)",
            isSelected = false,
            onClick = onSplit
        )
        ToolDockItem(
            icon = Icons.Default.Speed,
            label = "Speed",
            isSelected = activeTool == EditorTool.SPEED,
            onClick = { onToolSelect(EditorTool.SPEED) }
        )
        ToolDockItem(
            icon = Icons.Default.MusicNote,
            label = "Audio (म्यूजिक)",
            isSelected = false,
            onClick = { onToolSelect(EditorTool.AUDIO) }
        )
        ToolDockItem(
            icon = Icons.Default.TextFields,
            label = "Text",
            isSelected = false,
            onClick = { onToolSelect(EditorTool.TEXT) }
        )
        ToolDockItem(
            icon = Icons.Default.Tune,
            label = "Filters & FX",
            isSelected = false,
            onClick = { onToolSelect(EditorTool.FILTER) }
        )
        ToolDockItem(
            icon = Icons.Default.Subtitles,
            label = "Subtitles",
            isSelected = false,
            onClick = { onToolSelect(EditorTool.SUBTITLE) }
        )
        ToolDockItem(
            icon = Icons.Default.ContentCopy,
            label = "Duplicate",
            isSelected = false,
            onClick = onDuplicate
        )
        ToolDockItem(
            icon = Icons.Default.Delete,
            label = "Delete",
            isSelected = false,
            tint = AccentPink,
            onClick = onDelete
        )
    }
}

@Composable
private fun ToolDockItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    tint: Color = TextWhite,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) AccentCyan else tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) AccentCyan else TextGray
        )
    }
}

