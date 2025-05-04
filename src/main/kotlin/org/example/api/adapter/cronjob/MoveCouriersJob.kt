package org.example.api.adapter.cronjob

import org.example.core.application.usecase.command.movecouriers.MoveCouriersCommand
import org.example.core.application.usecase.command.movecouriers.MoveCouriersHandler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MoveCouriersJob(
    private val moveCouriersHandler: MoveCouriersHandler
) {

    @Scheduled(fixedRate = 2000)
    fun execute() {
        moveCouriersHandler.handle(MoveCouriersCommand())
    }
}