@echo off
echo [INFO]  init schema/data.
echo [INFO] confirm has created db.
cd %~dp0
cd ../web
echo [INFO] init schema.
call mvn db:schema

echo [INFO] init data.
call mvn db:data

cd ../bin
pause