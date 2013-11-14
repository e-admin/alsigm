--------------------------------
-- FOREIGN KEYS ARCHIVADORES ---
--------------------------------
ALTER TABLE ONLY scr_addrtel DROP CONSTRAINT fk_scr_addrtel0;
ALTER TABLE ONLY scr_caadm DROP CONSTRAINT fk_scrcaadm0;
ALTER TABLE ONLY scr_caofic DROP CONSTRAINT fk_scr_caofic0;
ALTER TABLE ONLY scr_lockrelations DROP CONSTRAINT fk_scrlckrel0;
ALTER TABLE ONLY scr_ofic DROP CONSTRAINT fk_scr_ofic0;
ALTER TABLE ONLY scr_ofic DROP CONSTRAINT fk_scr_ofic1;
ALTER TABLE ONLY scr_oficadm DROP CONSTRAINT fk_scroficadm0;
ALTER TABLE ONLY scr_orgs DROP CONSTRAINT fk_scr_orgs0;
ALTER TABLE ONLY scr_pfis DROP CONSTRAINT fk_scr_pfis0;
ALTER TABLE ONLY scr_pjur DROP CONSTRAINT fk_scr_pjur0;
ALTER TABLE ONLY scr_privorgs DROP CONSTRAINT fk_scr_privorgs0;
ALTER TABLE ONLY scr_recvmsg DROP CONSTRAINT fk_scr_recvmsg0;
ALTER TABLE ONLY scr_recvmsg DROP CONSTRAINT fk_scr_recvmsg1;
ALTER TABLE ONLY scr_relations DROP CONSTRAINT fk_scrrels0;
ALTER TABLE ONLY scr_relations DROP CONSTRAINT fk_scrrels1;
ALTER TABLE ONLY scr_reportofic DROP CONSTRAINT fk_scr_reportofic0;
ALTER TABLE ONLY scr_sendmsg DROP CONSTRAINT fk_scr_sendmsg0;
ALTER TABLE ONLY scr_sendmsg DROP CONSTRAINT fk_scr_sendmsg1;
ALTER TABLE ONLY scr_sendreg DROP CONSTRAINT fk_scr_sendreg0;
ALTER TABLE ONLY scr_usrofic DROP CONSTRAINT fk_scr_usrofic0;
ALTER TABLE ONLY scr_ws DROP CONSTRAINT fk_scr_ws0;
ALTER TABLE ONLY scr_pagerepository DROP CONSTRAINT fk_pagerepository0 CASCADE;

ALTER TABLE ONLY scr_entityreg DROP CONSTRAINT fk_entityofic ;
ALTER TABLE ONLY scr_tramunit DROP CONSTRAINT fk_tramunitgorgs  ;
ALTER TABLE ONLY scr_provexreg  DROP CONSTRAINT fk_provexregprov ;
ALTER TABLE ONLY scr_citiesexreg   DROP CONSTRAINT fk_citiesexregcities ;

ALTER TABLE ONLY a2sf DROP CONSTRAINT fk_a2sf8;
ALTER TABLE ONLY a2sf DROP CONSTRAINT fk_a2sf7;
ALTER TABLE ONLY a2sf DROP CONSTRAINT fk_a2sf5;
ALTER TABLE ONLY a2sf DROP CONSTRAINT fk_a2sf12;
ALTER TABLE ONLY a1sf DROP CONSTRAINT fk_a1sf16;
ALTER TABLE ONLY a1sf DROP CONSTRAINT fk_a1sf8;
ALTER TABLE ONLY a1sf DROP CONSTRAINT fk_a1sf7;
ALTER TABLE ONLY a1sf DROP CONSTRAINT fk_a1sf5;
ALTER TABLE ONLY a1sf DROP CONSTRAINT fk_a1sf13;

ALTER TABLE ONLY scr_ca DROP CONSTRAINT pk_scr_ca CASCADE;
ALTER TABLE ONLY scr_orgs DROP CONSTRAINT pk_scr_orgs CASCADE;
ALTER TABLE ONLY scr_ofic DROP CONSTRAINT pk_scr_ofic CASCADE;

ALTER TABLE ONLY scr_entityreg DROP CONSTRAINT pk_scr_entityreg CASCADE;
ALTER TABLE ONLY scr_tramunit DROP CONSTRAINT pk_scr_tramunit  CASCADE;
ALTER TABLE ONLY scr_exreg DROP CONSTRAINT pk_scr_exreg;
ALTER TABLE ONLY scr_exregaccept DROP CONSTRAINT pk_scr_exregaccept CASCADE;
ALTER TABLE ONLY scr_provexreg  DROP CONSTRAINT pk_scr_provexreg CASCADE;
ALTER TABLE ONLY scr_citiesexreg   DROP CONSTRAINT pk_scr_citiesexreg CASCADE;




