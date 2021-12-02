fun main() {
    fun part1(input: List<Int>): Int {
        return input.zipWithNext().count { (a, b) -> b > a }
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3).zipWithNext().count { (a, b) ->
            a.sum() < b.sum()
        }
    }

    val testInput = readInputToInts("Day01_test")
    check(part2(testInput) == 5)

    val input = readInputToInts("Day01")
    println(part1(input))
    println(part2(input))
}
