fun main() {

    fun splitInTwo(rucksack: String): List<Set<Char>> =
        listOf(rucksack.take(rucksack.length / 2).toSet(), rucksack.takeLast(rucksack.length / 2).toSet())

    fun priority(item: Char): Int =
        when {
            item.isLowerCase() -> item.code - 96
            else -> item.code - 38
        }

    fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val compartments = splitInTwo(rucksack)
            priority(compartments[0].toSet().intersect(compartments[1].toSet()).first())
        }
    }

    fun part2(input: List<String>): Int {
        val rucksacks = input.splitIntoNumberOfLists(3)
        println(rucksacks)
        return rucksacks.sumOf {
            priority(it[0].toSet().intersect((it[1].toSet().intersect(it[2].toSet()))).first())
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    check(part1(testInput).also { println("Answer test input part1: $it") } == 157)
    println("Answer part1: " + part1(input))

    check(part2(testInput).also { println("Answer test input part2: $it") } == 70)
    println("Answer part2: " + part2(input))
}
