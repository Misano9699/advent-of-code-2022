fun main() {

    // list of stacks
    var stacks: MutableList<MutableList<String>> = MutableList(9) { mutableListOf() }

    // list of moves (number of elements to move, source stack, destination stack)
    var moves: MutableList<List<Int>> = mutableListOf()

    fun reset() {
        // needs to be reset before every run. Easier than using stacks and moves as method parameters
        stacks = MutableList(9) { mutableListOf() }
        moves = mutableListOf()
    }

    fun parseStacks(line: String) {
        val row = MutableList(9) { "" }
        line.indices.forEach {
            if (line[it] in 'A'..'Z') {
                // every character is on 1,5,9 to 33 so a division by 4 should give the correct index in the list
                row.add(it / 4, line[it].toString())
            }
        }
        row.indices.forEach {
            if (row[it] != "") stacks[it].add(row[it])
        }
    }

    fun parseMoves(line: String) {
        val move = line.split(" ")
        // move (1) from (3) to (5)
        moves.add(listOf(move[1].toInt(), move[3].toInt(), move[5].toInt()))
    }

    fun createStacksAndDetermineMoves(input: List<String>) {
        var determineMoves = false
        input.forEach { line ->
            if (line == "") {
                // switch from parsing the stack to parsing the moves
                determineMoves = true
            } else {
                if (determineMoves) {
                    parseMoves(line)
                } else {
                    parseStacks(line)
                }
            }
        }
        // as the rows are build in reverse order, we need to reverse the rows of our stacks before we apply the moves
        stacks.forEach { it.reverse() }
    }

    fun moveOneCrateAtATimeFromSourceToDestination(numberOfCratesToMove: Int, source: Int, destination: Int) {
        (1..numberOfCratesToMove).forEach { _ ->
            val element = stacks[source - 1].removeLast()
            stacks[destination - 1].add(element)
        }
    }

    fun moveCratesAtOnceFromSourceToDestination(numberOfCratesToMove: Int, source: Int, destination: Int) {
        val elements = stacks[source - 1].takeLast(numberOfCratesToMove)
        // takeLast doesn't remove the taken elements, so we need to do that
        (1..numberOfCratesToMove).forEach { _ -> stacks[source - 1].removeLast() }
        stacks[destination - 1].addAll(elements)
    }

    fun retrieveTopElementsFromEachStack(): String = stacks.map {
        if (it.isEmpty()) ""
        else it.last()
    }.joinToString("") { it }

    fun part1(input: List<String>): String {
        reset()
        createStacksAndDetermineMoves(input)
        moves.forEach { moveOneCrateAtATimeFromSourceToDestination(it[0], it[1], it[2]) }
        return retrieveTopElementsFromEachStack()
    }

    fun part2(input: List<String>): String {
        reset()
        createStacksAndDetermineMoves(input)
        moves.forEach { moveCratesAtOnceFromSourceToDestination(it[0], it[1], it[2]) }
        return retrieveTopElementsFromEachStack()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput).also { println("Answer test input part1: $it") } == "CMZ")
    println("Answer part1: " + part1(input))

    check(part2(testInput).also { println("Answer test input part2: $it") } == "MCD")
    println("Answer part2: " + part2(input))
}
