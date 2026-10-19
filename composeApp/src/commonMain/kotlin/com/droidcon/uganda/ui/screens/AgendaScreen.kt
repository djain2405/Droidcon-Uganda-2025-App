package com.droidcon.uganda.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.data.SessionLevel
import com.droidcon.uganda.data.Track
import com.droidcon.uganda.ui.ConferenceViewModel
import com.droidcon.uganda.ui.UiState
import com.droidcon.uganda.ui.theme.DroidConBlue
import com.droidcon.uganda.ui.theme.DroidConNavy
import com.droidcon.uganda.utils.TimeZoneUtils
import org.jetbrains.compose.resources.painterResource
import droidconuganda.composeapp.generated.resources.*
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(viewModel: ConferenceViewModel) {
    var selectedSession by remember { mutableStateOf<Session?>(null) }
    val favoriteIds by viewModel.favoriteSessionIds.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedTrack by viewModel.selectedTrack.collectAsState()
    val typesenseMatchIds by viewModel.typesenseMatchIds.collectAsState()
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

    // Get filtered sessions based on selected day, track, and search query
    val displaySessions = remember(selectedDay, searchQuery, selectedTrack, typesenseMatchIds, viewModel.sessions) {
        viewModel.getFilteredSessions()
    }
    val availableTracks = remember(selectedDay, viewModel.sessions) {
        val sessionsOnDay = if (selectedDay == null) {
            viewModel.sessions
        } else {
            viewModel.sessions.filter { session ->
                session.startTime?.let { TimeZoneUtils.getDateKey(it) } == selectedDay
            }
        }
        Track.entries.filter { track ->
            track != Track.NONE && sessionsOnDay.any { it.track == track }
        }
    }
    LaunchedEffect(availableTracks, selectedTrack) {
        if (selectedTrack != null && selectedTrack !in availableTracks) {
            viewModel.selectTrack(null)
        }
    }

    val sessionsByDate = remember(displaySessions) {
        displaySessions
            .filter { it.startTime != null }
            .groupBy { session ->
                TimeZoneUtils.getDateKey(session.startTime!!)
            }
            .toList()
            .sortedBy { it.first }
            .toMap()
    }
    val unscheduledSessions = remember(displaySessions) {
        displaySessions
            .filter { it.startTime == null }
            .sortedBy { it.title }
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SearchPoweredByTypesense()

        TrackFilter(
            tracks = availableTracks,
            selectedTrack = selectedTrack,
            onTrackSelected = { viewModel.selectTrack(it) }
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
                hasTrackFilter = selectedTrack != null,
                onClearSearch = { viewModel.clearSearch() }
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Conference Schedule",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            if (searchQuery.isNotEmpty()) {
                                Text(
                                    "${displaySessions.size} result${if (displaySessions.size != 1) "s" else ""}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        }
                    }
                }

                for ((dateKey, sessionsForDate) in sessionsByDate.entries) {
                    // Only show date header if viewing all days
                    if (selectedDay == null) {
                        item {
                            val firstSession = sessionsForDate.first()
                            val localDate = TimeZoneUtils.toUserLocalTime(firstSession.startTime!!)

                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    TimeZoneUtils.formatDate(localDate),
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
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

                if (unscheduledSessions.isNotEmpty()) {
                    item {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Schedule coming soon",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                    items(unscheduledSessions) { session ->
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

    val sessionStatus = remember(session, currentTime) {
        val start = session.startTime
        val end = session.endTime
        if (start != null && end != null) {
            TimeZoneUtils.getSessionStatus(start, end)
        } else {
            null
        }
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
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (sessionStatus != null) {
                StatusBadge(status = sessionStatus)
                Spacer(modifier = Modifier.height(8.dp))
            }

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
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    val start = session.startTime
                    val end = session.endTime
                    val timeLabel = if (start != null && end != null) {
                        val startTimeLocal = TimeZoneUtils.toUserLocalTime(start)
                        val endTimeLocal = TimeZoneUtils.toUserLocalTime(end)
                        "${TimeZoneUtils.formatTime(startTimeLocal)} - ${TimeZoneUtils.formatTime(endTimeLocal)}"
                    } else {
                        "Time TBA"
                    }
                    Text(
                        timeLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
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
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Speaker Info (if available)
            session.speaker?.let { speaker ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Speaker photo
                    SpeakerImage(
                        imageUrl = speaker.imageUrl,
                        contentDescription = speaker.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            speaker.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
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
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.tertiary,
        textContentColor = MaterialTheme.colorScheme.onPrimary,
        title = {
            Column {
                Text(
                    session.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(4.dp))
                val start = session.startTime
                val end = session.endTime
                val timeLabel = if (start != null && end != null) {
                    val startTimeLocal = TimeZoneUtils.toUserLocalTime(start)
                    val endTimeLocal = TimeZoneUtils.toUserLocalTime(end)
                    "${TimeZoneUtils.formatTime(startTimeLocal)} - ${TimeZoneUtils.formatTime(endTimeLocal)}"
                } else {
                    "Time TBA"
                }
                Text(
                    timeLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
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
                        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f))
                    }

                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Speaker photo
                            SpeakerImage(
                                imageUrl = speaker.imageUrl,
                                contentDescription = speaker.name,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    speaker.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    speaker.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    speaker.company,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimary
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = onToggleFavorite,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Icon(
                        if (isFavorite) Icons.Default.Star else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isFavorite) "Remove" else "Add to My Agenda")
                }
                TextButton(onClick = onDismiss) {
                    Text("Close", color = MaterialTheme.colorScheme.onPrimary)
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
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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
            .width(56.dp)
            .height(56.dp),
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
            Text(
                subtitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
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
    val textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = Color.Black,
        fontWeight = FontWeight.Medium
    )
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .height(48.dp)
            .border(2.dp, DroidConNavy, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp),
        textStyle = textStyle,
        singleLine = true,
        cursorBrush = SolidColor(DroidConNavy),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(22.dp),
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty()) {
                        Text(
                            "Search sessions",
                            style = textStyle.copy(fontWeight = FontWeight.Medium),
                            color = Color(0xFF111111)
                        )
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear search",
                            modifier = Modifier.size(22.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun SearchPoweredByTypesense(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                role = Role.Button,
                onClick = { uriHandler.openUri("https://typesense.org") }
            ),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Search powered by Typesense",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(Res.drawable.typesense_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(16.dp)
                    .widthIn(max = 96.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun TrackFilter(
    tracks: List<Track>,
    selectedTrack: Track?,
    onTrackSelected: (Track?) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)) {
        Text(
            "Track",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TrackChip(
                    label = "All",
                    selected = selectedTrack == null,
                    onClick = { onTrackSelected(null) }
                )
            }
            items(tracks) { track ->
                TrackChip(
                    label = track.displayName,
                    selected = selectedTrack == track,
                    onClick = { onTrackSelected(track) }
                )
            }
        }
    }
}

@Composable
fun TrackChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .heightIn(min = 40.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        color = if (selected) DroidConBlue else Color.White,
        border = if (selected) null else BorderStroke(2.dp, DroidConNavy)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else DroidConNavy
            )
        }
    }
}

@Composable
fun EmptySearchState(
    hasSearchQuery: Boolean,
    hasTrackFilter: Boolean,
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
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            when {
                hasSearchQuery -> "Try adjusting your search terms or filters"
                hasTrackFilter -> "No sessions on this track"
                else -> "No sessions available for the selected day"
            },
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
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

/**
 * Displays speaker image from URL (Sessionize) or local drawable resource
 */
@Composable
private fun SpeakerImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    // Check if imageUrl is a URL (from Sessionize) or a local resource name
    val isUrl = imageUrl.isNotBlank() && (
        imageUrl.startsWith("http://", ignoreCase = true) ||
        imageUrl.startsWith("https://", ignoreCase = true)
    )

    if (isUrl) {
        // Load from network using Coil with error handling
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            error = painterResource(Res.drawable.speaker_ahmed_nabil),
            placeholder = painterResource(Res.drawable.speaker_ahmed_nabil),
            onError = { error ->
                println("❌ Error loading speaker image: ${error.result.throwable.message}")
            },
            onSuccess = {
                println("✅ Successfully loaded speaker image")
            }
        )
    } else {
        // Load from local drawable resources
        Image(
            painter = painterResource(getSpeakerImageResource(imageUrl)),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}

/**
 * Maps speaker imageUrl string to the actual drawable resource
 */
@Composable
private fun getSpeakerImageResource(imageUrl: String): org.jetbrains.compose.resources.DrawableResource {
    return when (imageUrl) {
        "speaker_ahmed_nabil" -> Res.drawable.speaker_ahmed_nabil
        "speaker_akshay_chordiya" -> Res.drawable.speaker_akshay_chordiya
        "speaker_anjan_kumar_kaleru" -> Res.drawable.speaker_anjan_kumar_kaleru
        "speaker_anselmo_alexandre" -> Res.drawable.speaker_anselmo_alexandre
        "speaker_arun_kambhammettu" -> Res.drawable.speaker_arun_kambhammettu
        "speaker_caroline_nicole_letaru" -> Res.drawable.speaker_caroline_nicole_letaru
        "speaker_dinoy_raj" -> Res.drawable.speaker_dinoy_raj
        "speaker_georges_byona" -> Res.drawable.speaker_georges_byona
        "speaker_indu_priya_uppala" -> Res.drawable.speaker_indu_priya_uppala
        "speaker_jamiu_okanlawon" -> Res.drawable.speaker_jamiu_okanlawon
        "speaker_jayesh_kumar_pandey" -> Res.drawable.speaker_jayesh_kumar_pandey
        "speaker_joshua_musyoki" -> Res.drawable.speaker_joshua_musyoki
        "speaker_joydip_basu" -> Res.drawable.speaker_joydip_basu
        "speaker_kenneth_mathari" -> Res.drawable.speaker_kenneth_mathari
        "speaker_markus_wendland" -> Res.drawable.speaker_markus_wendland
        "speaker_mrinal_jain" -> Res.drawable.speaker_mrinal_jain
        "speaker_mugisa_brian" -> Res.drawable.speaker_mugisa_brian
        "speaker_pallavi_desai" -> Res.drawable.speaker_pallavi_desai
        "speaker_paul_mayero" -> Res.drawable.speaker_paul_mayero
        "speaker_priyank_shankar" -> Res.drawable.speaker_priyank_shankar
        "speaker_pulkit_midha" -> Res.drawable.speaker_pulkit_midha
        "speaker_roy_wanyoike" -> Res.drawable.speaker_roy_wanyoike
        "speaker_saheed_adewumi" -> Res.drawable.speaker_saheed_adewumi
        "speaker_sam_aricha" -> Res.drawable.speaker_sam_aricha
        "speaker_sylvia_dieckmann" -> Res.drawable.speaker_sylvia_dieckmann
        "speaker_zoe_farooq" -> Res.drawable.speaker_zoe_farooq
        else -> Res.drawable.speaker_ahmed_nabil // fallback
    }
}
