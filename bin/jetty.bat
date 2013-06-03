@echo off
echo [INFO]  jetty starting
cd %~dp0
cd ../web

call mvn clean jetty:run  -Dmaven.test.skip=true  

cd ../bin
pause