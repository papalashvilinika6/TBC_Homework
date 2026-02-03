package com.example.myapp.feature.register

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType as ComposeKeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapp.core.theme.*
import com.example.myapp.domain.model.FieldType
import com.example.myapp.domain.model.KeyboardType
import com.example.myapp.domain.model.RegisterField
import java.util.Calendar

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(RegisterEvent.Load)
    }

    LaunchedEffect(state.snackbarMessage) {
        val msg = state.snackbarMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(msg.asString(context))
        viewModel.onEvent(RegisterEvent.OnSnackbarShown)
    }

    RegisterScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    snackbarHostState: SnackbarHostState,
    onEvent: (RegisterEvent) -> Unit
) {
    val colors = AppThemeProvider.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Spacing.spacing16)
                .padding(top = Spacing.spacing16)
        ) {
            HeaderRow()

            Spacer(modifier = Modifier.height(Spacing.spacing16))

            if (state.isLoading && state.config.isEmpty()) {
                LoadingCard(text = stringResource(R.string.loading))
            } else {
                state.config
                    .map { group -> group.filter { it.isActive } }
                    .filter { it.isNotEmpty() }
                    .forEach { group ->
                        FieldGroupCard(
                            fields = group,
                            values = state.values,
                            onValueChange = { id, v -> onEvent(RegisterEvent.OnValueChange(id, v)) }
                        )
                        Spacer(modifier = Modifier.height(Spacing.spacing16))
                    }
            }

            Spacer(modifier = Modifier.weight(1f))

            RegisterButton(
                onClick = { onEvent(RegisterEvent.OnRegisterClick) }
            )

            Spacer(modifier = Modifier.height(Spacing.spacing16))

        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Spacing.spacing12)
        ) {
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}

@Composable
private fun HeaderRow() {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.e_auth),
            style = typography.titleMedium,
            color = colors.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.register),
            style = typography.titleMedium,
            color = colors.primary
        )
    }
}

@Composable
private fun LoadingCard(text: String) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.radius24))
            .background(colors.surface)
            .padding(Spacing.spacing16),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.bodyMedium,
            color = colors.muted
        )
    }
}

@Composable
private fun FieldGroupCard(
    fields: List<RegisterField>,
    values: Map<Int, String>,
    onValueChange: (Int, String) -> Unit
) {
    val colors = AppThemeProvider.colors

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Radius.radius24))
            .background(colors.surface)
            .padding(vertical = Spacing.spacing12)
    ) {
        fields.forEachIndexed { index, field ->
            when (field.fieldType) {
                FieldType.INPUT -> InputFieldRow(
                    field = field,
                    value = values[field.fieldId].orEmpty(),
                    onValueChange = { onValueChange(field.fieldId, it) }
                )
                FieldType.CHOOSER -> ChooserFieldRow(
                    field = field,
                    value = values[field.fieldId].orEmpty(),
                    onValueChange = { onValueChange(field.fieldId, it) }
                )
            }

            if (index != fields.lastIndex) {
                DividerLine()
            }
        }
    }
}

@Composable
private fun DividerLine() {
    val colors = AppThemeProvider.colors
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colors.divider)
    )
}

@Composable
private fun InputFieldRow(
    field: RegisterField,
    value: String,
    onValueChange: (String) -> Unit
) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.fieldHeight)
            .padding(horizontal = Spacing.spacing16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = colors.onSurface),
            cursorBrush = SolidColor(colors.primary),
            keyboardOptions = KeyboardOptions(
                keyboardType = when (field.keyboardType) {
                    KeyboardType.NUMBER -> ComposeKeyboardType.Number
                    KeyboardType.TEXT -> ComposeKeyboardType.Text
                }
            ),
            modifier = Modifier.weight(1f),
            decorationBox = { inner ->
                if (value.isBlank()) {
                    Text(
                        text = field.hint,
                        style = typography.bodyMedium,
                        color = colors.muted
                    )
                }
                inner()
            }
        )

        Spacer(modifier = Modifier.width(Spacing.spacing12))

        TrailingIcon(iconUrl = field.icon)
    }
}

@Composable
private fun ChooserFieldRow(
    field: RegisterField,
    value: String,
    onValueChange: (String) -> Unit
) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    val openDatePicker = {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, y, m, d ->
                val mm = (m + 1).toString().padStart(2, '0')
                val dd = d.toString().padStart(2, '0')
                onValueChange(context.getString(R.string.ymd, y, mm, dd))
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.fieldHeight)
            .clickable {
                if (field.options.isEmpty()) openDatePicker() else expanded = true
            }
            .padding(horizontal = Spacing.spacing16),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.ifBlank { field.hint },
                style = typography.bodyMedium,
                color = if (value.isBlank()) colors.muted else colors.onSurface,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(Spacing.spacing12))

            TrailingIcon(iconUrl = field.icon)

            if (field.options.isNotEmpty()) {
                Dropdown(
                    expanded = expanded,
                    onDismiss = { expanded = false },
                    options = field.options,
                    onSelect = {
                        expanded = false
                        onValueChange(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun Dropdown(
    expanded: Boolean,
    onDismiss: () -> Unit,
    options: List<String>,
    onSelect: (String) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        options.forEach { opt ->
            DropdownMenuItem(
                text = { Text(opt) },
                onClick = { onSelect(opt) }
            )
        }
    }
}

@Composable
private fun TrailingIcon(iconUrl: String?) {
    val colors = AppThemeProvider.colors
    Box(
        modifier = Modifier
            .size(IconSize.trailingIcon)
            .clip(RoundedCornerShape(Radius.radius999))
            .background(colors.primary.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        if (!iconUrl.isNullOrBlank()) {
            AsyncImage(
                model = iconUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun RegisterButton(onClick: () -> Unit) {
    val colors = AppThemeProvider.colors
    val typography = AppThemeProvider.typography

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Height.buttonHeight)
            .clip(RoundedCornerShape(Radius.radius24))
            .background(colors.primary)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.registerButton),
            style = typography.titleMedium,
            color = colors.onPrimary
        )
    }
}
