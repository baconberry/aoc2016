class D23 : Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = lines
            .filter { it.isNotEmpty() }
            .toList()

        val emu = Emu(instructions)
        if (part == 2u) {
            emu.registers["a"] = 12
        } else {
            emu.registers["a"] = 7
        }
        emu.execute()
        return "${emu.registers["a"]}"
    }


}