package me.doogyb.countdown.models

import kotlinx.serialization.Serializable
import java.io.File

var words = File("./resources/data/words_alpha.txt").readLines().toSet()

@Serializable
data class LetterAnswers(
    private val letters: List<String>,
    private val combinations: List<String>,
    val answers: Set<String>
)

fun List<String>.toLetterAnswers(): LetterAnswers {
    val powerset = PowerSet<String>(this)
    val combinations = powerset.combinations.map { it.joinToString("") }
    val answers = words.intersect(combinations)
    return LetterAnswers(this, combinations, answers)
}