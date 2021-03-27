package me.skylands.skypvp.clan;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.user.User;

public class ModuleUUID {

    public static String getNameByUuid(final String uuid) {
        return SkyLands.userService.getUserByUuid(uuid).getName();
    }

    public static String getUuidByName(final String name) {
        User userByName = SkyLands.userService.getUserByName(name);

        if (userByName != null) {
            return userByName.getUuid();
        }

        return null;
    }

}
