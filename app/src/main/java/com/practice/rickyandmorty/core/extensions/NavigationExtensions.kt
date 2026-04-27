package com.practice.rickyandmorty.core.extensions

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(route: NavKey) {
    if (lastOrNull() != route) {
        add(route)
    }
}

fun NavBackStack<NavKey>.navigateToSingleTop(route: NavKey) {
    if (lastOrNull() != route) {
        if (isNotEmpty()) removeLastOrNull()
        add(route)
    }
}

fun NavBackStack<NavKey>.back() {
    if (isEmpty()) return
    removeLastOrNull()
}

fun NavBackStack<NavKey>.backTo(targetScreen: NavKey) {
    if (isEmpty()) return
    if(targetScreen !in this) return

    while(isNotEmpty() && last() != targetScreen){
        removeLastOrNull()
    }

}