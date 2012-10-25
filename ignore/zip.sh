#!/bin/bash
set -e

# move files to be ignored back, here to ensure it will run even if something fails
function cleanup 
{
  mv $dest/.git $loc
  mv $dest/.project $loc
  mv $dest/ignore $loc
  mv $dest/docs $loc
}

loc="/home/mihai/Workspaces/dibmap"
dest="/tmp"
version=$(date -u +%y%m%d%H%M)

# move previous deployment zips to the archive
find $loc -maxdepth 1 -name 'dibmap_v*.zip' -exec mv {} $loc/ignore/archive \;

# move files to be ignored out of the way while zipping occurs
mv $loc/.git* $dest
mv $loc/.project $dest
mv $loc/ignore $dest
mv $loc/docs $dest
trap cleanup EXIT

# zippity zip
cd $loc
zip -rq dibmap_v$version.zip . 

