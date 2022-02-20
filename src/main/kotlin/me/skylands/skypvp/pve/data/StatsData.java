package me.skylands.skypvp.pve.data;

public class StatsData {

    private final String formattedDamage;
    private final String formattedPercentage;

    public StatsData(String formattedPercentage, String formattedDamage) {
        this.formattedPercentage = formattedPercentage;
        this.formattedDamage = formattedDamage;
    }

    public String getFormattedDamage() {
        return formattedDamage;
    }

    public String getPercentage() {
        return formattedPercentage;
    }
}