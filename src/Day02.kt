fun main() {
    fun part1(input: List<String>): Int {
        val submarine = Submarine()

        val operations = transformInput(input)

        operations.map { (direction, amount) ->
            when (direction) {
                "forward" -> {
                    submarine.forward(amount)
                }
                "up" -> {
                    submarine.up(amount)
                }
                "down" -> {
                    submarine.down(amount)
                }
            }
        }

        return submarine.depth * submarine.horizontalPosition
    }

    fun part2(input: List<String>): Int {
        val submarine = Submarine()

        val operations = transformInput(input)

        operations.map { (direction, amount) ->
            when (direction) {
                "forward" -> {
                    submarine.aimForward(amount)
                }
                "up" -> {
                    submarine.aimUp(amount)
                }
                "down" -> {
                    submarine.aimDown(amount)
                }
            }
        }

        return submarine.depth * submarine.horizontalPosition
    }

    val testInput = readInputAsListOfString("Day02_test")
    check(part2(testInput) == 900)

    val input = readInputAsListOfString("Day02")
    println(part1(input))
    println(part2(input))
}

class Submarine(
    var depth: Int = 0,
    var horizontalPosition: Int = 0,
    var aim: Int = 0,
) {
    fun forward(amount: Int) {
        horizontalPosition += amount
    }

    fun up(amount: Int) {
        depth -= amount
    }

    fun down(amount: Int) {
        depth += amount
    }

    fun aimForward(amount: Int) {
        horizontalPosition += amount
        depth += aim * amount
    }

    fun aimUp(amount: Int) {
        aim -= amount
    }

    fun aimDown(amount: Int) {
        aim += amount
    }
}

data class Operation(
    val direction: String,
    val amount: Int,
)

fun transformInput(input: List<String>): List<Operation> =
    input.flatMap { it.split(" ").zipWithNext { a, b -> Operation(a, b.toInt()) } }
