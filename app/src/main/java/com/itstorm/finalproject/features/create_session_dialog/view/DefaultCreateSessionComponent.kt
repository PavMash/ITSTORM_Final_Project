package com.itstorm.finalproject.features.create_session_dialog.view

import com.arkivanov.decompose.ComponentContext
import com.itstorm.core_domain.repositories.SessionRepository

class DefaultCreateSessionComponent(
    componentContext: ComponentContext,
    sessionRepo: SessionRepository
): CreateSessionComponent, ComponentContext by componentContext {}