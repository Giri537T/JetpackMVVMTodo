package com.giri.mvvm_todo_app.util

/**
 * UiEvent: to know the screen  on click events
 */
sealed class UiEvent {

    object PopBackStack : UiEvent()

    data class Navigate(val route: String) : UiEvent()

    data class showErrorPopUp(val show: Boolean? = false) : UiEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ) : UiEvent()

}