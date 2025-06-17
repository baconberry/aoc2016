class D23:Solver {
    override fun solve(lines: Array<String>, part: UInt): String {
        val instructions = lines
            .filter { it.isNotEmpty() }
            .toList()

        val emu = Emu(instructions)
        emu.registers["a"] = 7
        emu.execute()
        return "${emu.registers["a"]}"
    }


}