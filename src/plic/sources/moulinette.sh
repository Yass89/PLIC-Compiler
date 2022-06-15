#!/bin/bash
if [ $# -ne 1 ]; then
    echo "Missing one argument .jar"
    exit 1
fi
for file in *.plic; do
echo "$file"
java -jar $1 "$file"
printf "\n"
done

