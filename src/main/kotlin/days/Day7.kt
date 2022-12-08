package days

import xyz.hughjd.aocutils.Collections.indicesOf

class Day7 : Day(7) {

    private val commandIndices = inputList.indicesOf { it.startsWith("$") }

    private val commands = commandIndices.plus(inputList.size).windowed(2).map { inputList.subList(it[0], it[1]) }

    private val root = getFilesystem().findRoot()

    override fun partOne(): Any {
        return root.findAllDirectories().filter { it.totalSize() <= 100000 }.sumOf { it.totalSize() }
    }

    override fun partTwo(): Any {
        val totalSpace = 70000000
        val neededSpace = 30000000
        val unusedSpace = totalSpace - root.totalSize()
        val spaceToFree = neededSpace - unusedSpace
        return root.findAllDirectories().filter { it.totalSize() > spaceToFree }.minBy { it.totalSize() }.totalSize()
    }

    private fun getFilesystem(): Directory {
        return commands.drop(1).fold(Directory("/", null)) { cwd, command ->
            val (cmd, output) = command[0].replace("$ ", "") to command.drop(1)
            if (cmd.startsWith("cd")) {
                val name = cmd.replace("cd ", "")
                if (name == "..") cwd.parent!! else cwd.findDirectory(name) ?: Directory(name, cwd)
            }
            else {
                val children = output.map {
                    if (it.startsWith("dir")) Directory(it.replace("dir ", ""), cwd) else {
                        val (size, name) = Regex("(\\d+) (.+)").matchEntire(it)!!.destructured;
                        File(name, size.toInt(), cwd)
                    }
                }
                cwd.children.addAll(children)
                cwd
            }
        }
    }

    abstract class FilesystemObject(val name: String, val size: Int, val parent: Directory?, val children: MutableList<FilesystemObject> = mutableListOf()) {
        override fun toString(): String {
            return "FilesystemObject(name='$name', size=$size, parent=$parent)"
        }
    }

    class File(name: String, size: Int, parent: Directory) : FilesystemObject(name, size, parent)

    class Directory(name: String, parent: Directory?) : FilesystemObject(name, 0, parent) {

        fun totalSize(): Int {
            return if (children.isEmpty()) 0 else children.sumOf { child ->
                if (child is File) child.size else (child as Directory).totalSize()
            }
        }

        fun findDirectory(name: String): Directory? {
            return if (children.isEmpty()) null else {
                val childDirs = children.filterIsInstance<Directory>()
                childDirs.find { it.name == name } ?: childDirs.map { it.findDirectory(name) }.find { it != null }
            }
        }

        fun findRoot(): Directory {
            return parent?.findRoot() ?: this
        }

        fun findAllDirectories(dirs: Set<Directory> = emptySet()): Set<Directory> {
            return if (children.isEmpty()) emptySet() else {
                val childDirs = children.filterIsInstance<Directory>()
                dirs.plus(this).plus(childDirs).plus(childDirs.flatMap { it.findAllDirectories() })
            }
        }
    }
}
