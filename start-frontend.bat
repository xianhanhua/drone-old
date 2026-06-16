@echo off
rem Start simple drone Vue frontend.
cd /d "%~dp0frontend"
npm install
npm run dev
pause
