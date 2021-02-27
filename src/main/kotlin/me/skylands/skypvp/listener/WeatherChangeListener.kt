package me.skylands.skypvp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.weather.WeatherChangeEvent

class WeatherChangeListener : Listener {

    @EventHandler
    fun onWeatherChange(event: WeatherChangeEvent) {
        if (event.toWeatherState()) {
            event.isCancelled = true
        }
    }

}