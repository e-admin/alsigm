DROP INDEX scr_userfilter2;
CREATE UNIQUE INDEX scr_userfilter2 ON scr_userfilter USING btree (idarch, iduser, type_obj);