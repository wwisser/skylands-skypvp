<?php

$file_path = "\...\...\users.yml";
$file_path_new = "\...\...\users_updated.yml";

if(@fopen($file_path, "r")) {
    echo "Found user file! Converting..";

    foreach(explode("\n", file_get_contents($file_path)) as $line) {
        $line = str_replace(":", ";", $line);
        $line_as_array = explode(";", $line);

        $user_data = [
            "uuid" => $line_as_array[0],
            "firstSeen" => $line_as_array[1],
            "lastSeen" => $line_as_array[2],
            "kills" => $line_as_array[3],
            "currentKillstreak" => "0",
            "bloodPoints" => "0",
            "deaths" => $line_as_array[4],
            "votes" => $line_as_array[5],
            "playtime" => $line_as_array[6],
            "level" => $line_as_array[7]
        ];

        $formatted_line = $user_data["uuid"] . ":" . $user_data["firstSeen"] . ";" . $user_data["lastSeen"] . ";" . $user_data["kills"] . ";" . $user_data["currentKillstreak"] . ";" . $user_data["bloodPoints"] . ";" . $user_data["deaths"] . ";" . $user_data["votes"] . ";" . $user_data["playtime"] . ";" . $user_data["level"];
        file_put_contents($file_path_new, PHP_EOL . $formatted_line, FILE_APPEND | LOCK_EX);
    }

    echo PHP_EOL . "Done! Updated user file can be found at " . $file_path_new;
} else {
    echo PHP_EOL . "File doesn't exist";
}
