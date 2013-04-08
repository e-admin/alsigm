------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Nuevo estado para las notificaciones (Errores devuelto por conector externo)
insert into sgmntinfo_estado_noti (cguid, cuidsisnot, cdescripcion) values (-2, 'Notificacion con errores', 'Error devuelto por conector');
