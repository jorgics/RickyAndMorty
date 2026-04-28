package com.practice.rickyandmorty.usecases

import com.practice.rickyandmorty.core.data.responses.BaseResponse
import com.practice.rickyandmorty.domain.model.Character
import com.practice.rickyandmorty.domain.repository.CharacterRepository
import com.practice.rickyandmorty.domain.usecase.GetCharacterByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCharacterByIdUseCaseTest {
    private val repository: CharacterRepository = mockk()
    private val useCase = GetCharacterByIdUseCase(repository)

    @Test
    fun `returns paging data`() = runTest {
        coEvery { repository.getCharacterById(1) } returns BaseResponse.Success(Character(id = 1, name = "Ricky"))
        val result = useCase(1)
        assertNotNull(result)
    }
}