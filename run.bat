@echo off
setlocal
javac Main.java Patient.java RecordManager.java FileHandler.java
if errorlevel 1 (
  echo Compile failed.
  exit /b 1
)
java -cp . Main
endlocal
