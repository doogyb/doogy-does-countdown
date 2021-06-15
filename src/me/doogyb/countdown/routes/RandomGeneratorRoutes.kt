package me.doogyb.countdown.routes

import consonant
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import large
import small
import vowel

fun Route.randomGeneratorRouting() {
    route("/random") {

        get("vowel") {
            call.respond(vowel())
        }
        get("consonant") {
            call.respond(consonant())
        }
        get("large") {
            call.respond(large())
        }
        get("small") {
            call.respond(small())
        }
    }
}

fun Application.registerRandomGeneratorRoutes() {
    routing {
        randomGeneratorRouting()
    }
}