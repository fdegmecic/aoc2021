import kotlin.math.absoluteValue

fun main() {
    fun Set<Int>.getCrabRange(): IntRange =
        this.minOf { it }..this.maxOf { it }

    fun getFuelConsumption(input: String, fuelCost: (Int) -> Int): Int {
        val positions = input.split(',').map { it.toInt() }

        val crabSubmarines: Map<Int, Int> = positions.groupingBy { it }.eachCount()

        val crabRange = crabSubmarines.keys.getCrabRange()

        return crabRange.minOf { horizontalPosition ->
            crabSubmarines.map { (crabPosition, crab) ->
                fuelCost((horizontalPosition - crabPosition).absoluteValue) * crab
            }.sum()
        }
    }

    val testInput = readInputAsString("Day07_test")
    check(getFuelConsumption(testInput) { distance -> (1..distance).sum() } == 168)

    val input = readInputAsString("Day07")
    println(getFuelConsumption(input) { it })
    println(getFuelConsumption(input) { distance -> (1..distance).sum() })
}
