@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.wfp2_project.screens.note

import androidx.compose.material3.ExperimentalMaterial3Api
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wfp2_project.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    val context = LocalContext.current
    var showDialogControl = remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    val dateFormat = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }
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
                title = { Text("Create your tasks")     },
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
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = state.title,
                onValueChange = {
                    onEvent(NoteEvent.TitleChange(it))
                },
                placeholder = {
                    Text(text = "Title",style = TextStyle(
                        fontSize = 32.sp,
                    ) )
                },colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),

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
                    Text(
                        text = "Description"
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { showDialogControl.value = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Date",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(text = "Pick Date",
                    modifier = Modifier
                        .clickable {
                            showDialogControl.value = true
                        }.padding(start = 8.dp)
                )
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
        }}}
