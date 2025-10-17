package com.droidcon.uganda.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droidcon.uganda.data.Speaker
import com.droidcon.uganda.ui.ConferenceViewModel
import com.droidcon.uganda.utils.TimeZoneUtils
import org.jetbrains.compose.resources.painterResource
import droidconuganda.composeapp.generated.resources.*

@Composable
fun SpeakersScreen(viewModel: ConferenceViewModel) {
    var selectedSpeaker by remember { mutableStateOf<Speaker?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Our Speakers",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(viewModel.speakers) { speaker ->
            SpeakerCard(
                speaker = speaker,
                onClick = { selectedSpeaker = speaker }
            )
        }
    }

    selectedSpeaker?.let { speaker ->
        SpeakerDetailDialog(
            speaker = speaker,
            sessions = viewModel.sessions.filter { it.speaker?.id == speaker.id },
            onDismiss = { selectedSpeaker = null }
        )
    }
}

@Composable
fun SpeakerCard(
    speaker: Speaker,
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isPressed = true
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Speaker photo
            Image(
                painter = painterResource(getSpeakerImageResource(speaker.imageUrl)),
                contentDescription = speaker.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Speaker Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    speaker.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    speaker.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    speaker.company,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Social Icons
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                speaker.twitter?.let {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Twitter",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                speaker.linkedin?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "LinkedIn",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
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
fun SpeakerDetailDialog(
    speaker: Speaker,
    sessions: List<com.droidcon.uganda.data.Session>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(getSpeakerImageResource(speaker.imageUrl)),
                    contentDescription = speaker.name,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(speaker.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        speaker.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Text(
                        speaker.company,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Text(
                        speaker.bio,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (speaker.twitter != null || speaker.linkedin != null) {
                    item {
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            speaker.twitter?.let {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Share,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        it,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                            speaker.linkedin?.let {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.AccountCircle,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        it,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            }
                        }
                    }
                }

                if (sessions.isNotEmpty()) {
                    item {
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Sessions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(sessions) { session ->
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                // Date if multi-day
                                val startTimeLocal = TimeZoneUtils.toUserLocalTime(session.startTime)
                                Text(
                                    TimeZoneUtils.formatShortDate(startTimeLocal),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val endTimeLocal = TimeZoneUtils.toUserLocalTime(session.endTime)
                                    Text(
                                        "${TimeZoneUtils.formatTime(startTimeLocal)} - ${TimeZoneUtils.formatTime(endTimeLocal)}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    // Track Badge - only show if display name is not empty
                                    if (session.track.displayName.isNotEmpty()) {
                                        session.track.color?.let { trackColor ->
                                            Surface(
                                                color = androidx.compose.ui.graphics.Color(trackColor),
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    session.track.displayName,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = androidx.compose.ui.graphics.Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    session.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
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