--------------------------------
--   Vistas DE INVESSICRES   ---
--------------------------------
DROP VIEW scr_lista_oficinas;
DROP VIEW scr_lista_usuarios;
DROP VIEW scr_users;
DROP VIEW scr_orgs_eu;
DROP VIEW scr_orgs_ct;
DROP VIEW scr_orgs_gl;
DROP VIEW scr_typeadm_eu;
DROP VIEW scr_typeadm_ct;
DROP VIEW scr_typeadm_gl;
DROP VIEW scr_ofic_eu;
DROP VIEW scr_ofic_ct;
DROP VIEW scr_ofic_gl;
DROP VIEW scr_ca_eu;
DROP VIEW scr_ca_ct;
DROP VIEW scr_ca_gl;
DROP VIEW scr_tt_eu;
DROP VIEW scr_tt_ct;
DROP VIEW scr_tt_gl;
DROP VIEW scr_reports_eu;
DROP VIEW scr_reports_ct;
DROP VIEW scr_reports_gl;

--------------------------------
--   Vistas DE InvesDoc   ---
--------------------------------
DROP VIEW idocarchhdr_eu;
DROP VIEW idocarchhdr_ct;
DROP VIEW idocarchhdr_gl;

--------------------------------
--   TABLAS DE INVESSICRES   ---
--------------------------------
DROP TABLE scr_actws;
DROP TABLE scr_address;
DROP TABLE scr_addrtel;
DROP TABLE scr_bookadmin;
DROP TABLE scr_bookasoc;
DROP TABLE scr_bookofic;
DROP TABLE scr_ca CASCADE;
DROP TABLE scr_caadm;
DROP TABLE scr_cadocs;
DROP TABLE scr_caofic;
DROP TABLE scr_cities;
DROP TABLE scr_cntcentral;
DROP TABLE scr_cntlocal;
DROP TABLE scr_cntoficina;
DROP TABLE scr_cntordinal;
DROP TABLE scr_cntrefcierre;
DROP TABLE scr_configuration;
DROP TABLE scr_caaux;
DROP TABLE scr_bookrepository;
DROP TABLE scr_booktypeconfig;
DROP TABLE scr_bookconfig;
DROP TABLE scr_contador;
DROP TABLE scr_country;
DROP TABLE scr_deltas;
DROP TABLE scr_dirofic;
DROP TABLE scr_dirorgs;
DROP TABLE scr_distaccept;
DROP TABLE scr_distlist;
DROP TABLE scr_distreg;
DROP TABLE scr_distregstate;
DROP TABLE scr_distribucion_actual;
DROP TABLE scr_dom;
DROP TABLE scr_entregsnd;
DROP TABLE scr_expcnts;
DROP TABLE scr_exptasks;
DROP TABLE scr_fldaccperms;
DROP TABLE scr_fldpermarch;
DROP TABLE scr_fldpermuser;
DROP TABLE scr_genperms;
DROP TABLE scr_lastconnection;
DROP TABLE scr_expfilters;
DROP TABLE scr_lastregister;
DROP TABLE scr_ofic CASCADE;
DROP TABLE scr_orgs CASCADE;
DROP TABLE scr_lockrelations;
DROP TABLE scr_modifreg;
DROP TABLE scr_usrident;
DROP TABLE scr_nackrecvmsg;
DROP TABLE scr_nackrecvreg;
DROP TABLE scr_nacksendmsg;
DROP TABLE scr_nacksendreg;
DROP TABLE scr_oficadm;
DROP TABLE scr_pageinfo;
DROP TABLE scr_pagerepository;
DROP TABLE scr_pagetype;
DROP TABLE scr_pfis;
DROP TABLE scr_pinfo;
DROP TABLE scr_pjur;
DROP TABLE scr_privorgs;
DROP TABLE scr_procreg;
DROP TABLE scr_prov;
DROP TABLE scr_recvmsg;
DROP TABLE scr_recvreg;
DROP TABLE scr_regasoc;
DROP TABLE scr_regasocex;
DROP TABLE scr_regint;
DROP TABLE scr_regpdocs;
DROP TABLE scr_regstate;
DROP TABLE scr_relations;
DROP TABLE scr_reportarch;
DROP TABLE scr_reportofic;
DROP TABLE scr_reportperf;
DROP TABLE scr_reports CASCADE;
DROP TABLE scr_repository;
DROP TABLE scr_sendmsg;
DROP TABLE scr_sendreg;
DROP TABLE scr_sharedfiles;
DROP TABLE scr_tmzofic;
DROP TABLE scr_translatedfmt;
DROP TABLE scr_tt CASCADE;
DROP TABLE scr_typeaddress;
DROP TABLE scr_typeadm CASCADE;
DROP TABLE scr_typedoc;
DROP TABLE scr_typeofic;
DROP TABLE scr_userconfig;
DROP TABLE scr_userfilter;
DROP TABLE scr_usrloc;
DROP TABLE scr_usrofic;
DROP TABLE scr_usrperms;
DROP TABLE scr_valdate;
DROP TABLE scr_valnum;
DROP TABLE scr_valstr;
DROP TABLE scr_ws;
DROP TABLE scr_unitadm;
DROP TABLE scr_lockitems;
DROP TABLE scr_versionitems;
DROP TABLE scr_repositoryconf CASCADE;
DROP TABLE scr_repobooktype CASCADE;
DROP TABLE scr_doclocator;

