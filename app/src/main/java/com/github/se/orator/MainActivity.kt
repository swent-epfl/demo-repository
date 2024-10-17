package com.github.se.orator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.github.se.orator.model.speaking.InterviewContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.se.orator.model.profile.UserProfileViewModel
import com.github.se.orator.ui.authentification.SignInScreen
import com.github.se.orator.ui.network.createChatGPTService
import com.github.se.orator.ui.overview.ChatScreen
import com.github.se.orator.ui.overview.FeedbackScreen
import com.github.se.orator.ui.overview.SpeakingSecond
import com.github.se.orator.ui.overview.SpeakingStart
import com.github.se.orator.ui.friends.ViewFriendsScreen
import com.github.se.orator.ui.navigation.NavigationActions
import com.github.se.orator.ui.navigation.Route
import com.github.se.orator.ui.navigation.Screen
import com.github.se.orator.ui.profile.CreateAccountScreen
import com.github.se.orator.ui.profile.EditProfileScreen
import com.github.se.orator.ui.profile.ProfileScreen
import com.github.se.orator.ui.screens.ViewConnectScreen
import com.github.se.orator.ui.screens.ViewFunScreen
import com.github.se.orator.ui.settings.SettingsScreen
import com.github.se.orator.ui.theme.ProjectTheme
import com.github.se.orator.viewModel.ChatViewModel
import com.github.se.orator.viewModel.ChatViewModelFactory
import com.github.se.orator.ui.theme.mainScreen.MainScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
  private lateinit var auth: FirebaseAuth
  private lateinit var chatViewModel: ChatViewModel

  @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Initialize Firebase Auth
    auth = FirebaseAuth.getInstance()
    auth.currentUser?.let {
      // Sign out the user if they are already signed in
      // This is useful for testing purposes
      auth.signOut()
    }



    val apiKey = "sk-1biCkCzmuVEldxLy8fUBG9UtxUCvXM2Sp90lhbaUJzT3BlbkFJg8gOeJefosH5MJrRjMI30LSAvM8x9H6bXGWwjIlBsA"
    val organizationId = "org-btfcw6ORfA1BTBtUq4lHB8cO"

    val chatGPTService = createChatGPTService(apiKey, organizationId)

    val factory = ChatViewModelFactory(chatGPTService)
    chatViewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)

    val interviewContext = InterviewContext(
      interviewType = "job interview",
      role = "Consultant",
      company = "McKinsey",
      focusAreas = listOf("Problem-solving", "Leadership", "Teamwork")
    )

    chatViewModel.initializeConversation(interviewContext)

    enableEdgeToEdge()
    setContent { ProjectTheme { Scaffold(modifier = Modifier.fillMaxSize()) { ChatScreen(viewModel = chatViewModel) } } }
  }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OratorApp() {

  // Main layout using a Scaffold
  Scaffold(modifier = Modifier.fillMaxSize()) {

    // Initialize the navigation controller
    val navController = rememberNavController()
    val navigationActions = NavigationActions(navController)

    // Initialize the view models
    val userProfileViewModel: UserProfileViewModel =
        viewModel(factory = UserProfileViewModel.Factory)

    // Replace the content of the Scaffold with the desired screen
    NavHost(navController = navController, startDestination = Route.AUTH) {
      navigation(
          startDestination = Screen.AUTH,
          route = Route.AUTH,
      ) {

        // Authentication Flow
        composable(Screen.AUTH) { SignInScreen(navigationActions, userProfileViewModel) }

        // Profile Creation Flow
        composable(Screen.CREATE_PROFILE) {
          CreateAccountScreen(navigationActions, userProfileViewModel)
        }
      }

      navigation(
          startDestination = Screen.HOME,
          route = Route.HOME,
      ) {
        composable(Screen.HOME) { MainScreen(navigationActions) }
      }

      navigation(
          startDestination = Screen.FRIENDS,
          route = Route.FRIENDS,
      ) {
        composable(Screen.FRIENDS) { ViewFriendsScreen(navigationActions, userProfileViewModel) }
      }

      //// temporarily adding those empty screens before we implement their functionalities
      composable(Screen.FUN_SCREEN) {
        ViewFunScreen(navigationActions, userProfileViewModel) // Your composable function for Fun Screen
      }
      composable(Screen.CONNECT_SCREEN) {
        ViewConnectScreen(navigationActions, userProfileViewModel) // Your composable function for Connect Screen
      }

      navigation(
          startDestination = Screen.PROFILE,
          route = Route.PROFILE,
      ) {
        composable(Screen.PROFILE) { ProfileScreen(navigationActions, userProfileViewModel) }
        composable(Screen.CREATE_PROFILE) {
          CreateAccountScreen(navigationActions, userProfileViewModel)
        }
        composable(Screen.EDIT_PROFILE) {
          EditProfileScreen(navigationActions, userProfileViewModel)
        }
        composable(Screen.SETTINGS){
          SettingsScreen(navigationActions, userProfileViewModel)
        }
      }
    }
  }
}
