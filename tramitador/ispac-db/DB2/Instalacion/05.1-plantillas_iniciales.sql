-----------------------
-- PLANTILLAS --
-----------------------

-- Comando para exportar las plantillas
--EXPORT TO ./plantillas/plantillas_iniciales OF DEL
--	LOBS TO ./plantillas/lobs
--	LOBFILE plantilla
--	MODIFIED BY lobsinfile
--    SELECT ID, NBYTES, BLOQUE, MIMETYPE FROM spac_p_blp ORDER BY ID;

-- Comando para importar las plantillas
IMPORT FROM ./plantillas/plantillas_iniciales OF DEL
	LOBS FROM ./plantillas/lobs
	MODIFIED BY lobsinfile
    INSERT INTO spac_p_blp(ID, NBYTES, BLOQUE, MIMETYPE);
