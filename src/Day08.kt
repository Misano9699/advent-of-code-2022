fun main() {

    fun determineGrid(input: List<String>): List<List<Int>> {
        val grid = mutableListOf<List<Int>>()
        input.forEach { line ->
            grid.add(line.toCharArray().map { it.toString().toInt() }.toList())
        }
        return grid
    }

    fun addToVisibleTrees(visibleTrees: MutableMap<Pair<Int, Int>, Int>, pair: Pair<Int, Int>, size: Int) {
        if (!visibleTrees.containsKey(pair)) {
            visibleTrees[pair] = size
        }
    }

    fun determineVisibleTrees(grid: List<List<Int>>): Map<Pair<Int, Int>, Int> {
        val visibleTrees =mutableMapOf<Pair<Int, Int>, Int>()
        (grid.indices).forEach { x ->
            var previousSize = -1
            (grid[x].indices).forEach { y ->
                val size = grid[x][y]
                if (size > previousSize ) {
                    previousSize = size
                    addToVisibleTrees(visibleTrees, Pair(x, y), size)
                }
            }
        }
        (grid[0].indices).forEach { y ->
            var previousSize = -1
            (grid.indices).forEach { x ->
                val size = grid[x][y]
                if (size > previousSize ) {
                    previousSize = size
                    addToVisibleTrees(visibleTrees, Pair(x, y), size)
                }
            }
        }
        (grid.indices).reversed().forEach { x ->
            var previousSize = -1
            (grid[x].indices).reversed().forEach { y ->
                val size = grid[x][y]
                if (size > previousSize ) {
                    previousSize = size
                    addToVisibleTrees(visibleTrees, Pair(x, y), size)
                }
            }
        }
        (grid[0].indices).reversed().forEach { y ->
            var previousSize = -1
            (grid.indices).reversed().forEach { x ->
                val size = grid[x][y]
                if (size > previousSize ) {
                    previousSize = size
                    addToVisibleTrees(visibleTrees, Pair(x, y), size)
                }
            }
        }
        return visibleTrees
    }

    fun left(x: Int, y: Int, grid: List<List<Int>>): Int {
        val size = grid[x][y]
        var i = x - 1
        var count = 0
        var stop = false
        while (i >= 0 && !stop) {
            count++
            if (grid[i][y] >= size) stop = true
            i--
        }
        return count
    }

    fun right(x: Int, y: Int, grid: List<List<Int>>): Int {
        val size = grid[x][y]
        var i = x + 1
        var count = 0
        var stop = false
        while (i < grid.size && !stop)  {
            count++
            if (grid[i][y] >= size) stop = true
            i++
        }
        return count
    }
    fun up(x: Int, y: Int, grid: List<List<Int>>): Int {
        val size = grid[x][y]
        var j = y - 1
        var count = 0
        var stop = false
        while (j >= 0 && !stop) {
            count++
            if (grid[x][j] >= size ) stop = true
            j--
        }
        return count
    }
    fun down(x: Int, y: Int, grid: List<List<Int>>): Int {
        val size = grid[x][y]
        var j = y + 1
        var count = 0
        var stop = false
        while (j <  grid[x].size && !stop) {
            count++
            if (grid[x][j] >= size) stop = true
            j++
        }
        return count
    }

    fun determineScenicTrees(grid: List<List<Int>>): Map<Pair<Int, Int>, Int> {
        val scenicTrees = mutableMapOf<Pair<Int, Int>, Int>()
        (grid.indices).forEach { x ->
            (grid[x].indices).forEach { y ->
                val treesLeft = left(x, y, grid)
                val treesRight = right(x, y, grid)
                val treesUp = up(x, y, grid)
                val treesDown = down(x, y, grid)
                scenicTrees[Pair(x,y)]=treesLeft * treesRight * treesUp * treesDown
            }
        }
        return scenicTrees
    }

    fun part1(input: List<String>): Int {
        val grid: List<List<Int>> = determineGrid(input)
        println(grid)
        val visibleTrees = determineVisibleTrees(grid)
        println(visibleTrees)
        return visibleTrees.count()
    }

    fun part2(input: List<String>): Int {
        val grid: List<List<Int>> = determineGrid(input)
        println(grid)
        val scenicTrees = determineScenicTrees(grid)
        println(scenicTrees)
        return scenicTrees.maxOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 21)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 8)
    println("Answer part2: " + part2(input))
}
