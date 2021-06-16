set echo on

ALTER SESSION SET "_ORACLE_SCRIPT"=true;

@Drop_DB_Objects.sql
@Create_DB_Objects.sql

ALTER SESSION SET CURRENT_SCHEMA=OES;

--DDL Scripts
@commands\DDL\DDL_1.sql;

--DML Scripts

@commands\DML\APPCONFIG.sql

set define on;

COMMIT;

spool off;
exit;