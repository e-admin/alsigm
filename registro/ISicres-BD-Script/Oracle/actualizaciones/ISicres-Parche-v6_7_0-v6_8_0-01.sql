--Inserta una nueva dirección telemática de personas
INSERT INTO scr_typeaddress (id, description, code) VALUES (4, 'Dirección electrónica única', 'DU');

-- TABLA scr_typeaddress
-- Modificamos los valores del registro de telefono para poner telefono (fijo)
update scr_typeaddress set description='Teléfono (fijo)', code='TF' where id = 1;

--Insertamos el nuevo valor para telefono movil
insert into scr_typeaddress (id, description, code) values (5,'Teléfono (móvil)','TM');
