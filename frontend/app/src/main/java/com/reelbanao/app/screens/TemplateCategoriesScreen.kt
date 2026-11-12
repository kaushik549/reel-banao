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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.AppTopBar
import com.reelbanao.app.models.Category
import com.reelbanao.app.repositories.TemplateRepository
import com.reelbanao.app.theme.*

@Composable
fun TemplateCategoriesScreen(
    templateRepository: TemplateRepository,
    onBackClick: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    val categories = templateRepository.getAllCategories()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "20 Categories • श्रेणियां",
                onBackClick = onBackClick
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(categories) { category ->
                CategoryGridCard(
                    category = category,
                    onClick = { onCategoryClick(category.id) }
                )
            }
        }
    }
}

@Composable
fun CategoryGridCard(
    category: Category,
    onClick: () -> Unit
) {
    val icon = getCategoryIcon(category.iconName)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(1.dp, if (category.isFeatured) PrimaryPurpleLight else CardStroke, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(if (category.isFeatured) PrimaryPurple.copy(alpha = 0.35f) else DividerDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = category.name,
                        tint = if (category.isFeatured) AccentCyan else TextWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (category.isFeatured) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(AccentPink.copy(alpha = 0.25f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "HOT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = AccentPink)
                    }
                }
            }

            Column {
                Text(
                    text = category.name,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontSize = 14.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.hindiName,
                        fontSize = 11.sp,
                        color = TextGray
                    )
                    Text(
                        text = "${category.templateCount}+",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AccentCyan
                    )
                }
            }
        }
    }
}

private fun getCategoryIcon(name: String): ImageVector {
    return when (name) {
        "Whatshot" -> Icons.Default.Whatshot
        "CameraAlt" -> Icons.Default.CameraAlt
        "Cake" -> Icons.Default.Cake
        "Favorite" -> Icons.Default.Favorite
        "SentimentDissatisfied" -> Icons.Default.SentimentDissatisfied
        "FlashOn" -> Icons.Default.FlashOn
        "Movie" -> Icons.Default.Movie
        "FlightTakeoff" -> Icons.Default.FlightTakeoff
        "Celebration" -> Icons.Default.Celebration
        "People" -> Icons.Default.People
        "Diversity3" -> Icons.Default.Diversity3
        "Collections" -> Icons.Default.Collections
        "SlowMotionVideo" -> Icons.Default.SlowMotionVideo
        "GraphicEq" -> Icons.Default.GraphicEq
        "FitnessCenter" -> Icons.Default.FitnessCenter
        "Forest" -> Icons.Default.Forest
        "Storefront" -> Icons.Default.Storefront
        "SmartDisplay" -> Icons.Default.SmartDisplay
        else -> Icons.Default.AutoAwesome
    }
}

