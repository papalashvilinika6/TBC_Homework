package com.example.myapp.presentation.screen.register

import com.example.myapp.R
import com.example.myapp.domain.model.FieldType
import com.example.myapp.domain.model.KeyboardType
import com.example.myapp.domain.model.RegisterField
import com.example.myapp.domain.model.Resource
import com.example.myapp.domain.usecase.GetRegisterConfigUseCase
import com.example.myapp.domain.usecase.RegisterValidationError
import com.example.myapp.domain.usecase.ValidateRegisterUseCase
import com.example.myapp.domain.usecase.ValidationResult
import com.example.myapp.presentation.common.UiText
import com.example.myapp.common.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRegisterConfig: GetRegisterConfigUseCase = mockk()
    private val validateRegister: ValidateRegisterUseCase = mockk()

    private fun createVm(): RegisterViewModel =
        RegisterViewModel(
            getRegisterConfig = getRegisterConfig,
            validateRegister = validateRegister
        )

    @Test
    fun `load - emits loader true then success sets config`() = runTest {
        // Arrange
        val fakeConfig = emptyList<List<RegisterField>>()
        every { getRegisterConfig.invoke() } returns flowOf(
            Resource.Loader(true),
            Resource.Success(fakeConfig)
        )

        val vm = createVm()

        // Act
        vm.onEvent(RegisterEvent.Load)

        // Assert
        val st = vm.state.value
        Assert.assertFalse(st.isLoading)
        Assert.assertEquals(fakeConfig, st.config)
        Assert.assertNull(st.snackbarMessage)
    }

    @Test
    fun `load - error sets snackbar UiText Dynamic`() = runTest {
        // Arrange
        every { getRegisterConfig.invoke() } returns flowOf(
            Resource.Loader(true),
            Resource.Error("boom")
        )
        val vm = createVm()

        // Act
        vm.onEvent(RegisterEvent.Load)

        // Assert
        val st = vm.state.value
        Assert.assertFalse(st.isLoading)
        Assert.assertTrue(st.snackbarMessage is UiText.Dynamic)
        Assert.assertEquals("boom", (st.snackbarMessage as UiText.Dynamic).value)
    }

    @Test
    fun `onValueChange - updates values map`() = runTest {
        // Arrange
        every { getRegisterConfig.invoke() } returns flowOf()
        val vm = createVm()

        // Act
        vm.onEvent(RegisterEvent.OnValueChange(fieldId = 10, value = "abc"))

        // Assert
        val st = vm.state.value
        Assert.assertEquals("abc", st.values[10])
    }

    @Test
    fun `onRegister - invalid returns MissingRequiredField - sets snackbar Resource error_empty_field`() =
        runTest {
            // Arrange
            val vm = createVm()

            every { validateRegister.invoke(any(), any()) } returns ValidationResult(
                isValid = false,
                error = RegisterValidationError.MissingRequiredField(fieldId = 1, hint = "Email")
            )

            // Act
            vm.onEvent(RegisterEvent.OnRegisterClick)

            // Assert
            val st = vm.state.value
            val msg = st.snackbarMessage
            Assert.assertNotNull(msg)
            Assert.assertTrue(msg is UiText.Resource)
            msg as UiText.Resource
            Assert.assertEquals(R.string.error_empty_field, msg.resId)
            Assert.assertEquals(listOf("Email"), msg.args)
        }

    @Test
    fun `onRegister - invalid with null error - sets snackbar Dynamic Invalid data`() = runTest {
        // Arrange
        val vm = createVm()
        every { validateRegister.invoke(any(), any()) } returns ValidationResult(
            isValid = false,
            error = null
        )

        // Act
        vm.onEvent(RegisterEvent.OnRegisterClick)

        // Assert
        val st = vm.state.value
        Assert.assertTrue(st.snackbarMessage is UiText.Dynamic)
        Assert.assertEquals("Invalid data", (st.snackbarMessage as UiText.Dynamic).value)
    }


    @Test
    fun `OnSnackbarShown clears snackbarMessage`() = runTest {
        // Arrange
        val vm = createVm()
        every { getRegisterConfig.invoke() } returns flowOf(Resource.Error("boom"))
        vm.onEvent(RegisterEvent.Load)
        Assert.assertNotNull(vm.state.value.snackbarMessage)

        // Act
        vm.onEvent(RegisterEvent.OnSnackbarShown)

        // Assert
        Assert.assertNull(vm.state.value.snackbarMessage)
    }
}