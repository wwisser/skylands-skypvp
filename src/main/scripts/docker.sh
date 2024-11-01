#!/bin/bash

# SkyPvP (spigot)
docker run -e OTHER_JAVA_OPS=-Dlog4j2.formatMsgNoLookups=true -d -p 25565:25565 -p 8192:8192 -v /home/spigot:/minecraft --name spigot -e EULA=true -e SPIGOT_VER=1.8.8 -e SPIGOT_AUTORESTART=yes nimmis/spigot

# Test (build)
docker run -e OTHER_JAVA_OPS=-Dlog4j2.formatMsgNoLookups=true -d -p 25566:25566 -v /home/build:/minecraft --name build -e EULA=true -e SPIGOT_VER=1.8.8 -e MC_MAXMEM=300m -e SPIGOT_AUTORESTART=yes nimmis/spigot
