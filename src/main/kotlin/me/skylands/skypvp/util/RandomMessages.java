package me.skylands.skypvp.util;

import me.skylands.skypvp.SkyLands;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class RandomMessages {

    private final List<String> messages;

    public RandomMessages() throws IOException {
        InputStreamReader inputR = new InputStreamReader(new FileInputStream(SkyLands.CONFIG_PATH + "/messages.txt"), StandardCharsets.UTF_8);

        String file = IOUtils.toString(inputR);
        List<String> list = new LinkedList<String>(Arrays.asList(file.split("\n")));
        List<String> removedEntries = new LinkedList<String>();

        Consumer<String> sort = e -> {
            if (e.startsWith("#")) {
                removedEntries.add(e);
            }
        };

        list.forEach(sort);

        removedEntries.forEach(list::remove);

        messages = list;
    }


    public List<String> getAll() {
        return messages;
    }
}
