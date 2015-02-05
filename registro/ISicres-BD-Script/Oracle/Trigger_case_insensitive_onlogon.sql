-- Trigger encargado de convertir la sesion del usuario logado,
-- a insensible con las mayúsculas/minúsculas

create or replace trigger case_insensitive_onlogon after logon on schema
begin
-- This makes the database case insensitive for sorting and searching
-- (Not working? Your client tool may be overriding the NLS Parameters)
    execute immediate 'alter session set NLS_COMP=LINGUISTIC';
    execute immediate 'alter session set NLS_SORT=BINARY_CI';
end;
