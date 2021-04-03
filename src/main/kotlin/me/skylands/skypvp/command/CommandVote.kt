package me.skylands.skypvp.command

import com.vexsoftware.votifier.model.Vote
import com.vexsoftware.votifier.model.VotifierEvent
import me.skylands.skypvp.Messages
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class CommandVote : AbstractCommand() {
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        if (sender.isOp && args.size == 1) {
            val vote = Vote()
            vote.username = args[0]
            Bukkit.getPluginManager().callEvent(VotifierEvent(
                vote
            ))
            return
        }

        sender.sendMessage(Messages.PREFIX + "Jetzt voten und eine Belohnung erhalten:")
        sender.sendMessage(" " + Messages.getVoteUrl(sender.name))
        sender.sendMessage(" §cWichtig: Du musst dazu online sein!")
    }

    override fun getName(): String {
        return "vote"
    }
}