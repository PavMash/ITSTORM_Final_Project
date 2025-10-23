package com.itstorm.finalproject.features.update_session_dialog.view

import com.arkivanov.decompose.ComponentContext
import com.itstorm.core_domain.repositories.SessionRepository

class DefaultUpdateSessionComponent(
    componentContext: ComponentContext,
    sessionRepo: SessionRepository,
    sessionId: Long
): UpdateSessionComponent, ComponentContext by componentContext {}