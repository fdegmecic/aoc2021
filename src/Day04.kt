fun main() {

    fun buildBoards(rawBoards: List<String>): List<BingoBoard> {
        val splitBoards = rawBoards.windowed(5, 5)

        return splitBoards.map { board ->
            BingoBoard(board.map { row ->
                row.split(" ").filter { it.isNotBlank() }.map { BingoField(it.toInt()) }
            })
        }
    }

    fun prepareData(input: List<String>): Pair<List<BingoBoard>, List<Int>> {
        val drawnNumbers = input.first().split(',').map { it.toInt() }

        val boards = buildBoards(input.drop(1).filter { it.isNotBlank() })

        return Pair(boards, drawnNumbers)
    }

    fun firstWinRemainingNumbersSum(input: List<String>): Int {
        val (boards, drawnNumbers) = prepareData(input)

        var winningBoard: BingoBoard? = null
        var winningNumber = 0

        for (number in drawnNumbers) {
            if (winningBoard != null) {
                break
            }

            boards.map { board ->
                val winnerCheck = board.markField(drawnNumber = number)
                if (winnerCheck) {
                    winningBoard = board
                    winningNumber = number
                    return@map
                }
            }
        }

        return winningNumber * winningBoard!!.getRemainingNumbersSum()
    }

    fun lastWinRemainingNumbersSum(input: List<String>): Int {
        val (boards, drawnNumbers) = prepareData(input)

        var winningBoard: BingoBoard? = null
        var winningNumber = 0

        for (number in drawnNumbers) {
            for (board in boards) {
                if (board.checkIfWon()) {
                    continue
                }

                val winnerCheck = board.markField(drawnNumber = number)

                if (winnerCheck) {
                    winningBoard = board
                    winningNumber = number
                }
            }
        }

        return winningNumber * winningBoard!!.getRemainingNumbersSum()
    }

    val testInput = readInputAsListOfString("Day04_test")
    check(lastWinRemainingNumbersSum(testInput) == 1924)

    val input = readInputAsListOfString("Day04")
    println(firstWinRemainingNumbersSum(input))
    println(lastWinRemainingNumbersSum(input))
}

class BingoBoard(private val board: List<List<BingoField>>) {
    private val transposedBoard: List<List<BingoField>> = board.transpose()

    fun markField(drawnNumber: Int): Boolean {
        board.forEach { row ->
            row.map { field ->
                if (field.number == drawnNumber) {
                    field.markField()
                }
            }
        }

        return checkIfWon()
    }

    fun getRemainingNumbersSum(): Int {
        return this.board.map { row ->
            row.filter { !it.isMarked }.map { it.number }
        }.sumOf { row -> row.sum() }
    }

    fun checkIfWon() = board.map { row -> row.all { it.isMarked } }.any { it } ||
            transposedBoard.map { row -> row.all { it.isMarked } }.any { it }
}

data class BingoField(val number: Int, var isMarked: Boolean = false) {
    fun markField() {
        isMarked = true
    }
}

/**
 * Thanks to https://stackoverflow.com/a/66401340
 */
fun <T> List<List<T>>.transpose(): List<List<T>> {
    val result: MutableList<MutableList<T>> = (this.first().indices).map { mutableListOf<T>() }.toMutableList()
    this.forEach { columns -> result.zip(columns).forEach { (rows, cell) -> rows.add(cell) } }
    return result
}
