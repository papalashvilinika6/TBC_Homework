package com.example.myapplication.domain.model

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver { val isConnected: Flow<Boolean> }