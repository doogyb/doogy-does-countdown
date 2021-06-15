import com.beust.klaxon.Klaxon
import java.io.File
import kotlin.random.Random

val letterDistJson = File("./resources/data/letter_frequencies.json").readText()

val vowels = "aeiou".split("").subList(1, 6)
val c = "bcdfghjklmnpqrstvwxyz".split("")
val consonants = c.subList(1, c.size - 1)

val letterDist = Klaxon()
    .parse<Map<String, Double>>(letterDistJson)!!

fun getDistribution(letters: List<String>, distribution: Map<String, Double>): MutableList<String> {
    var ret = mutableListOf<String>()
    for (v in letters) {
        val multiplier = distribution.get(v)?.times(10000)?.toInt()
        ret.addAll((1..multiplier!!).map { v })
    }
    return ret
}

val vowelDistribution = getDistribution(vowels, letterDist)
val consonantDistribution = getDistribution(consonants, letterDist)

fun <T> List<T>.choose() = this[Random.nextInt(this.size)]

fun vowel() = vowelDistribution.choose()
fun consonant() = consonantDistribution.choose()

fun large() = listOf(10, 25, 50, 75, 100).choose()
fun small() = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).choose()