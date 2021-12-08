fun main() {
    fun getGammaEpsilonRates(array: Array<BitCounter>): Pair<Int, Int> {
        val gammaRate = CharArray(12)
        val epsilonRate = CharArray(12)

        array.forEachIndexed { i, bit ->
            if (bit.oneBitCounter > bit.zeroBitCounter) {
                gammaRate[i] = '0'
                epsilonRate[i] = '1'
            } else {
                gammaRate[i] = '1'
                epsilonRate[i] = '0'
            }
        }

        return Pair(gammaRate.joinToString("").toInt(2), epsilonRate.joinToString("").toInt(2))
    }

    fun getPowerConsumption(input: List<String>): Int {
        val bitCounterArray = Array(size = input.first().length) { BitCounter() }

        input.forEach { bits ->
            val transformedBits = bits.toCharArray()

            transformedBits.forEachIndexed { i, bit ->
                if (bit == '0') {
                    bitCounterArray[i].zeroBitCounter++
                } else {
                    bitCounterArray[i].oneBitCounter++
                }
            }
        }

        val (gammaRate, epsilonRate) = getGammaEpsilonRates(bitCounterArray)

        return gammaRate * epsilonRate
    }

    fun ratingRecursion(
        input: List<String>,
        index: Int,
        ratingDecider: Char,
    ): String {
        val bitCounterArray = Array(size = input.first().length) { BitCounter() }

        if (input.size == 1) {
            return input.first()
        }

        val mutableInput = input.toMutableList()

        input.forEach { bits ->
            val transformedBits = bits.toCharArray()

            transformedBits.forEachIndexed { i, bit ->
                if (bit == '0') {
                    bitCounterArray[i].zeroBitCounter++
                } else {
                    bitCounterArray[i].oneBitCounter++
                }
            }
        }

        val mostCommon = when {
            bitCounterArray[index].oneBitCounter > bitCounterArray[index].zeroBitCounter -> ratingDecider
            bitCounterArray[index].oneBitCounter == bitCounterArray[index].zeroBitCounter -> ratingDecider
            else -> if (ratingDecider == '0') '1' else if (ratingDecider == '1') '0' else ' '
        }

        input.forEach { bits ->
            val transformedBits = bits.toCharArray()

            if (transformedBits[index] != mostCommon) {
                mutableInput.remove(bits)
            }
        }

        return ratingRecursion(mutableInput, index + 1, ratingDecider)
    }

    fun getLifeSupportRating(input: List<String>): Int {
        val oxygenGeneratorRating = ratingRecursion(input, index = 0, '1')
        val co2ScrubberRating = ratingRecursion(input, index = 0, '0')

        return oxygenGeneratorRating.toInt(2) * co2ScrubberRating.toInt(2)
    }

    val testInput = readInputAsListOfString("Day03_test")
    println(getLifeSupportRating(testInput) == 230)

    val input = readInputAsListOfString("Day03")
    println(getPowerConsumption(input))
    println(getLifeSupportRating(input))
}

data class BitCounter(var zeroBitCounter: Int = 0, var oneBitCounter: Int = 0)
