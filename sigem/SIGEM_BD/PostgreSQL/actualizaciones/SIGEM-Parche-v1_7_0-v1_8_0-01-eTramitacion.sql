ALTER TABLE sgmrdedocumentos
RENAME COLUMN hash TO filehash;

ALTER TABLE sgmntinfo_documento
ALTER COLUMN cnotiexpe TYPE character(32);

ALTER TABLE sgmntinfo_notificacion
ALTER COLUMN cguid TYPE character varying(32);

ALTER TABLE sgmntinfo_notificacion
ALTER COLUMN cnumexp TYPE character(32);

