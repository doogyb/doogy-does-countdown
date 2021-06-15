package me.doogyb.countdown.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import me.doogyb.countdown.models.toLetterAnswers
import me.doogyb.countdown.models.toNumberAnswer

fun Route.answersRouting() {
    route("/answers") {

        get("letters/{letters}") {
            var letters = call.parameters["letters"]?.split("") ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            letters = letters.subList(1, letters.size-1)
            if (letters.size > 9) {
                return@get call.respondText(
                    "Too many letters",
                    status = HttpStatusCode.NotAcceptable
                )
            }
            call.respond(letters.toLetterAnswers())
        }
        get("numbers/{numbers}") {

            val numbers = call.parameters["numbers"]?.split(",") ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val integers = numbers.map { it.toInt() }
            call.respond(integers.toNumberAnswer())
        }
    }
}

fun Application.registerAnswersRoutes() {
    routing {
        answersRouting()
    }
}