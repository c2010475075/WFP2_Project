package com.example.wfp2_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wfp2_project.navigation.Route
import com.example.wfp2_project.navigation.UiEvent
import com.example.wfp2_project.screens.note.NoteEvent
import com.example.wfp2_project.screens.note.NoteScreen
import com.example.wfp2_project.screens.note_list.NoteListScreen
import com.example.wfp2_project.ui.theme.WFP2_ProjectTheme
import com.example.wfp2_project.viewmodels.note.NoteViewModel
import com.example.wfp2_project.viewmodels.note_list.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WFP2_ProjectTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Route.noteList
                ) {
                    composable(route = Route.noteList) {
                        val viewModel = hiltViewModel<NoteListViewModel>()
                        val noteList by viewModel.noteList.collectAsStateWithLifecycle()

                        NoteListScreen(
                            noteList = noteList,
                            onNoteClick = {
                                navController.navigate(
                                    Route.note.replace(
                                        "{id}",
                                        it.id.toString()
                                    )
                                )
                            },
                            onAddNoteClick = {
                                navController.navigate(Route.note)
                            },
                            onEvent = {
                                NoteEvent.DeleteNote

                            }

                        )
                    }

                    composable(route = Route.note) {
                        val viewModel = hiltViewModel<NoteViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = true) {
                            viewModel.event.collect { event ->
                                when (event) {
                                    is UiEvent.NavigateBack -> {
                                        navController.popBackStack()
                                    }

                                    else -> Unit
                                }
                            }
                        }

                        NoteScreen(
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}