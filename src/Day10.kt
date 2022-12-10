fun main() {

    val checkpoint = listOf(20, 60, 100, 140, 180, 220)

    var cycle = 0
    var regx = 1
    var total = 0
    var screen = List(6) { MutableList(40) { "." } }

    fun reset() {
        cycle = 0
        regx = 1
        total = 0
        screen = List(6) { MutableList(40) { "." } }
    }

    fun drawPixel() {
        val x = (cycle - 1) % 40
        val y = (cycle - 1) / 40
        if (regx - 1 <= x && x <= regx + 1) {
            screen[y][x] = "#"
        }
    }

    fun checkCycle() {
        if (checkpoint.contains(cycle)) {
            total += cycle * regx
        }
        drawPixel()
    }

    fun cycle(numberOfCycles: Int) {
        (1..numberOfCycles).forEach {_ ->
            cycle++
            checkCycle()
        }
    }

    fun operate(operator: String) {
        if (operator.startsWith("noop")) {
            cycle(1)
        } else { // addx
            cycle(2)
            regx += operator.split(" ")[1].toInt()
        }
    }

    fun part1(input: List<String>): Int {
        reset()
        input.forEach {
            operate(it)
        }
        return total
    }

    fun part2(input: List<String>): Int {
        reset()
        input.forEach {
            operate(it)
        }
        // visually compare output
        println(screen[0].joinToString(""))
        println(screen[1].joinToString(""))
        println(screen[2].joinToString(""))
        println(screen[3].joinToString(""))
        println(screen[4].joinToString(""))
        println(screen[5].joinToString(""))
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 13140)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    // no check on grid, just visually check the output for both the test and real data
    check(part2(testInput).also { println("Answer test input part2: $it") } == 146)
    println("Answer part2: " + part2(input))
}