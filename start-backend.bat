@echo off
rem Start simple drone backend.
setlocal EnableDelayedExpansion
cd /d "%~dp0backend"

where java >nul 2>nul
if errorlevel 1 (
  echo ERROR: Java was not found. Please install JDK 8 or JDK 11.
  pause
  exit /b 1
)

where mvn >nul 2>nul
if errorlevel 1 (
  set "LOCAL_MVN="
  for /d %%D in ("%~dp0..\tools\apache-maven-*") do (
    if exist "%%D\bin\mvn.cmd" set "LOCAL_MVN=%%D\bin\mvn.cmd"
  )
  if not defined LOCAL_MVN (
    echo ERROR: Maven was not found.
    pause
    exit /b 1
  )
  call "!LOCAL_MVN!" "-Dmaven.test.skip=true" spring-boot:run
) else (
  call mvn "-Dmaven.test.skip=true" spring-boot:run
)

pause
