abstract class Evaluator<T>(
    private val part1Test: Long,
    private val part2Test: Long
) {

    fun evalutate() {
        evaluateDay(
            dayNumber = this::class.java.simpleName.takeLastWhile { it.isDigit() }.toInt(),
            prepareInput = ::prepareInput,
            part1Data = ::part1 to part1Test,
            part2Data = ::part2 to part2Test
        )
    }

    abstract fun prepareInput(lines: List<String>): T

    abstract fun part1(data: T): Long

    abstract fun part2(data: T): Long
}