package com.droidcon.uganda

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.droidcon.uganda.ui.ConferenceViewModel
import com.droidcon.uganda.ui.screens.AgendaScreen
import com.droidcon.uganda.ui.screens.FavoritesScreen
import com.droidcon.uganda.ui.screens.SpeakersScreen
import com.droidcon.uganda.ui.theme.DroidConTheme

enum class AppTab(
    val title: String,
    val icon: ImageVector
) {
    AGENDA("Agenda", Icons.Default.DateRange),
    SPEAKERS("Speakers", Icons.Default.Person),
    FAVORITES("My Agenda", Icons.Default.Star)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    DroidConTheme {
        val viewModel = remember { ConferenceViewModel() }
        var selectedTab by remember { mutableStateOf(AppTab.AGENDA) }
        var showAboutDialog by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "DroidCon Uganda 2025",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    actions = {
                        IconButton(onClick = { showAboutDialog = true }) {
                            Icon(Icons.Default.Info, contentDescription = "About")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = NavigationBarDefaults.Elevation
                ) {
                    AppTab.entries.forEach { tab ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    tab.icon,
                                    contentDescription = tab.title
                                )
                            },
                            label = { Text(tab.title) },
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(300)) togetherWith
                                fadeOut(animationSpec = tween(300))
                    }
                ) { tab ->
                    when (tab) {
                        AppTab.AGENDA -> AgendaScreen(viewModel)
                        AppTab.SPEAKERS -> SpeakersScreen(viewModel)
                        AppTab.FAVORITES -> FavoritesScreen(viewModel)
                    }
                }
            }
        }

        if (showAboutDialog) {
            AboutDialog(onDismiss = { showAboutDialog = false })
        }
    }
}

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text("About DroidCon Uganda")
        },
        text = {
            Column {
                Text(
                    "🇺🇬 DroidCon Uganda 2025\n\n" +
                    "Join Uganda's premier Android developer conference! " +
                    "Connect with fellow developers, learn from industry experts, " +
                    "and discover the latest in Android development.\n\n" +
                    "📅 Date: Coming Soon\n" +
                    "📍 Location: Kampala, Uganda\n\n" +
                    "Follow us on Twitter: @DroidConUganda\n\n" +
                    "Built with ❤️ using Compose Multiplatform",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
