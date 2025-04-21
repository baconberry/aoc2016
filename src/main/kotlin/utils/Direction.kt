package utils

enum class Direction {
    N, S, E, W, NONE;

    fun changeTo(a: Direction): Direction {
        if (this == NONE || this == N) {
            return a
        }
        if (this == S) {
            return when (a) {
                N -> S
                S -> N
                E -> W
                W -> E
                NONE -> S
            }
        }

        if (this == E) {
            return when (a) {
                N -> E
                S -> W
                E -> S
                W -> N
                NONE -> E
            }
        }

        if (this == W) {
            return when (a) {
                N -> W
                S -> E
                E -> N
                W -> S
                NONE -> W
            }
        }
        throw IllegalArgumentException()
    }

    fun diff(): Pair<Int, Int> {
        return when (this) {
            NONE -> 0 to 0
            N -> 0 to 1
            S -> 0 to -1
            E -> 1 to 0
            W -> -1 to 0
        }
    }

    fun plus(currentPoint: Pair<Int, Int>, scale: Int): Pair<Int, Int> {
        var (dx, dy) = this.diff()
        dx *= scale
        dy *= scale
        return currentPoint.first + dx to currentPoint.second + dy
    }
}

fun parseDirection(c: Char): Direction {
    return when (c) {
        'R' -> Direction.E
        'L' -> Direction.W
        'U' -> Direction.N
        'D' -> Direction.S
        else -> Direction.NONE
    }
}
