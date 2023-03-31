@REM script to compile scss files into css

@ECHO OFF

sass .\src\static\styles\sass
move .\src\static\styles\sass\*.css .\src\static\styles\css
move .\src\static\styles\sass\*.map .\src\static\styles\maps

ECHO Success! All non-partial sass files have been compiled to the directory .\src\static\styles\css
