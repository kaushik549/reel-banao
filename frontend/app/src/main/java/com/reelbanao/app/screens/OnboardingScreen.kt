package com.reelbanao.app.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.GradientButton
import com.reelbanao.app.theme.*
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val hindiSubtitle: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val pages = listOf(
        OnboardingPageData(
            title = "5,000+ Trending Templates",
            hindiSubtitle = "एक क्लिक में वायरल रील्स बनाएं",
            description = "Instagram Reels, Beat Sync, Attitude, Romantic और 20+ कैटेगरी के रेडीमेड टेम्प्लेट्स बिल्कुल फ्री में इस्तेमाल करें।",
            icon = Icons.Default.MovieFilter
        ),
        OnboardingPageData(
            title = "Pro Multi-Track Editor",
            hindiSubtitle = "VN व CapCut जैसा प्रोफेशनल अनुभव",
            description = "फ़ोटो-वीडियो ट्रिम करें, कट्स लगाएं, बैकग्राउंड म्यूजिक, 4K एक्सपोर्ट, और अल्ट्रा-स्मूथ स्लो मोशन जोड़ें।",
            icon = Icons.Default.Speed
        ),
        OnboardingPageData(
            title = "AI Auto Video Creator",
            hindiSubtitle = "आर्टिफिशियल इंटेलिजेंस से ऑटो एडिट",
            description = "अपनी फ़ोटो और वीडियो चुनें, म्यूजिक का मूड सेलेक्ट करें और AI खुद बीट मैच करके सिनेमाई वीडियो बना देगा।",
            icon = Icons.Default.AutoAwesome
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Skip Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onFinishOnboarding) {
                Text(text = "Skip", color = TextGray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Slide Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val item = pages[page]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(CardDark),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = AccentCyan,
                        modifier = Modifier.size(68.dp)
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.hindiSubtitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentPink,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // Page Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            repeat(pages.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 24.dp else 8.dp, 8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) AccentCyan else CardStroke)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Next / Get Started Button
        val isLastPage = pagerState.currentPage == pages.lastIndex
        GradientButton(
            text = if (isLastPage) "Get Started • शुरू करें" else "Next • आगे बढ़ें",
            onClick = {
                if (isLastPage) {
                    onFinishOnboarding()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

