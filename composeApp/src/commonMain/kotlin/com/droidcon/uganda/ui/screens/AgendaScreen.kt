package com.droidcon.uganda.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.data.SessionLevel
import com.droidcon.uganda.ui.ConferenceViewModel
import com.droidcon.uganda.ui.UiState
import com.droidcon.uganda.utils.TimeZoneUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(viewModel: ConferenceViewModel) {
    var selectedSession by remember { mutableStateOf<Session?>(null) }
    val favoriteIds by viewModel.favoriteSessionIds.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sessionsState by viewModel.sessionsState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    // Pull-to-refresh state
    val pullToRefreshState = rememberPullToRefreshState()

    // Trigger refresh when pulled
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            viewModel.refreshData()
        }
    }

    // Reset pull-to-refresh state when refresh completes
    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            pullToRefreshState.endRefresh()
        }
    }

    // Track current time for all session status updates (single timer for all cards)
    var currentTime by remember { mutableStateOf(kotlinx.datetime.Clock.System.now()) }

    // Auto-refresh status every minute (runs once at screen level, not per card)
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(60_000) // 60 seconds
            currentTime = kotlinx.datetime.Clock.System.now()
        }
    }

    // Get filtered sessions based on selected day and search query
    val displaySessions = remember(selectedDay, searchQuery, viewModel.sessions) {
        viewModel.getFilteredSessions()
    }

    // Group sessions by date
    val sessionsByDate = remember(displaySessions) {
        displaySessions
            .groupBy { session ->
                TimeZoneUtils.getDateKey(session.startTime)
            }
            .toList()
            .sortedBy { it.first }
            .toMap()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
        // Day selector
        if (viewModel.conferenceDays.size > 1) {
            DaySelector(
                days = viewModel.conferenceDays,
                selectedDay = selectedDay,
                onDaySelected = { viewModel.selectDay(it) }
            )
        }

        // Search bar
        SearchBar(
            query = searchQuery,
            onQueryChange = { viewModel.updateSearchQuery(it) },
            onClear = { viewModel.clearSearch() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Show loading indicator if data is loading
        if (sessionsState is UiState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (displaySessions.isEmpty()) {
            // Empty state when search returns no results
            EmptySearchState(
                hasSearchQuery = searchQuery.isNotEmpty(),
                onClearSearch = { viewModel.clearSearch() }
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Conference Schedule",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        // Show results count when searching
                        if (searchQuery.isNotEmpty()) {
                            Text(
                                "${displaySessions.size} result${if (displaySessions.size != 1) "s" else ""}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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

                    items(sessionsForDate) { session ->
                        SessionCard(
                            session = session,
                            currentTime = currentTime,
                            isFavorite = session.id in favoriteIds,
                            onToggleFavorite = { viewModel.toggleFavorite(session.id) },
                            onClick = { selectedSession = session }
                        )
                    }
                }
            }
        }
        }

        // Pull-to-refresh indicator
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionCard(
    session: Session,
    currentTime: kotlinx.datetime.Instant,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    // Calculate session status based on passed-in time
    val sessionStatus = remember(session, currentTime) {
        TimeZoneUtils.getSessionStatus(session.startTime, session.endTime)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isPressed = true
                onClick()
            }
            // Reduce opacity for ended sessions
            .then(
                if (sessionStatus == com.droidcon.uganda.data.SessionStatus.ENDED) {
                    Modifier.alpha(0.6f)
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Status Badge (prominent position at top)
            StatusBadge(status = sessionStatus)

            Spacer(modifier = Modifier.height(8.dp))

            // Time and Track Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val startTimeLocal = TimeZoneUtils.toUserLocalTime(session.startTime)
                    val endTimeLocal = TimeZoneUtils.toUserLocalTime(session.endTime)
                    Text(
                        "${TimeZoneUtils.formatTime(startTimeLocal)} - ${TimeZoneUtils.formatTime(endTimeLocal)}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Track Badge - Commented out for now
//                    if (session.track.displayName.isNotEmpty()) {
//                        session.track.color?.let { trackColor ->
//                            Surface(
//                                color = Color(trackColor),
//                                shape = RoundedCornerShape(8.dp)
//                            ) {
//                                Text(
//                                    session.track.displayName,
//                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
//                                    style = MaterialTheme.typography.labelSmall,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            }
//                            Spacer(modifier = Modifier.width(8.dp))
//                        }
//                    }

                    // Favorite Button
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            if (isFavorite) Icons.Default.Star else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) Color(0xFFFFD700) else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Session Title
            Text(
                session.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Speaker Info (if available)
            session.speaker?.let { speaker ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar with initials
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = getInitials(speaker.name),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            speaker.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "${speaker.title} @ ${speaker.company}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Room and Level - Commented out for now
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Chip(
//                    icon = Icons.Default.Place,
//                    text = session.room
//                )
//                session.level?.let { level ->
//                    Chip(
//                        icon = Icons.Default.Build,
//                        text = when (level) {
//                            SessionLevel.BEGINNER -> "Beginner"
//                            SessionLevel.INTERMEDIATE -> "Intermediate"
//                            SessionLevel.ADVANCED -> "Advanced"
//                        }
//                    )
//                }
//            }
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}

@Composable
fun Chip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SessionDetailDialog(
    session: Session,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(session.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                val startTimeLocal = TimeZoneUtils.toUserLocalTime(session.startTime)
                val endTimeLocal = TimeZoneUtils.toUserLocalTime(session.endTime)
                Text(
                    "${TimeZoneUtils.formatTime(startTimeLocal)} - ${TimeZoneUtils.formatTime(endTimeLocal)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Track Badge - only show if display name is not empty
                if (session.track.displayName.isNotEmpty()) {
                    item {
                        session.track.color?.let { trackColor ->
                            Surface(
                                color = Color(trackColor),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    session.track.displayName,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        session.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                session.speaker?.let { speaker ->
                    item {
                        HorizontalDivider()
                    }

                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Avatar with initials
                            Surface(
                                modifier = Modifier.size(56.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = getInitials(speaker.name),
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    speaker.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    speaker.title,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    speaker.company,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }

                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Chip(icon = Icons.Default.Place, text = session.room)
                        session.level?.let { level ->
                            Chip(
                                icon = Icons.Default.Build,
                                text = level.name.lowercase().replaceFirstChar { it.uppercase() }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row {
                TextButton(onClick = onToggleFavorite) {
                    Icon(
                        if (isFavorite) Icons.Default.Star else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (isFavorite) Color(0xFFFFD700) else MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isFavorite) "Remove" else "Add to My Agenda")
                }
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    )
}

@Composable
fun DaySelector(
    days: List<String>,
    selectedDay: String?,
    onDaySelected: (String?) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        shadowElevation = 2.dp
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // "All Days" chip
            item {
                DayChip(
                    selected = selectedDay == null,
                    onClick = { onDaySelected(null) },
                    title = "All",
                    subtitle = "Days"
                )
            }

            // Individual day chips
            items(days) { day ->
                val localDate = remember(day) {
                    kotlinx.datetime.LocalDate.parse(day)
                }

                val dayOfWeek = localDate.dayOfWeek.name.substring(0, 3)
                    .lowercase().replaceFirstChar { it.uppercase() }
                val dayNum = localDate.dayOfMonth.toString()

                DayChip(
                    selected = selectedDay == day,
                    onClick = { onDaySelected(day) },
                    title = dayOfWeek,
                    subtitle = dayNum
                )
            }
        }
    }
}

@Composable
fun DayChip(
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    subtitle: String
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(200)
    )

    val contentColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        },
        animationSpec = tween(200)
    )

    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(70.dp)
            .height(70.dp),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        shadowElevation = if (selected) 4.dp else 0.dp,
        border = if (!selected) {
            androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        } else null
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = contentColor
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = {
            Text(
                "Search sessions, speakers, tracks...",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(28.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun EmptySearchState(
    hasSearchQuery: Boolean,
    onClearSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            if (hasSearchQuery) "No Sessions Found" else "No Sessions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            if (hasSearchQuery) {
                "Try adjusting your search terms or filters"
            } else {
                "No sessions available for the selected day"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        if (hasSearchQuery) {
            Spacer(modifier = Modifier.height(16.dp))
            FilledTonalButton(onClick = onClearSearch) {
                Icon(Icons.Default.Clear, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear Search")
            }
        }
    }
}

@Composable
fun StatusBadge(status: com.droidcon.uganda.data.SessionStatus) {
    val (text, backgroundColor, textColor) = when (status) {
        com.droidcon.uganda.data.SessionStatus.LIVE_NOW -> Triple(
            "LIVE NOW",
            Color(0xFFEF5350), // Red
            Color.White
        )
        com.droidcon.uganda.data.SessionStatus.STARTING_SOON -> Triple(
            "STARTING SOON",
            Color(0xFFFF9800), // Amber
            Color.White
        )
        com.droidcon.uganda.data.SessionStatus.UPCOMING -> Triple(
            "UPCOMING",
            Color(0xFF66BB6A), // Green
            Color.White
        )
        com.droidcon.uganda.data.SessionStatus.ENDED -> Triple(
            "ENDED",
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Extracts initials from a full name
 * Example: "John Doe" -> "JD"
 */
private fun getInitials(name: String): String {
    return name
        .trim()
        .split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .map { it.first().uppercase() }
        .joinToString("")
}
