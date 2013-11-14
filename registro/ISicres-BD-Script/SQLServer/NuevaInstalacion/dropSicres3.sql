ALTER TABLE scr_citiesexreg DROP CONSTRAINT fk_citiesexregcities;
ALTER TABLE scr_entityreg DROP CONSTRAINT fk_entityofic;
ALTER TABLE scr_provexreg ADD CONSTRAINT fk_provexregprov;
ALTER TABLE scr_tramunit DROP CONSTRAINT fk_tramunitgorgs;

drop table scr_tramunit;
drop table scr_tramunit_id_seq;

drop table scr_entityreg;
drop table scr_entityreg_id_seq;

drop table scr_exregstate;
drop table scr_exregstate_id_seq;

drop table scr_exreg;
drop table scr_exreg_id_seq;

drop table scr_exreg_in;
drop table scr_exreg_in_id_seq;

drop table scr_provexreg;
drop table scr_citiesexreg;


drop table scr_attachment_sign_info;
drop table scr_attachment_sign_info_seq;

drop table scr_attachment;
drop table scr_attachment_seq;



drop TABLE scr_attachment_validity_type;

drop table scr_attachment_document_type;

drop table scr_repre;

drop table scr_ttexreg;
