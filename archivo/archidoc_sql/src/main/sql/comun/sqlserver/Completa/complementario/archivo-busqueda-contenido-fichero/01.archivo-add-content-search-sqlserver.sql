-- Añadir un linked server al servicio de index server 'Web', que debe tener definido como
-- uno de sus directorios la dirección de red del repositorio electrónico (Ej: \\10.228.20.176\repfs)
-- con la información de usuario y contraseña rellenas.
	EXEC sp_addlinkedserver FileSystem,
           'Index Server',
           'MSIDXS',
           'Web'