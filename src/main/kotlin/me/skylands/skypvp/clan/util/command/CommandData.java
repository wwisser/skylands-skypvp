package me.skylands.skypvp.clan.util.command;

import me.skylands.skypvp.clan.util.clan.ClanRank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandData {

    String name() default "unknown";

    String args() default "";

    String description() default "Default Description";

    ClanRank permission() default ClanRank.NO_CLAN;

    boolean hideNoPermission() default true;

    boolean strictPermission() default false;

}
