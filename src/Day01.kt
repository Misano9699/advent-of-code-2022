fun main() {

    fun listOfCalories(listOfElves: List<List<String>>): List<Int> = listOfElves.map { elf ->
        elf.sumOf { it.toInt() }
    }

    fun part1(input: List<String>): Int {
        return listOfCalories(input.splitIntoSublists("")).maxOf { it }
    }

    fun part2(input: List<String>): Int {
        return listOfCalories(input.splitIntoSublists("")).sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    check(part1(testInput).also { println("Answer test input part1: $it") } == 24000)
    println("Answer part1: " + part1(input))

    check(part2(testInput).also { println("Answer test input part2: $it") } == 45000)
    println("Answer part2: " + part2(input))
}
