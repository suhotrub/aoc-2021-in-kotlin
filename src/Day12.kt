private typealias PreparedData = HashMap<String, List<String>>

fun main() {
    evaluateDay(
        dayNumber = 12,
        prepareInput = ::prepareInput,
        part1Data = ::part1 to 10L,
        part2Data = ::part2 to 36L
    )
}

private fun prepareInput(lines: List<String>): PreparedData {
    return PreparedData().apply {
        lines.forEach {
            val dests = it.split('-')
            if (dests[1] != "start") this[dests[0]] = this[dests[0]]?.plus(dests[1]) ?: listOf(dests[1])
            if (dests[0] != "start") this[dests[1]] = this[dests[1]]?.plus(dests[0]) ?: listOf(dests[0])
        }
        this.remove("end")
    }
}

private fun dfs(
    data: PreparedData,
    visited: List<String>,
    current: String,
    singleCaveAvailable: Boolean,
    revisited: Boolean = false
): Long {
    if (current == "end") return 1
    val availablePaths = data[current].orEmpty().filter { dest ->
        dest.all { it.isUpperCase() }
                || dest !in visited
                || (singleCaveAvailable && !revisited)
    }
    return if (availablePaths.isEmpty()) {
        0L
    } else {
        availablePaths.sumOf {
            dfs(
                data,
                visited + current,
                it,
                singleCaveAvailable,
                revisited || it.all { it.isLowerCase() } && it in visited
            )
        }
    }
}

private fun part1(data: PreparedData): Long {
    return dfs(data, listOf(), "start", false)
}

private fun part2(data: PreparedData): Long {
    return dfs(data, listOf(), "start", true)
}