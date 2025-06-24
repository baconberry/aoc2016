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

class Emu(
    originalInstructions: List<String>,
    val maxExecutions: Long = Long.MAX_VALUE,
    val maxOut: Int = Int.MAX_VALUE
) {
    val instructions = ArrayList(originalInstructions)
    var pc = 0
    var registers = mutableMapOf<String, Int>()
    val sout = StringBuilder()

    fun execute(): Boolean {
        var icounter = 0L
        var outs = 0
        while (pc < instructions.size && icounter < maxExecutions && outs < maxOut) {
            icounter++
            val instruction = instructions[pc]
            val nums = getIntList(instruction)
            val parts = instruction.split(" ")
            val ic = parts[0]
            when (ic) {
                "cpy" -> copy(nums, parts)
                "inc" -> increment(parts)
                "dec" -> decrement(parts)
                "jnz" -> jumpIfNotZero(parts)
                "tgl" -> toggle(getNumber(parts[1]))
                "mv" -> move(parts)
                "mul" -> multiply(parts)
                "sum" -> sum(parts)
                "out" -> {
                    outs++
                    sout.append("${getNumber(parts[1])}")
                }
            }
            pc++
        }
        if (icounter < maxExecutions) {
            println("Total executed instructions $icounter")
        }
        return icounter < maxExecutions
    }

    //optimization for sum
    //inc a | dec b | jnz b -2 -> sum a b a | cpy 0 b | jnz b -2
    private fun sum(parts: List<String>) {
        registers[parts[3]] = getNumber(parts[1]) + getNumber(parts[2])
    }

    //optimization for mul
    // cpy b c | inc a | dec c | jnz c -2 | dec d | jnz d -5 ->
    // sum b a a | cpy 0 c | dec d | jnz d -3 ->
    // mul d b a | cpy 0 c | cpy 0 d
    private fun multiply(parts: kotlin.collections.List<String>) {
        registers[parts[3]] = getNumber(parts[1]) * getNumber(parts[2])
    }

    private fun toggle(n: Int) {
        val idx = pc + n
        if (idx >= instructions.size) {
            return
        }
        val instruction = instructions[idx]
        val parts = instruction.split(" ").toMutableList()
        check(parts[0] != "mv") {
            "Could not toggle mv"
        }
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

    private fun move(parts: List<String>) {
        registers[parts[2]] = getNumber(parts[2]) + getNumber(parts[1])
        registers[parts[1]] = 0
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
