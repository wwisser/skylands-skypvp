package me.skylands.skypvp.command.exception

class TargetNotFoundException(target: String?) : CommandException("§cZiel $target nicht gefunden.")
