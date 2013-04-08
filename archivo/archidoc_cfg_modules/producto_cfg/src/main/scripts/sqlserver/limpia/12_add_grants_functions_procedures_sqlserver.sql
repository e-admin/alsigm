-- Permisos para funciones de la versión 4.0
-- Es necesario modificar el nombre de usuario

DECLARE @usuario VARCHAR(50);

--SET @usuario = N'archidoc';
SET @usuario = N'usuario';

--FUNCIONES
exec ('grant execute on GREATEST                     to '+ @usuario);
exec ('grant execute on DEVOLVERMARCA                to '+ @usuario);
exec ('grant execute on CALCULARFINALCODREFPADRE     to '+ @usuario);
exec ('grant execute on GETNUMERICPOSITIVE           to '+ @usuario);
exec ('grant execute on GETCODREF           		to '+ @usuario);
exec ('grant execute on GETFINCODREFPADRE      		to '+ @usuario);
exec ('grant execute on UPDATECODREF           		to '+ @usuario);


--PROCEDIMIENTOS
exec ('grant execute on IdsDescendientesNoAsignable to '+ @usuario);