DROP TABLE scr_oficlang;
DROP TABLE scr_calang;
DROP TABLE scr_orgslang;
DROP TABLE scr_reportslang;
DROP TABLE scr_ttlang;
DROP TABLE scr_typeadmlang;
DROP TABLE scr_bookslang;

--------------------------------
--   TABLAS DE ARCHIVADORES  ---
--------------------------------
DROP TABLE a1clfh;
DROP TABLE a1clfhx;
DROP TABLE a1doch;
DROP TABLE a1dochx;
DROP TABLE a1fdrh;
DROP TABLE a1pageh;
DROP TABLE a1pagehx;
DROP TABLE a1sf;
DROP TABLE a1xnid;
DROP TABLE a2clfh;
DROP TABLE a2clfhx;
DROP TABLE a2doch;
DROP TABLE a2dochx;
DROP TABLE a2fdrh;
DROP TABLE a2pageh;
DROP TABLE a2pagehx;
DROP TABLE a2sf;
DROP TABLE a2xnid;
DROP TABLE a3clfh;
DROP TABLE a3clfhx;
DROP TABLE a3doch;
DROP TABLE a3dochx;
DROP TABLE a3fdrh;
DROP TABLE a3pageh;
DROP TABLE a3pagehx;
DROP TABLE a3sf;
DROP TABLE a3xnid;
DROP TABLE a4clfh;
DROP TABLE a4clfhx;
DROP TABLE a4doch;
DROP TABLE a4dochx;
DROP TABLE a4fdrh;
DROP TABLE a4pageh;
DROP TABLE a4pagehx;
DROP TABLE a4sf;
DROP TABLE a4xnid;
DROP TABLE a1xf;
DROP TABLE a2xf;
DROP TABLE a3xf;
DROP TABLE a4xf;

--------------------------------
--    TABLAS DE INVESDOC     ---
--------------------------------
DROP TABLE idocappevent;
DROP TABLE idocarchhdr CASCADE;
DROP TABLE idocautonumctlg;
DROP TABLE idoccompusg;
DROP TABLE idocdatnode;
DROP TABLE idocbtblctlg;
DROP TABLE idocdirhdr;
DROP TABLE idocfdrlink;
DROP TABLE idocfdrlinkx;
DROP TABLE idocfdrstat;
DROP TABLE idocfiauxtblctlg;
DROP TABLE idocglbdoch;
DROP TABLE idocnextid;
DROP TABLE idocpreffmt;
DROP TABLE idocprefwfmt;
DROP TABLE idocrptauxtbl;
DROP TABLE idocssnextid;
DROP TABLE idocxnextid;
DROP TABLE idocsrvstatedet;
DROP TABLE idocsrvstatehdr;
DROP TABLE idocvtblctlg;
DROP TABLE idocpageanns;
DROP TABLE idocwmacro;
DROP TABLE idocarchdet;
DROP TABLE idocdbctlg;
DROP TABLE idocdbinfo;
DROP TABLE idocfmt;
DROP TABLE idocmacro;
DROP TABLE idocpict;
DROP TABLE idocrpt;
DROP TABLE itdsmsnextid;
DROP TABLE itdsmssess;
DROP TABLE iusercurrcnt;
DROP TABLE iuserdepthdr;
DROP TABLE iusergenperms;
DROP TABLE iusergrouphdr;
DROP TABLE iusergroupuser;
DROP TABLE iuserldapgrphdr;
DROP TABLE iuserldapuserhdr;
DROP TABLE iusernextid;
DROP TABLE iuserobjhdr;
DROP TABLE iuserobjperm;
DROP TABLE iuserremuser;
DROP TABLE iuseruserhdr;
DROP TABLE iuserusermap;
DROP TABLE iuseruserpwds;
DROP TABLE iuserusersys;
DROP TABLE iuserusertype;
DROP TABLE iuserlicences;
DROP TABLE ivolactdir;
DROP TABLE ivolarchlist;
DROP TABLE ivolfilefts;
DROP TABLE ivolfilehdr;
DROP TABLE ivollisthdr;
DROP TABLE ivollistvol;
DROP TABLE ivolnextid;
DROP TABLE ivolvoltbl;
DROP TABLE ivolrephdr;
DROP TABLE ivolvolhdr;


--------------------------------
--    TABLAS DE SICRES3     ---
--------------------------------

drop table scr_tramunit;
drop sequence scr_tramunit_id_seq;

drop table scr_entityreg;
drop sequence scr_entityreg_id_seq;

drop table scr_exregstate;
drop sequence scr_exregstate_id_seq;

drop table scr_exreg;
drop sequence scr_exreg_id_seq;

drop table scr_exregaccept;
drop sequence scr_exregaccept_id_seq;

drop table scr_provexreg;
drop table scr_citiesexreg;

drop table scr_attachment_sign_info;
drop sequence scr_attachment_sign_info_seq;

drop table scr_attachment;
drop sequence scr_attachment_seq;

drop table scr_repre;

drop TABLE scr_attachment_validity_type;

drop table scr_attachment_document_type;
DROP SEQUENCE scr_seqcnt;