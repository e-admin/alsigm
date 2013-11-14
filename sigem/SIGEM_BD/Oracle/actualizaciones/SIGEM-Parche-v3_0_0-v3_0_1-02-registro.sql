--Actualizamos el contador para la tabla IDOCFMT
UPDATE IDOCNEXTID SET id = (select (max(id) + 1) from idocfmt) where type = 10;