@REM script to compile scss files into css

@ECHO OFF

sass .\src\static\styles\sass
ECHO All non-partial sass files have been compiled

move .\src\static\styles\sass\*.css .\src\static\styles\css >nul
@REM move .\src\static\styles\sass\components\*.css .\src\static\styles\css\components >nul
ECHO All compiled css files have been moved to the directory .\src\static\styles\css

move .\src\static\styles\sass\*.map .\src\static\styles\maps >nul
@REM move .\src\static\styles\sass\components\*.map .\src\static\styles\maps\components >nul
ECHO All map files have been moved to the directory .\src\static\styles\map
