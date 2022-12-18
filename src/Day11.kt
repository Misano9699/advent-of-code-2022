var monkeys: MutableList<Monkey> = mutableListOf()
var reliefModifier = 0L
var keepInCheck = 1L

fun main() {

    fun reset() {
        monkeys = mutableListOf()
        keepInCheck = 1L
    }

    fun listOfItems(line: String): MutableList<Long> = line.replace(" ", "").split(",").map { it.toLong() }.toMutableList()

    fun parseInput(input: List<String>) {
        var monkey = Monkey(0)
        input.forEach { line ->
            when {
                line.startsWith("Monkey ") -> monkey = Monkey(line.split(" ")[1].replace(":", "").toInt())
                line.startsWith("  Starting items:") -> monkey.items = listOfItems(line.substring(18))
                line.startsWith("  Operation: new = old ") -> {
                    monkey.operation = line.substring(23).split(" ")[0]
                    monkey.operand = line.substring(23).split(" ")[1]
                }
                line.startsWith("  Test: divisible by ") -> monkey.test = line.substring(21).toInt()
                line.startsWith("    If true: throw to monkey ") -> monkey.toMonkey.add(line.substring(29).toInt())
                line.startsWith("    If false: throw to monkey ") -> monkey.toMonkey.add(line.substring(30).toInt())
                line.startsWith("") -> monkeys.add(monkey)
            }
        }
        monkeys.add(monkey)
    }

    fun play() {
        monkeys.forEach {
            println("Monkey ${it.number}")
            it.play()
        }
    }

    // To keep the worry level within range, we need to find a way to let all the tests still work.
    // Reducing the worry level to the modulo of all test numbers multiplied should do that,
    // without disrupting the test
    fun determineKeepInCheckLevels() {
        monkeys.forEach {
            keepInCheck *= it.test
        }
    }

    fun part1(input: List<String>): Long {
        reliefModifier = 3L
        reset()
        parseInput(input)
        determineKeepInCheckLevels()
        (1..20).forEach {
            println("Round $it")
            play()
        }
        val maxActive = monkeys.map { it.active }.sortedDescending().take(2)
        return maxActive[0] * maxActive[1]
    }

    fun part2(input: List<String>): Long {
        reliefModifier = 1L // no more relieved after inspection
        reset()
        parseInput(input)
        determineKeepInCheckLevels()
        println("Keep in check $keepInCheck")
        (1..10000).forEach {
            println("Round $it")
            play()
        }
        val maxActive = monkeys.sortedByDescending {  it.active }.take(2)
        println("Most active monkeys: ${maxActive[0].active}, ${maxActive[1].active}")
        return maxActive[0].active * maxActive[1].active
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 10605L)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 2713310158)
    println("Answer part2: " + part2(input))
}


class Monkey(val number: Int) {
    var items = mutableListOf<Long>()
    var operation: String = ""
    var operand: String = ""
    var test: Int = 0
    var toMonkey = mutableListOf<Int>()
    var active = 0L

    fun play() {
        (0 until items.size).forEach { _ ->
            active++
            // take item
            var worryLevel = items.removeFirst()
            // inspect
            worryLevel = operate(worryLevel, operation, when(operand) {
                "old" -> worryLevel
                else -> operand.toLong()
            })
            worryLevel /= reliefModifier
            worryLevel %= keepInCheck
            // test worry level and throw
            when (worryLevel % test == 0L) {
                true ->  monkeys[toMonkey[0]].items.add(worryLevel)
                false -> monkeys[toMonkey[1]].items.add(worryLevel)
            }
        }
    }

    private fun operate(worrylevel: Long, operation: String, operand: Long) = when (operation) {
        "+" -> worrylevel + operand
        "-" -> worrylevel - operand
        "/" -> worrylevel / operand
        "*" -> worrylevel * operand
        else -> worrylevel
    }
}
