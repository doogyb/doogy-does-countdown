import kotlin.random.Random

val vowels = "aeiou".split("").subList(1, 6)
val c = "bcdfghjklmnpqrstvwxyz".split("")
val consonants = c.subList(1, c.size - 1)

fun <T> List<T>.choose() = this[Random.nextInt(this.size)]

fun vowel() = vowels.choose()
fun consonant() = consonants.choose()