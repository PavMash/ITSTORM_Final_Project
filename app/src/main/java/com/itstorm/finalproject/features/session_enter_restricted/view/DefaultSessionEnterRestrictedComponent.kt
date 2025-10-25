package com.itstorm.finalproject.features.session_enter_restricted.view

import com.arkivanov.decompose.ComponentContext
import com.itstorm.finalproject.root.flow.user.UserFlowComponent

class DefaultSessionEnterRestrictedComponent(
    componentContext: ComponentContext
): SessionEnterRestrictedComponent, ComponentContext by componentContext {}