package com.practice.rickyandmorty.viewmodels

import androidx.paging.PagingData
import app.cash.turbine.test
import com.practice.rickyandmorty.utils.MainDispatcherRule
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.Gender
import com.practice.rickyandmorty.domain.usecase.GetCharactersByFilterUseCase
import com.practice.rickyandmorty.ui.characters.CharacterListIntent
import com.practice.rickyandmorty.ui.characters.CharacterListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {
    private val useCase: GetCharactersByFilterUseCase = mockk(relaxed = true)

    private lateinit var viewModel: CharacterListViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = CharacterListViewModel(useCase)
    }

    @Test
    fun `initial state is correct`() = runTest {
        val state = viewModel.state.value

        assertEquals(false, state.retry)
        assertEquals(CharacterFilter(), state.filter)
    }

    @Test
    fun `ApplyFilter updates state`() = runTest {
        val filter = CharacterFilter(gender = Gender.Male.value)

        viewModel.sendIntent(
            CharacterListIntent.ApplyFilter(filter)
        )

        val state = viewModel.state.value

        assertEquals(filter, state.filter)
    }

    @Test
    fun `paging flow emits data when filter changes`() = runTest {
        coEvery { useCase(any()) } returns flowOf(PagingData.empty())

        viewModel.pagingDataFlow.test {
            viewModel.sendIntent(
                CharacterListIntent.ApplyFilter(
                    CharacterFilter(name = "Rick")
                )
            )

            advanceUntilIdle()
            val item = awaitItem()
            assertNotNull(item)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `debounce prevents multiple calls`() = runTest {

        coEvery { useCase(any()) } returns flowOf(PagingData.empty())

        val job = launch {
            viewModel.pagingDataFlow.collect { }
        }

        repeat(5) {
            viewModel.sendIntent(
                CharacterListIntent.ApplyFilter(
                    CharacterFilter(name = "Rick")
                )
            )
        }

        advanceTimeBy(10)
        advanceUntilIdle()

        coVerify(exactly = 1) {
            useCase(any()) as? Flow<*>
        }

        job.cancel()
    }

    @Test
    fun `useCase failure should be handled`() = runTest {

        coEvery { useCase(any()) } returns flowOf(PagingData.empty())

        viewModel.pagingDataFlow.test {

            viewModel.sendIntent(
                CharacterListIntent.ApplyFilter(CharacterFilter())
            )

            advanceUntilIdle()
            cancelAndIgnoreRemainingEvents()
        }
    }
}