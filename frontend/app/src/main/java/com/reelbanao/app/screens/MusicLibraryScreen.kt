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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.reelbanao.app.components.AppTopBar
import com.reelbanao.app.components.FilterChipPill
import com.reelbanao.app.models.AudioTrack
import com.reelbanao.app.repositories.AudioRepository
import com.reelbanao.app.theme.*
import com.reelbanao.app.utils.TimeUtils

@Composable
fun MusicLibraryScreen(
    audioRepository: AudioRepository,
    onBackClick: () -> Unit,
    onTrackSelected: (AudioTrack) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var playingTrackId by remember { mutableStateOf<String?>(null) }

    val tracks by audioRepository.searchTracks(searchQuery, selectedCategory).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Music & Sounds • म्यूजिक लाइब्रेरी",
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
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search songs, artists, BGM...", color = TextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPurple,
                    unfocusedBorderColor = CardStroke,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedContainerColor = CardDark,
                    unfocusedContainerColor = CardDark
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Category Tabs
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(audioRepository.categories) { cat ->
                    FilterChipPill(
                        label = cat,
                        isSelected = selectedCategory == cat,
                        onClick = { selectedCategory = cat }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tracks List
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tracks) { track ->
                    val isPlaying = playingTrackId == track.id
                    MusicTrackItem(
                        track = track,
                        isPlaying = isPlaying,
                        onPlayToggle = {
                            playingTrackId = if (isPlaying) null else track.id
                        },
                        onFavoriteToggle = {
                            audioRepository.toggleFavorite(track.id)
                        },
                        onUseTrack = {
                            onTrackSelected(track)
                            onBackClick()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MusicTrackItem(
    track: AudioTrack,
    isPlaying: Boolean,
    onPlayToggle: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onUseTrack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .border(1.dp, if (isPlaying) AccentCyan else CardStroke, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Play Button & Info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(if (isPlaying) AccentCyan else PrimaryPurple)
                        .clickable(onClick = onPlayToggle),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = if (isPlaying) BackgroundDark else TextWhite,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = track.title,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = track.artist, color = TextGray, fontSize = 11.sp)
                        Text(text = " • ", color = TextMuted, fontSize = 11.sp)
                        Text(text = TimeUtils.formatMsToTime(track.durationMs), color = TextMuted, fontSize = 11.sp)
                    }

                    if (track.beatPointsMs.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.GraphicEq, contentDescription = null, tint = AccentPink, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${track.beatPointsMs.size} Beat Points", fontSize = 10.sp, color = AccentPink, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // Favorite & Use Buttons
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (track.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Fav",
                        tint = if (track.isFavorite) AccentPink else TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(PrimaryPurple)
                        .clickable(onClick = onUseTrack)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "Use", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                }
            }
        }
    }
}

