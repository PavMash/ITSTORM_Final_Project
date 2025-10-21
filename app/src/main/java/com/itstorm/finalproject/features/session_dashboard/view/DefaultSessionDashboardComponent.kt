package com.itstorm.finalproject.features.session_dashboard.view

import androidx.room.util.convertUUIDToByte
import com.arkivanov.decompose.ComponentContext

class DefaultSessionDashboardComponent(
    componentContext: ComponentContext
): SessionDashboardComponent, ComponentContext by componentContext {}