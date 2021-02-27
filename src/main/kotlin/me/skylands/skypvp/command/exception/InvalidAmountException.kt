package me.skylands.skypvp.command.exception

class InvalidAmountException(rawNumber: String) : CommandException("§c'$rawNumber§c' ist keine gültige Zahl.")