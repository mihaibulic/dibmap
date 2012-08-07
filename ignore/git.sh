#!/bin/bash

mysqldump -u root dibmap > "db/$(date +%d%m-%H%M).mysql.backup"

git add --all
git commit -a -m "$@"
git push origin master
