package me.doogyb.countdown.models

import java.lang.Math.abs

fun <T> permute(list: List<T>): List<List<T>> {

    // base case

    if (list.size == 1) {
        return listOf(list)
    }

    var permutations = mutableListOf<List<T>>()

    for ((index, value) in list.withIndex()) {

        val subList = list.subList(0, index) + list.subList(index + 1, list.size)
        val permutedSubList = permute(subList)

        for (permutation in permutedSubList) {

            val inter = (mutableListOf<T>(value) + permutation)
            permutations.add(inter)
        }

    }

    return permutations

}

class PowerSet<T>(val inputSet: List<T>, val minLength: Int = 4) {

    var combinations = mutableListOf<List<T>>()

    init {
        powerset(listOf(), inputSet)
    }

    private fun powerset(curr: List<T>, remaining: List<T>) {

        if (curr.size > this.minLength) {
            combinations.add(curr)
        }

        if (remaining.isEmpty()) {
            return
        }

        for ((index, value) in remaining.withIndex()) {

            val subList = remaining.subList(0, index) + remaining.subList(index + 1, remaining.size)
            val newList = curr + listOf(value)
            powerset(newList, subList)
        }
    }
}

class NumberAnswer(val numbers: List<Int>, val target: Int) {

    var space = mutableMapOf<Double, String>();
    private val operators = listOf("+", "-", "/", "*")

    var closestAnswer: Double = 0.0
    var closestExpression: String = ""
    private var distance = 10000000.0

    init {
        for ((index, value) in numbers.withIndex()) {
            val subList = numbers.subList(0, index) + numbers.subList(index + 1, numbers.size)
            search(value.toDouble(), "", subList.map { it.toDouble() })
        }


    }
    fun Double.format(): String {
        val formatted = "%.${0}f".format(this)
        if (this.rem(1) == 0.0) {
            return formatted
        }
        return this.toString()
    }

    fun search(result: Double, expression: String, remaining: List<Double>) {

        space.put(result, expression)
        if (abs(result - target) < distance) {
            closestAnswer = result
            closestExpression = expression
            distance = abs(result - target)
        }

        if (remaining.isEmpty()) {
            return
        }

        for ((index, value) in remaining.withIndex()) {

            val subList = remaining.subList(0, index) + remaining.subList(index + 1, remaining.size)

            for (op in operators) {
                when (op) {
                    "+" -> {
                        val res = result + value
                        search(result + value, "$expression${result.format()} + ${value.format()} = ${res.format()}\n", subList)
                    }
                    "-" -> {
                        val res = result - value
                        search(result - value, "$expression${result.format()} - ${value.format()} = ${res.format()}\n", subList)
                    }
                    "/" -> {
                        val res = result / value
                        search(result / value, "$expression${result.format()} / ${value.format()} = ${res.format()}\n", subList)
                    }
                    "*" -> {
                        val res = result * value
                        search(result * value, "$expression${result.format()} * ${value.format()} = ${res.format()}\n", subList)
                    }
                }
            }

        }
    }
}
