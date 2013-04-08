-- TABLA scr_typeaddress
-- Modificamos los valores del registro de telefono para poner telefono (fijo)
update scr_typeaddress set description='Teléfono (fijo)', code='TF' where id = 1;

--Insertamos el nuevo valor para telefono movil
insert into scr_typeaddress (id, description, code) values (5,'Teléfono (móvil)','TM');
