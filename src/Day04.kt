fun main() {

    fun prepareInput(lines: Sequence<String>): Pair<List<Int>, List<Board>> {
        val iterator = lines.iterator()
        val numbers = iterator.next().split(',').map(String::toInt)

        var board = Board()
        var row = 0
        val boards = mutableListOf<Board>()
        iterator.forEachRemaining {
            if (it.isNotEmpty()) {
                it.split(' ').filter(String::isNotBlank).map(String::toInt).forEachIndexed { index, num ->
                    board.addNumber(num, row, index)
                }
                if (++row == 5) {
                    boards.add(board)
                    board = Board()
                    row = 0
                }
            }
        }
        return numbers to boards
    }

    fun part1(lines: Sequence<String>): Int {
        val (numbers, boards) = prepareInput(lines)
        numbers.forEach { number ->
            boards.forEach { board ->
                if (board.markNumber(number)) {
                    return board.calculateScore() * number
                }
            }
        }
        return 0
    }

    fun part2(lines: Sequence<String>): Int {
        val (numbers, boards) = prepareInput(lines)
        val mutableBoards = boards.toMutableList()
        var prevScore = 0
        numbers.forEach { number ->
            mutableBoards.removeAll { board ->
                board.markNumber(number).also { prevScore = board.calculateScore() * number }
            }
        }
        return prevScore
    }

    listOf(::part1 to 4512, ::part2 to 1924).forEach { (func, test) ->
        check(invokeOnLines("Day04_test", func) == test)
        println(invokeOnLines("Day04", func))
    }
}

class Board {
    private val lookup = hashMapOf<Int, Pair<Int, Int>>()
    private val rows = IntArray(5) { 5 }
    private val columns = IntArray(5) { 5 }

    fun addNumber(number: Int, row: Int, column: Int) {
        lookup[number] = row to column
    }

    fun markNumber(number: Int): Boolean {
        val (row, column) = lookup.remove(number) ?: return false
        return --rows[row] == 0 || --columns[column] == 0
    }

    fun calculateScore(): Int = lookup.keys.sum()
}