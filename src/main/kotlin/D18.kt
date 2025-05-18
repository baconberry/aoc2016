class D18 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val input = lines.first()
        val maze = MineMaze(40, input.length)
        maze.parseFirstRow(input)
        maze.fillMaze()
        return "${maze.countSafe()}"
    }

    class MineMaze(val height: Int, val width: Int) {
        val grid = Array<Array<MazePlace>>(height, {
            Array<MazePlace>(width, {
                MazePlace.UNKNOWN
            })
        })
        val trapValues = listOf<Int>(6, 3, 4, 1)

        fun parseFirstRow(s: String) {
            for (i in 0..<s.length) {
                val c = s[i]
                grid[0][i] = when (c) {
                    '^' -> MazePlace.TRAP
                    '.' -> MazePlace.SAFE
                    else -> MazePlace.UNKNOWN
                }
            }
        }

        fun countSafe(): Int {
            return grid
                .flatMap { it.toList() }
                .count { it == MazePlace.SAFE }
        }

        fun fillMaze() {
            for (y in 1..<grid.size) {
                val row = grid[y]
                for (x in 0..<row.size) {
                    val trapNumber = getTrapNumber(x to y)
                    for (t in trapValues) {
                        if (trapNumber == t) {
                            grid[y][x] = MazePlace.TRAP
                            break
                        }
                    }
                    if (grid[y][x] == MazePlace.UNKNOWN) {
                        grid[y][x] = MazePlace.SAFE
                    }
                }
            }
        }

        fun getTrapNumber(pos: Position): Int {
            val y = pos.second - 1
            var x = pos.first - 1
            var result = 0
            if (x >= 0 && grid[y][x] == MazePlace.TRAP) {
                result += 4
            }
            x++
            if (x >= 0 && x < this.width && grid[y][x] == MazePlace.TRAP) {
                result += 2
            }
            x++
            if (x >= 0 && x < this.width && grid[y][x] == MazePlace.TRAP) {
                result += 1
            }
            return result
        }
    }

    enum class MazePlace {
        SAFE, TRAP, UNKNOWN
    }
}