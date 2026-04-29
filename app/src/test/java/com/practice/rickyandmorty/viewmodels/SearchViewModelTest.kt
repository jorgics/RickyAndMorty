package com.practice.rickyandmorty.viewmodels

import app.cash.turbine.test
import com.practice.rickyandmorty.ui.search.SearchIntent
import com.practice.rickyandmorty.ui.search.SearchViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel()
    }

    @Test
    fun `initial state should be default`() = runTest {
        viewModel.state.test {
            val state = awaitItem()

            assertEquals(null, state.name)
            assertEquals(false, state.search)
            assertEquals(null, state.filter)
        }
    }

    @Test
    fun `SelectedName should update name`() = runTest {
        viewModel.state.test {
            awaitItem()

            viewModel.sendIntent(SearchIntent.SelectedName("Rick"))

            val state = awaitItem()

            assertEquals("Rick", state.name)
        }
    }

    @Test
    fun `ApplyFilter should create filter and set search true`() = runTest {
        viewModel.state.test {
            awaitItem()

            viewModel.sendIntent(SearchIntent.SelectedName("Morty"))
            awaitItem()

            viewModel.sendIntent(SearchIntent.ApplyFilter)

            val state = awaitItem()

            assertEquals(true, state.search)
            assertEquals("Morty", state.filter?.name)
        }
    }

    @Test
    fun `SearchClear should reset search flag`() = runTest {
        viewModel.state.test {
            awaitItem()

            viewModel.sendIntent(SearchIntent.ApplyFilter)
            awaitItem()

            viewModel.sendIntent(SearchIntent.SearchClear)

            val state = awaitItem()

            assertEquals(false, state.search)
        }
    }

    @Test
    fun `full flow should update state correctly`() = runTest {
        viewModel.state.test {
            awaitItem()

            viewModel.sendIntent(SearchIntent.SelectedName("Summer"))
            val state1 = awaitItem()
            assertEquals("Summer", state1.name)

            viewModel.sendIntent(SearchIntent.ApplyFilter)
            val state2 = awaitItem()
            assertEquals(true, state2.search)
            assertEquals("Summer", state2.filter?.name)

            viewModel.sendIntent(SearchIntent.SearchClear)
            val state3 = awaitItem()
            assertEquals(false, state3.search)
        }
    }
}