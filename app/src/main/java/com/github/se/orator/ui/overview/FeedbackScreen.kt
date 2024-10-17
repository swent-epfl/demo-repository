package com.github.se.orator.ui.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.se.orator.R
import com.github.se.orator.ui.navigation.NavigationActions
import com.github.se.orator.ui.navigation.Screen

/**
 * The FeedbackScreen composable is a composable screen that displays the feedback screen.
 *
 * @param navigationActions The navigation actions that can be performed.
 */
@Composable
fun FeedbackScreen(navigationActions: NavigationActions) {
  Scaffold(
      modifier = Modifier.fillMaxSize().testTag("feedbackScreen"),
      topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth().statusBarsPadding(),
            backgroundColor = Color.White,
            contentColor = Color.Black,
            elevation = 4.dp,
            title = { Text("Feedback") },
            navigationIcon = {
              IconButton(onClick = { navigationActions.goBack() }) {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp).testTag("back_button"))
              }
            })
      },
      content = { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.Top) {
              // Header Title and Subtitle
              Text(
                  text = "Your Feedback",
                  style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                  modifier = Modifier.padding(bottom = 8.dp).testTag("feedbackTitle"))
              Text(
                  text = "Here's what you did well and where you can improve.",
                  style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                  modifier = Modifier.padding(bottom = 24.dp).testTag("feedbackSubtitle"))

              // Strengths Section
              Text(
                  text = "What You Did Well",
                  style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                  modifier = Modifier.padding(vertical = 8.dp).testTag("strengthsTitle"))
              Text(
                  text = "Strength", // ChatGPT's generated strengths
                  style = TextStyle(fontSize = 16.sp),
                  modifier = Modifier.padding(bottom = 16.dp).testTag("strengthsContent"))

              // Areas for Improvement
              Text(
                  text = "Areas for Improvement",
                  style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                  modifier = Modifier.padding(vertical = 8.dp).testTag("improvementsTitle"))
              Text(
                  text = "Improvements", // ChatGPT's generated improvements
                  style = TextStyle(fontSize = 16.sp),
                  modifier = Modifier.padding(bottom = 24.dp).testTag("improvementsContent"))

              // Tips Section
              Text(
                  text = "Tips to Improve",
                  style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                  modifier = Modifier.padding(vertical = 8.dp).testTag("tipsTitle"))
              Text(
                  text = "Tips", // ChatGPT's actionable tips
                  style = TextStyle(fontSize = 16.sp),
                  modifier = Modifier.padding(bottom = 32.dp).testTag("tipsContent"))

              // Buttons for Retry and Review
              Row(
                  horizontalArrangement = Arrangement.SpaceEvenly,
                  modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                    Button(
                        onClick = { navigationActions.navigateTo(Screen.HOME) },
                        modifier = Modifier.testTag("retryButton")) {
                          Text("Try Again")
                        }
                    Button(
                        onClick = {
                          navigationActions.navigateTo(
                              Screen
                                  .HOME) // Change to save previous state of mode and get back to it
                        },
                        modifier = Modifier.testTag("reviewButton")) {
                          Text("Review Conversation")
                        }
                  }
            }
      })
}
