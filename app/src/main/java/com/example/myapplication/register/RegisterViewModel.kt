package com.example.myapplication.register

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.field.Field
import com.example.myapplication.field.FieldType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray

class RegisterViewModel : ViewModel() {

    fun loadFromJson(context: Context): List<List<Field>> {
        val json = context.assets.open("fake_fields.json").bufferedReader().use { it.readText() }
        val outerArray = JSONArray(json)

        val groupList = mutableListOf<List<Field>>()

        for (i in 0 until outerArray.length()) {
            val innerArray = outerArray.getJSONArray(i)
            val fields = mutableListOf<Field>()

            for (j in 0 until innerArray.length()) {
                val obj = innerArray.getJSONObject(j)

                val typeString = obj.getString("field_type").uppercase()
                val fieldType = try {
                    FieldType.valueOf(typeString)
                } catch (e: IllegalArgumentException) {
                    FieldType.INPUT
                }

                fields.add(
                    Field(
                        fieldId = obj.getInt("field_id"),
                        hint = obj.getString("hint"),
                        fieldType = fieldType,
                        keyboard = obj.optString("keyboard", "text"),
                        required = obj.getBoolean("required"),
                        isActive = obj.getBoolean("is_active"),
                        icon = obj.getString("icon")
                    )
                )
            }


            groupList.add(fields)
        }

        return groupList
    }



    private val _fieldValues = MutableStateFlow<MutableMap<Int, String>>(mutableMapOf())
    val fieldValues: StateFlow<Map<Int, String>> get() = _fieldValues

    fun updateField(fieldId: Int, value: String) {
        val map = _fieldValues.value.toMutableMap()
        map[fieldId] = value
        _fieldValues.value = map
    }

    fun validateFields(fields: List<Field>): List<String> {
        val errors = mutableListOf<String>()
        fields.forEach { field ->
            val value = _fieldValues.value[field.fieldId].orEmpty()
            if (field.required && value.isBlank()) {
                errors.add("Field not filled: ${field.hint}")
            }
        }
        return errors
        }

}