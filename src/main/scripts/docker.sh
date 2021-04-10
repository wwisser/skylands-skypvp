#!/bin/bash
docker run -d -p 25565:25565 -p 8192:8192 -v /home/spigot:/minecraft --name spigot -e EULA=true -e SPIGOT_VER=1.8.8 -e MC_MAXMEM=3g -e MC_MINMEM=2g -e SPIGOT_AUTORESTART=yes nimmis/spigot