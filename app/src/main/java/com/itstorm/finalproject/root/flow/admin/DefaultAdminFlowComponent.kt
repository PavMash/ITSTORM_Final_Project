package com.itstorm.finalproject.root.flow.admin

import com.arkivanov.decompose.ComponentContext
import com.itstorm.finalproject.root.flow.user.UserFlowComponent

class DefaultAdminFlowComponent(
    componentContext: ComponentContext
): AdminFlowComponent, ComponentContext by componentContext {}