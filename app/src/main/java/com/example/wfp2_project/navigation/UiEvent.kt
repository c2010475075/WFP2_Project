package com.example.wfp2_project.navigation

sealed interface UiEvent {
    data class Navigate(val route: String): UiEvent
    object NavigateBack : UiEvent
}