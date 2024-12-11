# !/bin/bash

echo "Enter Commit Message -> "
read msg

git add .
git commit -am"$msg"
git push 
