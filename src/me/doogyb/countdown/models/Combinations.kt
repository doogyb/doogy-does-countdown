package me.doogyb.countdown.models

import kotlinx.css.input

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

    private fun powerset(curr: List<T>, left: List<T>) {

        if (curr.size > this.minLength) {
            combinations.add(curr)
        }

        if (left.isEmpty()) {
            return
        }

        for ((index, value) in left.withIndex()) {

            val subList = left.subList(0, index) + left.subList(index + 1, left.size)
            val newList = curr + listOf(value)
            powerset(newList, subList)
        }
    }
}
