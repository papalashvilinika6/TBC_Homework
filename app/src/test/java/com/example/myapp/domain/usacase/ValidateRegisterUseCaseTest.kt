package com.example.myapp.domain.usecase

import com.example.myapp.domain.model.FieldType
import com.example.myapp.domain.model.KeyboardType
import com.example.myapp.domain.model.RegisterField
import org.junit.Assert.*
import org.junit.Test

class ValidateRegisterUseCaseTest {

    private val useCase = ValidateRegisterUseCase()

    private fun field(
        id: Int,
        hint: String,
        required: Boolean,
        isActive: Boolean = true
    ) = RegisterField(
        fieldId = id,
        hint = hint,
        fieldType = FieldType.INPUT,
        keyboardType = KeyboardType.TEXT,
        required = required,
        isActive = isActive,
        icon = null,
        options = emptyList()
    )

    @Test
    fun `returns valid when all required active fields are filled`() {
        val config = listOf(
            listOf(
                field(id = 1, hint = "Email", required = true),
                field(id = 2, hint = "Age", required = false)
            )
        )
        val values = mapOf(
            1 to "test@mail.com",
            2 to ""
        )

        val result = useCase(config, values)

        assertTrue(result.isValid)
        assertNull(result.error)
    }

    @Test
    fun `returns error when required active field is missing`() {
        val config = listOf(
            listOf(field(id = 1, hint = "Email", required = true))
        )
        val values = emptyMap<Int, String>()

        val result = useCase(config, values)

        assertFalse(result.isValid)
        val err = result.error
        assertTrue(err is RegisterValidationError.MissingRequiredField)
        err as RegisterValidationError.MissingRequiredField
        assertEquals(1, err.fieldId)
        assertEquals("Email", err.hint)
    }

    @Test
    fun `treats whitespace as blank`() {
        val config = listOf(
            listOf(field(id = 1, hint = "Name", required = true))
        )
        val values = mapOf(1 to "   ")

        val result = useCase(config, values)

        assertFalse(result.isValid)
        val err = result.error as RegisterValidationError.MissingRequiredField
        assertEquals(1, err.fieldId)
        assertEquals("Name", err.hint)
    }

    @Test
    fun `ignores inactive required fields`() {
        val config = listOf(
            listOf(field(id = 1, hint = "Hidden", required = true, isActive = false))
        )
        val values = emptyMap<Int, String>()

        val result = useCase(config, values)

        assertTrue(result.isValid)
        assertNull(result.error)
    }

    @Test
    fun `returns first missing required field in order`() {
        val f1 = field(id = 1, hint = "Email", required = true)
        val f2 = field(id = 2, hint = "Phone", required = true)

        val config = listOf(listOf(f1, f2))
        val values = mapOf(1 to "", 2 to "") 

        val result = useCase(config, values)

        assertFalse(result.isValid)
        val err = result.error as RegisterValidationError.MissingRequiredField
        assertEquals(1, err.fieldId)
        assertEquals("Email", err.hint)
    }
}
