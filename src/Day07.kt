const val TOTAL_DISKSPACE = 70000000

const val FREE_DISKSPACE_NEEDED = 30000000

fun main() {

    var root = Dir("/")

    var currentDirectory: Dir = root

    var directoriesWithSizes = mutableListOf<Pair<String, Int>>()

    fun reset() {
        root = Dir("/")
        currentDirectory = root
        directoriesWithSizes = mutableListOf()
    }

    fun changeDir(directory: String) {
        when (directory) {
            "/" -> currentDirectory = root
            ".." -> currentDirectory.parent?.let { dir -> currentDirectory = dir }
            "." -> {} // current directory stays the same
            else -> {
                if (currentDirectory.nodes[directory] != null) {
                    currentDirectory = currentDirectory.nodes[directory] as Dir
                } else {
                    throw IllegalStateException("Directory $directory unknown")
                }
            }
        }
    }

    fun processCommand(command: String) {
        if (command.startsWith("cd ")) changeDir(command.substring(3))
        // ls does nothing except showing files and directories which are processed separately
    }

    fun processDirectory(name: String) {
        if (!currentDirectory.nodes.containsKey(name)) {
            val directory = Dir(name)
            directory.parent = currentDirectory
            currentDirectory.nodes[name] = directory
        } else {
            println("Directory already added")
        }
    }

    fun processFile(line: String) {
        val split = line.split(" ")
        val size = split[0].toInt()
        val name = split[1]
        if (!currentDirectory.nodes.containsKey(split[1])) {
            currentDirectory.nodes[name] = File(name, size)
        } else {
            println("File already added")
        }
    }

    fun createDirectoryStructure(input: List<String>) {
        input.forEach { line ->
            when {
                line.startsWith("$ ") -> processCommand(line.substring(2))
                line.startsWith("dir ") -> processDirectory(line.substring(4))
                else -> processFile(line)
            }
        }
    }

    fun calculateSize(totalSize: Int, dir: Dir): Int {
        dir.totalSize = dir.nodes.values.sumOf {
            when (it) {
                is File -> it.size
                is Dir -> calculateSize(totalSize, it)
                else -> 0 // should not happen
            }
        }
        directoriesWithSizes.add(Pair(dir.name, dir.totalSize))
        return dir.totalSize
    }

    fun createListOfDirectoriesWithSizes(input: List<String>): Int {
        reset()
        createDirectoryStructure(input)
        return calculateSize(0, root)
    }

    fun part1(input: List<String>): Int {
        val totalDiskspaceUsed = createListOfDirectoriesWithSizes(input)
        println("total diskspace used: $totalDiskspaceUsed")
        return directoriesWithSizes.filter { it.second <= 100000 }
            .sumOf { it.second }
    }

    fun part2(input: List<String>): Int {
        val totalDiskspaceUsed = createListOfDirectoriesWithSizes(input)
        println("total diskspace used: $totalDiskspaceUsed")
        val spaceToDelete = FREE_DISKSPACE_NEEDED - (TOTAL_DISKSPACE - totalDiskspaceUsed)
        println("space to delete: $spaceToDelete")
        return directoriesWithSizes.filter { it.second > spaceToDelete }
            .minOf { it.second }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    println("---- PART 1 ----")
    check(part1(testInput).also { println("Answer test input part1: $it") } == 95437)
    println("Answer part1: " + part1(input))

    println("---- PART 2 ----")
    check(part2(testInput).also { println("Answer test input part2: $it") } == 24933642)
    println("Answer part2: " + part2(input))
}

open class Node(val name: String) {
    override fun toString(): String {
        return name
    }
}

class Dir(name: String) : Node(name) {
    var parent: Dir? = null // needed to navigate a directory up
    var nodes: MutableMap<String, Node> = mutableMapOf()
    var totalSize: Int = 0

    override fun toString(): String {
        return super.toString() + ", nodes=[$nodes]"
    }
}

class File(name: String, val size: Int) : Node(name)