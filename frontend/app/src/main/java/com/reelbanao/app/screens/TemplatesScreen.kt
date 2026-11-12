package com.reelbanao.app.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.AppBottomBar
import com.reelbanao.app.components.FilterChipPill
import com.reelbanao.app.components.TemplateCard
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.repositories.TemplateRepository
import com.reelbanao.app.theme.*

@Composable
fun TemplatesScreen(
    templateRepository: TemplateRepository,
    projectRepository: ProjectRepository,
    onNavigate: (String) -> Unit,
    onOpenTemplate: (String) -> Unit,
    onOpenEditor: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf("All") } // "All", "Trending", "Latest", "Popular", "Favorites"
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }

    val categories = templateRepository.getAllCategories()
    val filteredTemplates by templateRepository.searchAndFilter(
        query = searchQuery,
        categoryId = selectedCategoryId,
        filterTab = selectedTab
    ).collectAsState(initial = emptyList())

    val tabs = listOf("All", "Trending", "Latest", "Popular", "Favorites")

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = "templates",
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
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "5,000+ Templates",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextWhite,
                        fontSize = 22.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardDark)
                        .clickable { onNavigate("template_categories") }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FilterList, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Categories", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search templates, music, tags...", color = TextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextGray)
                        }
                    }
                },
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
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Filter Tabs (Trending, Latest, Popular, Favorites)
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tabs) { tab ->
                    FilterChipPill(
                        label = tab,
                        isSelected = selectedTab == tab,
                        onClick = { selectedTab = tab }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Categories Strip
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChipPill(
                        label = "All Categories",
                        isSelected = selectedCategoryId == null,
                        onClick = { selectedCategoryId = null }
                    )
                }
                items(categories) { cat ->
                    FilterChipPill(
                        label = cat.name,
                        isSelected = selectedCategoryId == cat.id,
                        onClick = { selectedCategoryId = cat.id }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Templates 2-Column Grid
            if (filteredTemplates.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "कोई टेम्प्लेट नहीं मिला",
                            style = MaterialTheme.typography.titleMedium.copy(color = TextWhite)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try searching something else or reset filters",
                            color = TextGray,
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredTemplates) { template ->
                        TemplateCard(
                            template = template,
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

