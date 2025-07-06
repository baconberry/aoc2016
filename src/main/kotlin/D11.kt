import java.util.*

typealias Generator = String
typealias DMicrochip = String

class D11 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {

        var f1 = Floor(listOf("T", "P", "S"), listOf("T"))
        val f2 = Floor(listOf(), listOf("P", "S"))
        val f3 = Floor(listOf("PR", "R"), listOf("PR", "R"))
        val f4 = Floor()
        if (part == 2u) {
            f1 = Floor(listOf("T", "P", "S", "E", "D"), listOf("T", "E", "D"))
        }
        val building = Building(0, listOf(f1, f2, f3, f4))
        val result = bfs(building)
        return "$result"
    }

    fun bfs(starter: Building): Int {
        val q = PriorityQueue<Pair<Building, List<Pair<Floor, Int>>>>(Comparator.comparing { it.second.size })
        val visited = mutableListOf<String>()
        q.add(starter to listOf())
        visited.add(starter.floorHash())
        var depth = -1
        while (q.isNotEmpty()) {
            val building = q.remove()
            if (building.first.isAllInLastFloor()) {
                return building.second.size
            }
            if (building.second.size != depth) {
                println("exploring depth [$depth]")
                depth = building.second.size
            }


            val options = building.first.moveOptions()
            for (opt in options) {
                val buildingWithoutOption = building.first.removeOption(opt)
                if (buildingWithoutOption == null) {
                    continue
                }
                val moveUp = buildingWithoutOption.moveIn(buildingWithoutOption.elevatorAt + 1, opt)
                val moveDown = buildingWithoutOption.moveIn(buildingWithoutOption.elevatorAt - 1, opt)
                for (move in listOf(moveUp, moveDown)) {
                    if (move != null && move.floorHash() !in visited) {
                        q.add(move to (building.second + (opt to move.elevatorAt)))
                        visited.add(move.floorHash())
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    data class Floor(val generators: List<Generator> = listOf(), val microchips: List<DMicrochip> = listOf()) {
        fun isValid(): Boolean {
            return generators.isEmpty() || microchips.isEmpty() || generators.containsAll(microchips)
        }

        fun isEmpty(): Boolean {
            return generators.isEmpty() && microchips.isEmpty()
        }
    }

    data class Building(val elevatorAt: Int = 0, val floors: List<Floor>) {

        fun moveOptions(): List<Floor> {
            val floor = floors[elevatorAt]
            val result = mutableListOf<Floor>()
            floor.generators.forEach { result.add(Floor(listOf(it))) }
            floor.microchips.forEach { result.add(Floor(listOf(), listOf(it))) }

            for (i in 0..<floor.generators.size) {
                val gen = floor.generators[i]
                for (j in i + 1..<floor.generators.size) {
                    result.add(Floor(listOf(gen, floor.generators[j])))
                }
                floor.microchips
                    .filter { it == gen }
                    .forEach { result.add(Floor(listOf(gen), listOf(it))) }
            }
            for (i in 0..<floor.microchips.size) {
                val micro = floor.microchips[i]
                for (j in i + 1..<floor.microchips.size) {
                    result.add(Floor(listOf(), listOf(micro, floor.microchips[j])))
                }
            }

            return result
                .filter { it.isValid() }
                .sortedBy { -(it.generators.size + it.microchips.size) }
                .toList()

        }

        fun removeOption(opt: Floor): Building? {
            val floor = floors[elevatorAt]
            val gens = floor.generators.toMutableList()
            val micros = floor.microchips.toMutableList()
            gens.removeAll(opt.generators)
            micros.removeAll(opt.microchips)
            gens.sort()
            micros.sort()
            val newFloors = floors.toMutableList()
            newFloors[elevatorAt] = Floor(gens, micros)
            if (!newFloors[elevatorAt].isValid()) {
                return null
            }
            return Building(elevatorAt, newFloors)
        }

        fun isAllInLastFloor(): Boolean {
            return !floors.last().isEmpty() && floors
                .take(floors.size - 1)
                .all { it.isEmpty() }
        }

        fun moveIn(floorAt: Int, opt: D11.Floor): Building? {
            if (floorAt < 0 || floorAt >= floors.size) {
                return null
            }
            val floor = floors[floorAt]
            val gens = floor.generators.toMutableList()
            val micros = floor.microchips.toMutableList()
            gens.addAll(opt.generators)
            micros.addAll(opt.microchips)
            gens.sort()
            micros.sort()
            val newFloor = Floor(gens, micros)
            if (!newFloor.isValid()) {
                return null
            }
            val newFloors = floors.toMutableList()
            newFloors[floorAt] = newFloor

            return Building(floorAt, newFloors)
        }

        fun floorHash(): String {
            val sb = StringBuilder()
            sb.append(elevatorAt)
            sb.append("@")
            for (floor in floors) {
                sb.append(floor.generators.size)
                sb.append("+")
                sb.append(floor.microchips.size)
                sb.append(";")
            }

            return sb.toString()
        }
    }
}
