@echo off
where gradle >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo Gradle not found in PATH. Please install Gradle or provide the wrapper.
    exit /b 1
)
gradle assembleDebug