package com.droidcon.uganda.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.ui.ConferenceViewModel
import com.droidcon.uganda.utils.TimeZoneUtils
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun FavoritesScreen(viewModel: ConferenceViewModel) {
    val favoriteIds by viewModel.favoriteSessionIds.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    var selectedSession by remember { mutableStateOf<Session?>(null) }

    // Filter favorites by selected day
    val favoriteSessions = remember(favoriteIds, selectedDay, viewModel.sessions) {
        val allFavorites = viewModel.sessions.filter { it.id in favoriteIds }
        if (selectedDay == null) {
            allFavorites
        } else {
            allFavorites.filter { TimeZoneUtils.getDateKey(it.startTime) == selectedDay }
        }
    }

    // Group favorite sessions by date
    val sessionsByDate = remember(favoriteSessions) {
        favoriteSessions
            .sortedBy { it.startTime }
            .groupBy { session ->
                TimeZoneUtils.getDateKey(session.startTime)
            }
            .toList()
            .sortedBy { it.first }
            .toMap()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Day selector (reuse from AgendaScreen)
        if (viewModel.conferenceDays.size > 1) {
            DaySelector(
                days = viewModel.conferenceDays,
                selectedDay = selectedDay,
                onDaySelected = { viewModel.selectDay(it) }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (favoriteSessions.isEmpty()) {
                EmptyFavoritesState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Column {
                            Text(
                                "My Personal Agenda",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "${favoriteSessions.size} session${if (favoriteSessions.size != 1) "s" else ""} saved",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    for ((dateKey, sessionsForDate) in sessionsByDate.entries) {
                        // Only show date header if viewing all days
                        if (selectedDay == null) {
                            item {
                                val firstSession = sessionsForDate.first()
                                val localDate = TimeZoneUtils.toUserLocalTime(firstSession.startTime)

                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        TimeZoneUtils.formatDate(localDate),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        items(
                            sessionsForDate,
                            key = { it.id }
                        ) { session ->
                            SessionCard(
                                session = session,
                                isFavorite = true,
                                onToggleFavorite = { viewModel.toggleFavorite(session.id) },
                                onClick = { selectedSession = session }
                            )
                        }
                    }
                }
            }
        }
    }

    selectedSession?.let { session ->
        SessionDetailDialog(
            session = session,
            isFavorite = session.id in favoriteIds,
            onToggleFavorite = { viewModel.toggleFavorite(session.id) },
            onDismiss = { selectedSession = null }
        )
    }
}

@Composable
fun EmptyFavoritesState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "No Saved Sessions Yet",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Tap the ⭐ icon on sessions in the Agenda to build your personal schedule!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
