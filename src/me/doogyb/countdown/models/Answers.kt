package me.doogyb.countdown.models

import kotlinx.serialization.Serializable
import java.io.File

var words = File("./resources/data/words_alpha.txt").readLines().toSet()

fun Set<String>.lengthWordMap(minLength: Int = 5): MutableMap<Int, MutableList<String>> {
    var ret = mutableMapOf<Int, MutableList<String>>()
    for (word in this) {
        var strings = ret.getOrDefault(word.length, mutableListOf<String>())
        strings.add(word)
        ret[word.length] = strings
    }
    return ret
}

fun List<String>.toLetterAnswers(): MutableMap<Int, MutableList<String>> {
    val powerset = PowerSet<String>(this)
    val combinations = powerset.combinations.map { it.joinToString("") }

    val answers = words.intersect(combinations).lengthWordMap()
    return answers
}

fun List<Int>.toNumberAnswer(): List<String> {
    val answer = NumberAnswer(this.subList(0, this.size-1), this[this.size-1])
    return listOf(answer.closestAnswer.toString(), answer.closestExpression)
}