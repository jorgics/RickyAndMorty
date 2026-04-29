package com.practice.rickyandmorty.usecases

import androidx.paging.PagingData
import app.cash.turbine.test
import com.practice.rickyandmorty.domain.model.CharacterFilter
import com.practice.rickyandmorty.domain.model.Gender
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import com.practice.rickyandmorty.domain.usecase.GetCharactersByFilterUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCharactersByFilterUseCaseTest {

    private val repository: CharacterRepository = mockk()
    private val useCase = GetCharactersByFilterUseCase(repository)

    @Test
    fun `returns paging data`() = runTest {
        coEvery {
            repository.getPagedCharacters(any())
        } returns flowOf(PagingData.empty())

        useCase(CharacterFilter()).test {
            val result = awaitItem()
            assertNotNull(result)
            awaitComplete()
        }
    }

    @Test
    fun `passes correct filter to repository`() = runTest {

        val slot = slot<CharacterFilter>()

        coEvery {
            repository.getPagedCharacters(capture(slot))
        } returns flowOf(PagingData.empty())

        useCase(CharacterFilter(gender = Gender.Male.value)).test {
            awaitItem()
            awaitComplete()
        }

        assertEquals(Gender.Male.value, slot.captured.gender)
    }
}