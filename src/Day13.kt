class Day13 : Evaluator<Pair<List<Dot>, List<Fold>>>(part1Test = 17L, part2Test = 16L) {

    override fun prepareInput(lines: List<String>): Pair<List<Dot>, List<Fold>> {
        val dots = lines.takeWhile { it.isNotEmpty() }
            .map {
                val split = it.split(',')
                Dot(split[0].toInt(), split[1].toInt())
            }
        val folds = lines.takeLastWhile { it.contains("fold") }
            .map { Fold(it.contains('x'), it.takeLastWhile { fold -> fold.isDigit() }.toInt()) }

        return dots to folds
    }

    private fun fold(dots: List<Dot>, folds: List<Fold>): HashSet<Dot> {
        val transformation = folds.fold<Fold, Transformation?>(null) { acc, fold -> Transformation(acc, fold) }!!
        return dots.map { transformation(it) }.toHashSet()
    }

    override fun part1(data: Pair<List<Dot>, List<Fold>>): Long {
        return fold(data.first, data.second.subList(0, 1)).size.toLong()
    }

    override fun part2(data: Pair<List<Dot>, List<Fold>>): Long {
        val set = fold(data.first, data.second)
        val maxX = set.maxOf { it.x } + 1
        val maxY = set.maxOf { it.y } + 1
        val kek = Array(maxY) { y -> BooleanArray(maxX) { x -> Dot(x, y) in set } }
        println(kek.joinToString(separator = "\n") { it.joinToString(separator = "") { dot -> if (dot) "#" else "." } })
        return set.size.toLong()
    }
}

class Transformation(private val prev: Transformation?, private val fold: Fold) {
    operator fun invoke(dot: Dot): Dot {
        return prev?.let { fold(it(dot)) } ?: fold(dot)
    }

    private fun fold(dot: Dot): Dot {
        val pos = if (fold.alongX) dot.x else dot.y
        return if (pos > fold.coord) {
            val newPos = fold.coord - (pos - fold.coord)
            if (fold.alongX) {
                dot.copy(x = newPos)
            } else {
                dot.copy(y = newPos)
            }
        } else {
            dot
        }
    }
}

data class Dot(val x: Int, val y: Int)

class Fold(val alongX: Boolean, val coord: Int)

fun main() {
    Day13().evalutate()
}