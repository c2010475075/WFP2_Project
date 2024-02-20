package com.example.wfp2_project.screens.note_list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoneOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wfp2_project.common.Note
import com.example.wfp2_project.screens.note.NoteEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteItem(
    note: Note,
    //onEditClick: () -> Unit,
    //onDeleteClick: () -> Unit,
    onNoteClick: (Note) -> Unit,
    onEvent: (NoteEvent) -> Unit
) {
    // Datum formatieren
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val dateString = note.date?.let { dateFormat.format(Date(it)) } ?: "No Date"

    Card(

        modifier = Modifier
            .fillMaxWidth().
            clickable(
                onClick = { onNoteClick(note) })
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = androidx.compose.material3.MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
// Datum anzeigen
            Text(
                text = "Start on $dateString",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(

                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()


            ) {
                IconButton(onClick = {onEvent(NoteEvent.DeleteNote)}) {
                    Icon(
                        imageVector = Icons.Rounded.DoneOutline,
                        contentDescription = "Delete"
                    )

                }
            }
        }
    }
}

