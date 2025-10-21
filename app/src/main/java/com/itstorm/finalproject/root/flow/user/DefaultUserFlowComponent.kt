package com.itstorm.finalproject.root.flow.user

import com.arkivanov.decompose.ComponentContext

class DefaultUserFlowComponent(
    componentContext: ComponentContext
): UserFlowComponent, ComponentContext by componentContext {}