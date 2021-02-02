package me.doogyb.countdown.routes

import consonant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import vowel

fun Route.randomGeneratorRouting() {
    route("/letter") {

        get("vowel") {
            call.respond(vowel())
        }
        get("consonant") {
            call.respond(consonant())
        }
    }
}

fun Application.registerRandomGeneratorRoutes() {
    routing {
        randomGeneratorRouting()
    }
}