package me.skylands.skypvp.config

import me.skylands.skypvp.SkyLands

class MotdConfig {

    companion object {
        private const val FILE_NAME = "motd.yml"

        private const val KEY_HEADER = "header"
        private const val KEY_FOOTER = "footer"
    }

    private var config: Config

    init {
        this.config = Config(SkyLands.CONFIG_PATH, FILE_NAME)
        this.config.setDefault(KEY_HEADER, "§6§lSkyLands")
        this.config.setDefault(KEY_FOOTER, "§7MOTD wird geladen...")
    }

    fun reloadConfig() {
        this.config = Config(SkyLands.CONFIG_PATH, FILE_NAME)
    }

    fun updateHeader(header: String) {
        this.config[KEY_HEADER] = header
        this.config.saveFile()
    }

    fun updateFooter(footer: String) {
        this.config[KEY_FOOTER] = footer
        this.config.saveFile()
    }

    fun getHeader(): String {
        return this.config.getString(KEY_HEADER)
    }

    fun getFooter(): String {
        return this.config.getString(KEY_FOOTER)
    }
}