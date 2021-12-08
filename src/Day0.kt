fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInputAsListOfString("Day01_test")
    check(part2(testInput) == 5)

    val input = readInputAsListOfString("Day01")
    println(part1(input))
    println(part2(input))
}
