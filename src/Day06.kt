fun main() {
    fun simulateReproduction(input: String, days: Int): Long {
        val initialState = input.split(',').map { it.toLong() }

        val fishArray = LongArray(9) { i -> initialState.filter { i == it.toInt() }.size.toLong() }

        repeat(days) {
            fishArray.rotateLeftInPlaceBetter()
            fishArray[6] += fishArray[8]
        }

        return fishArray.sum()
    }

    val testInput = readInputAsString("Day06_test")
    check(simulateReproduction(testInput, 80) == 5934L)

    val input = readInputAsString("Day06")
    println(simulateReproduction(input, 80))
    println(simulateReproduction(input, 256))
}

private fun LongArray.rotateLeftInPlace() {
    val leftMost = this.first()

    for (i in 0 until this.size - 1) {
        this[i] = this[i + 1]
    }

    this[this.lastIndex] = leftMost
}

private fun LongArray.rotateLeftInPlaceBetter() {
    val leftMost = this.first()

    this.copyInto(this, startIndex = 1)

    this[this.lastIndex] = leftMost
}
