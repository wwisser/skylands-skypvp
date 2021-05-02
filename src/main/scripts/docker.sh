#!/bin/bash

# SkyPvP (spigot)
docker run -d -p 25565:25565 -p 8192:8192 -v /home/spigot:/minecraft --name spigot -e EULA=true -e SPIGOT_VER=1.8.8 -e MC_MINMEM=1g -e SPIGOT_AUTORESTART=yes nimmis/spigot

# Test (build)
docker run -d -p 25566:25566 -v /home/build:/minecraft --name build -e EULA=true -e SPIGOT_VER=1.8.8 -e MC_MAXMEM=512m -e SPIGOT_AUTORESTART=yes nimmis/spigot
