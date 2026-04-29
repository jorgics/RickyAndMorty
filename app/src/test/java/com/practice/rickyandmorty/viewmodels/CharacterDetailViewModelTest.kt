package com.practice.rickyandmorty.viewmodels

import com.practice.rickyandmorty.core.data.exceptions.BaseException
import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.usecase.GetCharacterByIdUseCase
import com.practice.rickyandmorty.ui.detail.CharacterDetailIntent
import com.practice.rickyandmorty.ui.detail.CharacterDetailViewModel
import com.practice.rickyandmorty.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private val useCase: GetCharacterByIdUseCase = mockk()

    private lateinit var viewModel: CharacterDetailViewModel

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = CharacterDetailViewModel(useCase)
    }

    @Test
    fun `initial state is correct`() = runTest {

        val state = viewModel.state.value

        assertEquals(true, state.isLoading)
        assertEquals(null, state.error)
        assertEquals(Character(), state.character)
    }

    @Test
    fun `LoadCharacter with null id returns NoData error`() = runTest {

        viewModel.sendIntent(
            CharacterDetailIntent.LoadCharacter(null)
        )

        advanceTimeBy( 10)
        val state = viewModel.state.value
        assertTrue(state.error is BaseException.NoData)
        assertFalse(state.isLoading)
    }

    @Test
    fun `LoadCharacter success updates character`() = runTest {

        val character = Character(id = 1, name = "Rick")

        coEvery { useCase(1) } returns BaseResponse.Success(character)

        viewModel.sendIntent(
            CharacterDetailIntent.LoadCharacter(1)
        )

        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(character, state.character)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `LoadCharacter error updates error state`() = runTest {

        val exception = BaseException.Network("error")

        coEvery { useCase(1) } returns BaseResponse.Error(exception)

        viewModel.sendIntent(
            CharacterDetailIntent.LoadCharacter(1)
        )

        advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(exception, state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `LoadCharacter success with null data returns NoContent`() = runTest {

        coEvery { useCase(1) } returns BaseResponse.Success(null)

        viewModel.sendIntent(
            CharacterDetailIntent.LoadCharacter(1)
        )

        advanceUntilIdle()

        val state = viewModel.state.value

        assertTrue(state.error is BaseException.NoContent)
    }
}