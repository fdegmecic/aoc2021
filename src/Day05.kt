import kotlin.math.absoluteValue

fun main() {

    fun getPoints(input: List<String>): List<Pair<Point, Point>> =
        input.map { it.split(" -> ") }
            .map { (a, b) ->
                val pointsA = a.split(',')
                val pointsB = b.split(',')

                val pointA = Point(pointsA[0].toInt(), pointsA[1].toInt())
                val pointB = Point(pointsB[0].toInt(), pointsB[1].toInt())

                Pair(pointA, pointB)
            }

    fun getConvergenceOccurrence(input: List<String>, lineFilter: (Pair<Point, Point>) -> Boolean): Int {
        val points = getPoints(input)

        return points.filter { lineFilter(it) }
            .flatMap { (pointA, pointB) -> pointA lineTo pointB }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }
    }

    val testInput = readInputAsListOfString("Day05_test")
    check(getConvergenceOccurrence(testInput) { true } == 12)

    val input = readInputAsListOfString("Day05")
    println(getConvergenceOccurrence(input) { it.first sharesAxisWith it.second })
    println(getConvergenceOccurrence(input) { true })
}

data class Point(
    val x: Int,
    val y: Int,
) {
    infix fun sharesAxisWith(that: Point): Boolean =
        x == that.x || y == that.y

    infix fun lineTo(that: Point): List<Point> {
        val xDelta = lineWalkStep(x, that.x)
        val yDelta = lineWalkStep(y, that.y)

        val steps = maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)

        return (1..steps).scan(this) { lastPoint, _ -> Point(lastPoint.x + xDelta, lastPoint.y + yDelta) }
    }

    private fun lineWalkStep(a: Int, b: Int): Int =
        when {
            a > b -> -1
            a < b -> 1
            else -> 0
        }
}
