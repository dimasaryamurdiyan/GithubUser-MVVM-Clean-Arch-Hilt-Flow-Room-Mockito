package com.testcase.githubapp.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateOrNull(navDirections: NavDirections?) {
    try {
        navDirections?.let { nullNavigation ->
            navigate(nullNavigation)
        }
    } catch (e: Exception) {
        // TODO : Add Error Fragment Navigation
    }
}