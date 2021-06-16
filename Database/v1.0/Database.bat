@ECHO OFF

SET logfile=database.log
SET username=system
SET password=oracle
SET sqlfilename=Main.sql
SET CURPATH=%~dp0

del .\%logfile%

TIMEOUT /T 4
cd /d %CURPATH%
echo exit | sqlplus %username%/%password% @%sqlfilename% > %logfile%

pause;