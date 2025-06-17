import utils.getIntList

class D12 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = lines
            .filter { it.isNotEmpty() }
            .toList()

        val emu = Emu(instructions)
        if (part == 2u) {
            emu.registers["c"] = 1
        }
        emu.execute()
        return "${emu.registers["a"]}"
    }


}

class Emu(originalInstructions: List<String>) {
    val instructions = ArrayList(originalInstructions)
    var pc = 0
    var registers = mutableMapOf<String, Int>()

    fun execute() {
        while (pc < instructions.size) {
            val instruction = instructions[pc]
            val nums = getIntList(instruction)
            val parts = instruction.split(" ")
            if (instruction.startsWith("cpy")) {
                copy(nums, parts)
            }
            if (instruction.startsWith("inc")) {
                increment(parts)
            }
            if (instruction.startsWith("dec")) {
                decrement(parts)
            }
            if (instruction.startsWith("jnz")) {
                jumpIfNotZero(parts)
            }
            if (instruction.startsWith("tgl")) {
                toggle(getNumber(parts[1]))
            }
            pc++
        }
    }

    private fun toggle(n: Int) {
        val idx = pc + n
        if (idx >= instructions.size) {
            return
        }
        val instruction = instructions[idx]
        val parts = instruction.split(" ").toMutableList()
        if (parts.size == 2) {
            parts[0] = if (parts[0] == "inc") {
                "dec"
            } else {
                "inc"
            }
        } else if (parts.size == 3) {
            parts[0] = if (parts[0] == "jnz") {
                "cpy"
            } else {
                "jnz"
            }
        }
        instructions[idx] = parts.joinToString(" ")

    }

    private fun jumpIfNotZero(parts: List<String>) {
        val n = getNumber(parts[1])
        if (n == 0) {
            return
        }
        val jump = getNumber(parts[2])
        pc += jump - 1
    }

    fun getNumber(n: String): Int {
        val nlist = getIntList(n)
        if (nlist.isNotEmpty()) {
            return nlist[0]
        }
        return registers.getOrDefault(n, 0)
    }

    private fun decrement(parts: List<String>) {
        val v = getNumber(parts[1])
        registers[parts[1]] = v - 1
    }

    private fun increment(parts: List<String>) {
        val v = getNumber(parts[1])
        registers[parts[1]] = v + 1
    }

    private fun copy(nums: List<Int>, parts: List<String>) {
        if (nums.isNotEmpty()) {
            registers[parts[parts.size - 1]] = nums[0]
        } else {
            registers[parts[2]] = getNumber(parts[1])
        }
    }
}
