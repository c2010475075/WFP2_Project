@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wfp2_project.screens.note_list
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableInferredTarget
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wfp2_project.common.Note
import com.example.wfp2_project.screens.note.NoteEvent

@Composable
fun NoteListScreen(
    noteList: List<Note>,
    onNoteClick: (Note) -> Unit,
    onAddNoteClick: () -> Unit,
    onEvent: (NoteEvent) -> Unit

) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notes") },
                navigationIcon = {
                    IconButton(
                        onClick =  {}){
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNoteClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add note"
                )
            }
        }
    ) { padding ->
        NoteList(
            noteList = noteList,
            onNoteClick = onNoteClick,
            padding = padding,
            onEvent = onEvent
        )
    }
}

@Composable
fun NoteList(
    noteList: List<Note>,
    onNoteClick: (Note) -> Unit,
    padding: PaddingValues,
    onEvent: (NoteEvent) -> Unit

) {
    LazyColumn(contentPadding = padding) {
        item {
            Text(
                text = "Your Notes",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        items(noteList) { note ->
            NoteItem(
                note = note,
                onNoteClick,
                onEvent
            )
        }
    }
}


@Composable
fun DrawerContent(onClose: () -> Unit) {
    // Implementiere den Inhalt des Drawers hier, z.B. eine Liste von Navigationspunkten
    // Du kannst onClose verwenden, um den Drawer zu schließen, nachdem ein Element ausgewählt wurde
}
