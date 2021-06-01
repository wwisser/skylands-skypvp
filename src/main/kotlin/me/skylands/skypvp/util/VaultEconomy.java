package me.skylands.skypvp.util;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class VaultEconomy implements Economy {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Levelsystem";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double v) {
        return v + " Level";
    }

    @Override
    public String currencyNamePlural() {
        return "Level";
    }

    @Override
    public String currencyNameSingular() {
        return "Level";
    }

    @Override
    public boolean hasAccount(String s) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

    @Override
    public double getBalance(String s) {
        if(Bukkit.getPlayer(s) != null) {
            return getBalance(Bukkit.getPlayer(s), "");
        }
        return getBalance(Bukkit.getOfflinePlayer(s), "");
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getBalance(offlinePlayer, "");
    }

    @Override
    public double getBalance(String s, String s1) {
        if(Bukkit.getPlayer(s) != null) {
            return getBalance(Bukkit.getPlayer(s), "");
        }
        return getBalance(Bukkit.getOfflinePlayer(s), "");
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        UserService userService = SkyLands.userService;
        User user = userService.getUserByUuid(offlinePlayer.getUniqueId().toString());

        if(Bukkit.getPlayer(offlinePlayer.getName()) != null) {
            Player player = Bukkit.getPlayer(offlinePlayer.getName());
            return player.getLevel();
        }
        return user.getLevel();
    }

    @Override
    public boolean has(String s, double v) {
        if(Bukkit.getPlayer(s) != null) {
            return has(Bukkit.getPlayer(s),"", v);
        }
        return has(Bukkit.getOfflinePlayer(s), "",  v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return has(offlinePlayer, "", v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        if(Bukkit.getPlayer(s) != null) {
            return has(Bukkit.getPlayer(s), "", v);
        }
        return has(Bukkit.getOfflinePlayer(s), "", v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        UserService userService = SkyLands.userService;
        User user = userService.getUserByUuid(offlinePlayer.getUniqueId().toString());

        if(Bukkit.getPlayer(offlinePlayer.getName()) != null) {
            Player player = Bukkit.getPlayer(offlinePlayer.getName());
            return (player.getLevel() >= v);
        }
        return (user.getLevel() >= v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        if(Bukkit.getPlayer(s) != null) {
            return withdrawPlayer(Bukkit.getPlayer(s), v);
        }
        return withdrawPlayer(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        UserService userService = SkyLands.userService;
        User user = userService.getUserByUuid(offlinePlayer.getUniqueId().toString());

        if(Bukkit.getPlayer(offlinePlayer.getName()) != null) {
            Player player = Bukkit.getPlayer(offlinePlayer.getName());
            player.setLevel(player.getLevel() - (int) v);
            return new EconomyResponse(v, player.getLevel(), EconomyResponse.ResponseType.SUCCESS, "");
        } else {
            user.setLevel(user.getLevel() - (int) v);
            userService.saveUser(user);
        }

        return new EconomyResponse(v, user.getLevel(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        if(Bukkit.getPlayer(s) != null) {
            return withdrawPlayer(Bukkit.getPlayer(s), v);
        }
        return withdrawPlayer(Bukkit.getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        if(Bukkit.getPlayer(s) != null) {
            return depositPlayer(Bukkit.getPlayer(s), "", v);
        }
        return depositPlayer(Bukkit.getOfflinePlayer(s), "", v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer, "", v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        if(Bukkit.getPlayer(s) != null) {
            return depositPlayer(Bukkit.getPlayer(s), "", v);
        }
        return depositPlayer(Bukkit.getOfflinePlayer(s), "", v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        UserService userService = SkyLands.userService;
        User user = userService.getUserByUuid(offlinePlayer.getUniqueId().toString());

        if(Bukkit.getPlayer(offlinePlayer.getName()) != null) {
            Player player = Bukkit.getPlayer(offlinePlayer.getName());
            player.setLevel(player.getLevel() + (int) v);
            return new EconomyResponse(v, player.getLevel(), EconomyResponse.ResponseType.SUCCESS, "");
        } else {
            user.setLevel(user.getLevel() + (int) v);
            userService.saveUser(user);
        }
        return new EconomyResponse(v, user.getLevel(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
