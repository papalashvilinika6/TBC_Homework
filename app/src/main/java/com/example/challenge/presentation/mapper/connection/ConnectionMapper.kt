package com.example.challenge.presentation.mapper.connection

import com.example.challenge.domain.model.connection.GetConnection
import com.example.challenge.presentation.screen.connection.Connection

/**
 * Presentation-layer mapper from domain `GetConnection` to UI `Connection`.
 */
fun GetConnection.toPresenter(): Connection =
    Connection(
        avatar = avatar,
        email = email,
        id = id,
        fullName = fullName
    )


