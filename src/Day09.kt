fun main() {
    val filename = "Day09"
    val part1Data = ::part1 to 15
    val part2Data = ::part2 to 1134

    listOf(part1Data, part2Data).forEach { (func, test) ->
        check(func(invokeOnLinesInMemory("${filename}_test", ::prepareInput)) == test)
        println(func(invokeOnLinesInMemory(filename, ::prepareInput)))
    }
}

private fun prepareInput(lines: List<String>): List<List<Int>> {
    return lines.map { line -> line.toCharArray().map { it - '0' } }
}

fun createAdjacents(x: Int, y: Int, xSize: Int, ySize: Int): List<Pair<Int, Int>> {
    return listOf(
        x - 1 to y,
        x + 1 to y,
        x to y - 1,
        x to y + 1,
    ).filter { (xa, ya) -> ya in 0 until ySize && xa in 0 until xSize }
}

fun isLowerThanAllAdjacent(heightmap: List<List<Int>>, x: Int, y: Int): Boolean {
    return createAdjacents(x, y, heightmap.first().size, heightmap.size)
        .none { (xa, ya) -> heightmap[ya][xa] <= heightmap[y][x] }
}

private fun part1(heightmap: List<List<Int>>): Int {
    return heightmap.indices.sumOf { y ->
        heightmap[y].indices.sumOf { x ->
            if (isLowerThanAllAdjacent(heightmap, x, y)) heightmap[y][x] + 1 else 0
        }
    }
}

private fun part2(heightmap: List<List<Int>>): Int {
    val visited = heightmap.map(List<Int>::toMutableList)
    return heightmap.indices
        .flatMap { y ->
            heightmap[y].indices
                .filter { x -> isLowerThanAllAdjacent(heightmap, x, y) }
                .map { x -> x to y }
        }
        .map { (x, y) ->
            val toVisit = mutableListOf(x to y)
            var count = 0
            while (toVisit.isNotEmpty()) {
                val (xCur, yCur) = toVisit.removeFirst()
                if (visited[yCur][xCur] >= 9) continue
                count++
                toVisit.addAll(
                    createAdjacents(xCur, yCur, heightmap.first().size, heightmap.size)
                        .filter { (xA, yA) -> heightmap[yA][xA] in (heightmap[yCur][xCur] + 1) until 9 }
                )
                visited[yCur][xCur] = 9
            }
            count
        }
        .sortedDescending()
        .take(3)
        .reduce { acc, i -> acc * i }
}