package com.reelbanao.app.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reelbanao.app.components.GradientButton
import com.reelbanao.app.theme.*

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Title
        Text(
            text = "Reel Banao",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextWhite
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isSignUp) "नया अकाउंट बनाएं • Create Account" else "लॉगिन करें • Welcome Back",
            color = TextGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tabs: Login / Sign Up
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(CardDark)
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (!isSignUp) PrimaryPurple else Color.Transparent)
                    .clickable { isSignUp = false }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Login",
                    color = if (!isSignUp) TextWhite else TextGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSignUp) PrimaryPurple else Color.Transparent)
                    .clickable { isSignUp = true }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sign Up",
                    color = if (isSignUp) TextWhite else TextGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Name field for signup
        AnimatedVisibility(visible = isSignUp) {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name (नाम)") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = AccentCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = CardStroke,
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedLabelColor = AccentCyan,
                        unfocusedLabelColor = TextGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email / Mobile (ईमेल या नंबर)") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = AccentCyan) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentCyan,
                unfocusedBorderColor = CardStroke,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedLabelColor = AccentCyan,
                unfocusedLabelColor = TextGray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password (पासवर्ड)") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = AccentCyan) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password",
                        tint = TextGray
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentCyan,
                unfocusedBorderColor = CardStroke,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedLabelColor = AccentCyan,
                unfocusedLabelColor = TextGray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        GradientButton(
            text = if (isSignUp) "Create Account • अकाउंट बनाएं" else "Login • अंदर जाएं",
            onClick = onAuthSuccess
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Guest Mode Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, CardStroke, RoundedCornerShape(24.dp))
                .clickable(onClick = onAuthSuccess),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Continue as Guest (बिना लॉगिन सीधे चलाएं)",
                color = AccentCyan,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Info Badge
        Text(
            text = "Phase 1: Local UI Mode (Backend API will connect in Phase 2)",
            color = TextMuted,
            fontSize = 11.sp
        )
    }
}

