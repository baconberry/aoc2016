import utils.getIntList

class D12 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = lines
            .filter { it.isNotEmpty() }
            .toList()

        val emu = Emu(instructions)
        if(part==2u){
            emu.registers["c"] = 1
        }
        emu.execute()
        return "${emu.registers["a"]}"
    }


    class Emu(val instructions: List<String>) {
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
                    jumpIfNotZero(nums, parts)
                }
                pc++
            }
        }

        private fun jumpIfNotZero(nums: List<Int>, parts: List<String>) {
            if (nums.size > 1) {
                if (nums[0] != 0) {
                    pc += nums[1] - 1
                }
            } else {
                val v = registers.getOrDefault(parts[1], 0)
                if (v != 0) {
                    pc += nums[0] - 1
                }
            }
        }

        private fun decrement(parts: List<String>) {
            val v = registers.getOrDefault(parts[1], 0)
            registers[parts[1]] = v - 1
        }

        private fun increment(parts: List<String>) {
            val v = registers.getOrDefault(parts[1], 0)
            registers[parts[1]] = v + 1
        }

        private fun copy(nums: List<Int>, parts: List<String>) {
            if (nums.isNotEmpty()) {
                registers[parts[parts.size - 1]] = nums[0]
            } else {
                registers[parts[2]] = registers.getOrDefault(parts[1], 0)
            }
        }
    }
}