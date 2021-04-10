#!/bin/bash
now=`date +"%Y-%m-%d"`
filename=backup-${now}.tar.gz

ssh sl "tar -czf ${filename} /home/spigot/"
scp sl:~/${filename} /var/backups/sl/${filename}
ssh sl "rm ${filename}"
find /var/backups/sl/* -mtime +5 -exec rm {} \; # delete backups older than 5 days