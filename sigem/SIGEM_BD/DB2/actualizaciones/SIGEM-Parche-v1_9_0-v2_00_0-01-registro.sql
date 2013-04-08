--Crear una lista de volúmenes propia para los documentos firmados de tramitación
INSERT INTO ivollisthdr (id, name, flags, stat, remarks, crtrid, crtndate, updrid, upddate)	VALUES (5, 'ListaTramitadorSegura01', 0, 0, NULL, 0, current_timestamp, NULL, NULL); 
UPDATE ivolarchlist SET listid=5 where archid=4;
INSERT INTO ivollistvol (listid, volid, sortorder) SELECT 5, volid, sortorder FROM ivollistvol WHERE listid=4;
