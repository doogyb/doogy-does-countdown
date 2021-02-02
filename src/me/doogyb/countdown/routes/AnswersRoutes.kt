package me.doogyb.countdown.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import me.doogyb.countdown.models.toLetterAnswers

fun Route.answersRouting() {
    route("/answers") {

        get("letters/{letters}") {
            val letters = call.parameters["letters"]?.split("") ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val letterAnswers = letters.subList(1, letters.size-1).toLetterAnswers()
            call.respond(letterAnswers.answers)
        }
        get("numbers") {
            return@get call.respondText(
                "Not implemented yet",
                status = HttpStatusCode.NotImplemented
            )
        }
    }
}

fun Application.registerAnswersRoutes() {
    routing {
        answersRouting()
    }
}