-- Actualizamos el contador de Dept.
UPDATE IUSERNEXTID SET ID = (SELECT (MAX(ID) + 1) FROM IUSERDEPTHDR) WHERE TYPE=2;

-- Borramos de la tabla SCR_CONTADOR el contador de dept. IUSERDEPTHDR
DELETE FROM SCR_CONTADOR WHERE TABLAID='IUSERDEPTHDR';

--modulo sir actualizacion a 1.0.1
ALTER TABLE sir_anexos
  ADD cd_id_documento_firmado character varying(50);
