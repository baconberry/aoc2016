import java.util.PriorityQueue
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.plus
import kotlin.concurrent.atomics.AtomicInt

typealias Generator = String
typealias DMicrochip = String

class D11 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {

        val f1 = D11.Floor(listOf("T", "P", "S"), listOf("T"))
        val f2 = D11.Floor(listOf(), listOf("P", "S"))
        val f3 = D11.Floor(listOf("PR", "R"), listOf("PR", "R"))
        val f4 = D11.Floor()
        val building = D11.Building(0, listOf(f1, f2, f3, f4))
        val result = dfs(building)
        return "$result"
    }

    fun dfs(
        building: Building,
        steps: Int = 0,
        nfloors: Int = building.floors.size,
        visited: MutableMap<String, Int> = mutableMapOf(),
        tempMax: AtomicInteger = AtomicInteger(10_000)
    ): Int {
        if (building.isAllInLastFloor()) {
            return steps
        }
        if (steps >= tempMax.get()) {
            return Int.MAX_VALUE
        }
        var min = Int.MAX_VALUE
        val options = building.moveOptions()
        val newSteps = steps + 1
        for (opt in options) {
            val buildingWithoutOption = building.removeOption(opt)
//            if (buildingWithoutOption == null || (buildingWithoutOption in visited && visited[buildingWithoutOption]!! > newSteps)) {
            if (buildingWithoutOption == null ){
                continue
            }
//            visited[buildingWithoutOption] = newSteps
            for (i in 0..<nfloors) {
                val floorAt = nfloors - 1 - i
                if (building.elevatorAt == floorAt ||
                    (floorAt < building.elevatorAt && building.floors[floorAt].isEmpty())) {
                    continue
                }
                val moveIn = buildingWithoutOption.moveIn(floorAt, opt)
                if (moveIn != null ) {
                    val moveInHash = moveIn.floorHash()
                    if(moveInHash in visited && visited[moveInHash]!! < newSteps){
                        continue
                    }
//                if (moveIn != null) {
                    visited[moveInHash] = newSteps
                    val localResult = dfs(moveIn, newSteps, nfloors, visited, tempMax)
                    if (localResult < min) {
                        min = localResult
                        if (min < tempMax.get()) {
                        println("Found solution at $localResult steps")
                            tempMax.set(min)
                        }
                    }
                }
            }
        }

        return min
    }

    fun bfs(starter: Building): Int {
        val q = PriorityQueue<Pair<Building, List<Pair<Floor, Int>>>>(Comparator.comparing { it.second.size })
        val visited = mutableListOf<Building>()
        q.add(starter to listOf())
        visited.add(starter)
        val nfloors = starter.floors.size
        var depth = -1
        while (q.isNotEmpty()) {
            val building = q.remove()
            if (building.first.isAllInLastFloor()) {
                println("Found solution ${building.second}")
                return building.second.size
            }
            if (building.second.size != depth) {
                depth = building.second.size
                println("Exploring depth [$depth]")
            }


            val options = building.first.moveOptions()
            for (opt in options) {
                val buildingWithoutOption = building.first.removeOption(opt)
                if (buildingWithoutOption == null) {
                    continue
                }
                for (floorAt in 0..<nfloors) {
                    if (building.first.elevatorAt == floorAt) {
                        continue
                    }
                    val moveIn = buildingWithoutOption.moveIn(floorAt, opt)
                    if (moveIn != null && moveIn !in visited) {
                        q.add(moveIn to (building.second + (opt to floorAt)))
                        visited.add(moveIn)
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }


    data class Floor(val generators: List<Generator> = listOf(), val microchips: List<DMicrochip> = listOf()) {
        fun isValid(): Boolean {
            return generators.isEmpty() || microchips.isEmpty() || microchips.containsAll(generators)
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
                //generator will be already addressed in the for above
            }

            return result
                .filter { it.isValid() }
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


        fun score(): Int {
            var score = 0
            for (i in 0..<floors.size) {
                score += (floors.size - i) * (floors[i].generators.size + floors[i].microchips.size)
            }
            return score
        }

        fun floorHash(): String {
            val sb = StringBuilder()
            sb.append(elevatorAt)
            for (floor in floors) {
                sb.append(floor.generators.size)
                sb.append("+")
                sb.append(floor.microchips.size)
                sb.append(";")
            }

            return sb.toString()
        }

        fun equalsHaha(other: Any?): Boolean {
            if(other !is Building){
                return false
            }
            val otherf = other.floors.subList(0, other.floors.size-1)
            return this.floors
                .take(this.floors.size-1)
                .all { it in otherf  }
        }
    }
}
