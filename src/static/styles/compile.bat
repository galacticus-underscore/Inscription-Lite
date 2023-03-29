@ECHO OFF

sass .\src\static\styles\sass
move .\src\static\styles\sass\*.css .\src\static\styles\css
move .\src\static\styles\sass\*.map .\src\static\styles\maps
