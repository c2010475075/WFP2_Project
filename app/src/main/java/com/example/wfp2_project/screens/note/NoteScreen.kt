@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wfp2_project.screens.note

import androidx.compose.material3.ExperimentalMaterial3Api

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    val context = LocalContext.current
    var showDialogControl = remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    // Setze den Listener für den DatePickerDialog
    if (showDialogControl.value) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }
                val selectedDate = calendar.timeInMillis
                onEvent(NoteEvent.DateChange(selectedDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()

        showDialogControl.value = false
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { "Create your tasks" },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(NoteEvent.NavigateBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "navigate back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(NoteEvent.DeleteNote)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "delete"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(
                    horizontal = 20.dp,
                    vertical = 15.dp
                ),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.title,
                onValueChange = {
                    onEvent(NoteEvent.TitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                }

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.content,
                onValueChange = {
                    onEvent(NoteEvent.ContentChange(it))
                },
                placeholder = {
                    Text(text = "Description")
                },
                label = {
                    Text(text= "Description"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                )
            )

            // Datum auswählen Button

            Button(
                onClick = { showDialogControl.value = true },
            ) {
                Text(text = "Pick Date")
            }

            // Anzeige des ausgewählten Datums
            if (state.date != null) {
                Text(
                    text = "Selected date: ${dateFormat.format(Date(state.date))}",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Button(
                onClick = {
                    onEvent(NoteEvent.Save)
                },
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(text = "Save")
            }
        }
    }
}
