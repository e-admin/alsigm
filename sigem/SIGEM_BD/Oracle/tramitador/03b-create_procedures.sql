-- Procedimiento para gestionar las secuencias
CREATE OR REPLACE PROCEDURE spac_nextval(sequence_name IN VARCHAR2, sequence_id OUT NUMBER)
	IS
		stmt VARCHAR(512);
	BEGIN
		stmt:='SELECT '||sequence_name||'.NEXTVAL FROM DUAL';
		EXECUTE IMMEDIATE stmt into sequence_id;
	END;
/

-- Funcion auxiliar para insertar el contenido hexadecimal de las plantillas
CREATE OR REPLACE FUNCTION concat_hexclob_to_blob(hc in CLOB, b in BLOB) RETURN BLOB
	IS
		c BLOB;
	BEGIN
		DBMS_LOB.CREATETEMPORARY(c, TRUE);
		IF DBMS_LOB.GETLENGTH(b) >= 1 THEN
			DBMS_LOB.APPEND(c, b);
			DBMS_LOB.WRITE(c, DBMS_LOB.GETLENGTH(hc) / 2, DBMS_LOB.GETLENGTH(b) + 1, HEXTORAW(TO_CHAR(hc)));
		ELSE
			DBMS_LOB.WRITE(c, DBMS_LOB.GETLENGTH(hc) / 2, 1, HEXTORAW(TO_CHAR(hc)));
		END IF;
		RETURN c;
	END;
/