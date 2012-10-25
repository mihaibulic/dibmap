#!/bin/bash

hash=$(git rev-parse --verify HEAD)
time=$(date +%m%d-%H%M)
mysqldump -u root dibmap > "db/${time}.${hash}.mysql.backup"

git add --all
git commit -a -m "$@"
git push origin master
