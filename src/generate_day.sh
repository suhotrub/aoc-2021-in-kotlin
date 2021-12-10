if [ -z "$1" ]
then
  echo day number is empty
else
  touch Day$1.txt
  touch Day$1_test.txt
  touch Day$1.kt
  git add Day$1.kt
fi