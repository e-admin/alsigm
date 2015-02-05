ALTER TABLE ONLY iuserdata ADD CONSTRAINT pk_iuserdata PRIMARY KEY (id) ;

CREATE INDEX iuserdata_ix_id ON iuserdata USING btree (id);