-- /***************************/
-- /* Actualización 4.2->4.3            */
-- /***************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','4.2->4.3',now());

	-- Aniadir marcas de bloqueo a la tabla de unidades documentales en la relacion
	ALTER TABLE ASGTUNIDADDOCRE ADD MARCASBLOQUEO INTEGER DEFAULT 0 NOT NULL;

	-- Aniadir marcas de bloqueo a la tabla de unidades documentales
	ALTER TABLE ASGFUNIDADDOC ADD MARCASBLOQUEO INTEGER DEFAULT 0 NOT NULL;