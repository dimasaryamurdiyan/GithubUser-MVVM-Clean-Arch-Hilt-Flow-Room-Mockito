package com.testcase.githubapp.utils

import com.testcase.githubapp.utils.Tes

object Tes {
    fun convertToTernary(N: Int) {
        // Base case
        var N = N
        if (N == 0) return

        // Finding the remainder
        // when N is divided by 3
        val x = N % 3
        N /= 3
        if (x < 0) N += 1

        // Recursive function to
        // call the function for
        // the integer division
        // of the value N/3
        convertToTernary(N)

        // Handling the negative cases
        if (x < 0) print(x + 3 * -1) else print(x)
    }
}