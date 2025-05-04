package org.example.api.adapter.cronjob

import org.example.core.application.usecase.command.assignorders.AssignOrdersCommand
import org.example.core.application.usecase.command.assignorders.AssignOrdersHandler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AssignOrdersJob(
    private val assignOrdersHandler: AssignOrdersHandler
) {

    @Scheduled(fixedRate = 1000)
    fun execute() {
        assignOrdersHandler.handle(AssignOrdersCommand())
    }
}