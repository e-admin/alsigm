
-----------------------------------
-- FOREIGN KEYS ARCHIVADORES     --
-----------------------------------
ALTER TABLE SCR_ADDRTEL DROP CONSTRAINT FK_SCR_ADDRTEL0;
ALTER TABLE SCR_CAADM DROP CONSTRAINT FK_SCRCAADM0;
ALTER TABLE SCR_CAOFIC DROP CONSTRAINT FK_SCR_CAOFIC0;
ALTER TABLE SCR_LOCKRELATIONS DROP CONSTRAINT FK_SCRLCKREL0;
ALTER TABLE SCR_OFIC DROP CONSTRAINT FK_SCR_OFIC0;
ALTER TABLE SCR_OFIC DROP CONSTRAINT FK_SCR_OFIC1;
ALTER TABLE SCR_OFICADM DROP CONSTRAINT FK_SCROFICADM0;
ALTER TABLE SCR_ORGS DROP CONSTRAINT FK_SCR_ORGS0;
ALTER TABLE SCR_PFIS DROP CONSTRAINT FK_SCR_PFIS0;
ALTER TABLE SCR_PJUR DROP CONSTRAINT FK_SCR_PJUR0;
ALTER TABLE SCR_PRIVORGS DROP CONSTRAINT FK_SCR_PRIVORGS0;
ALTER TABLE SCR_RECVMSG DROP CONSTRAINT FK_SCR_RECVMSG0;
ALTER TABLE SCR_RECVMSG DROP CONSTRAINT FK_SCR_RECVMSG1;
ALTER TABLE SCR_RELATIONS DROP CONSTRAINT FK_SCRRELS0;
ALTER TABLE SCR_RELATIONS DROP CONSTRAINT FK_SCRRELS1;
ALTER TABLE SCR_REPORTOFIC DROP CONSTRAINT FK_SCR_REPORTOFIC0;
ALTER TABLE SCR_SENDMSG DROP CONSTRAINT FK_SCR_SENDMSG0;
ALTER TABLE SCR_SENDMSG DROP CONSTRAINT FK_SCR_SENDMSG1;
ALTER TABLE SCR_SENDREG DROP CONSTRAINT FK_SCR_SENDREG0;
ALTER TABLE SCR_USROFIC DROP CONSTRAINT FK_SCR_USROFIC0;
ALTER TABLE SCR_WS DROP CONSTRAINT FK_SCR_WS0;
ALTER TABLE SCR_PAGEREPOSITORY DROP CONSTRAINT fk_pagerepository0;

ALTER TABLE scr_entityreg DROP CONSTRAINT fk_entityofic ;
ALTER TABLE scr_tramunit DROP CONSTRAINT fk_tramunitgorgs  ;
ALTER TABLE scr_provexreg  DROP CONSTRAINT fk_provexregprov ;
ALTER TABLE scr_citiesexreg   DROP CONSTRAINT fk_citiesexregci ;

ALTER TABLE a2sf DROP CONSTRAINT fk_a2sf8;
ALTER TABLE a2sf DROP CONSTRAINT fk_a2sf7;
ALTER TABLE a2sf DROP CONSTRAINT fk_a2sf5;
ALTER TABLE a2sf DROP CONSTRAINT fk_a2sf12;
ALTER TABLE a1sf DROP CONSTRAINT fk_a1sf16;
ALTER TABLE a1sf DROP CONSTRAINT fk_a1sf8;
ALTER TABLE a1sf DROP CONSTRAINT fk_a1sf7;
ALTER TABLE a1sf DROP CONSTRAINT fk_a1sf5;
ALTER TABLE a1sf DROP CONSTRAINT fk_a1sf13;

ALTER TABLE scr_ca DROP CONSTRAINT pk_scr_ca;
ALTER TABLE scr_orgs DROP CONSTRAINT pk_scr_orgs;
ALTER TABLE scr_ofic DROP CONSTRAINT pk_scr_ofic;

ALTER TABLE scr_entityreg DROP CONSTRAINT pk_scr_entityreg;
ALTER TABLE scr_tramunit DROP CONSTRAINT pk_scr_tramunit;
ALTER TABLE scr_exreg DROP CONSTRAINT pk_scr_exreg;
ALTER TABLE scr_exregaccept DROP CONSTRAINT pk_scr_exregaccept;
ALTER TABLE scr_provexreg  DROP CONSTRAINT pk_scr_provexreg;
ALTER TABLE scr_citiesexreg   DROP CONSTRAINT pk_scr_citiesexreg;

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

--------------------------
--  Vistas DE InvesDoc ---
--------------------------
DROP VIEW idocarchhdr_eu;
DROP VIEW idocarchhdr_ct;
DROP VIEW idocarchhdr_gl;

---------------------------
-- TABLAS DE INVESSICRES --
---------------------------
DROP TABLE scr_actws;
DROP TABLE scr_address;
DROP TABLE scr_addrtel;
DROP TABLE scr_bookadmin;
DROP TABLE scr_bookasoc;
DROP TABLE scr_bookofic;
DROP TABLE scr_ca;
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
DROP TABLE scr_ofic;
DROP TABLE scr_orgs;
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
DROP TABLE scr_reports;
DROP TABLE scr_repository;
DROP TABLE scr_sendmsg;
DROP TABLE scr_sendreg;
DROP TABLE scr_sharedfiles;
DROP TABLE scr_tmzofic;
DROP TABLE scr_translatedfmt;
DROP TABLE scr_tt;
DROP TABLE scr_typeaddress;
DROP TABLE scr_typeadm;
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
DROP TABLE scr_repositoryconf;
DROP TABLE scr_repobooktype;
DROP TABLE scr_doclocator;

DROP TABLE scr_oficlang;
DROP TABLE scr_calang;
DROP TABLE scr_orgslang;
DROP TABLE scr_reportslang;
DROP TABLE scr_ttlang;
DROP TABLE scr_typeadmlang;
DROP TABLE scr_bookslang;

----------------------------
-- TABLAS DE ARCHIVADORES --
----------------------------
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

------------------------
-- TABLAS DE INVESDOC --
------------------------
DROP TABLE idocappevent;
DROP TABLE idocarchhdr;
DROP TABLE idocautonumctlg;
DROP TABLE idoccompusg;
DROP TABLE idocdatnode;
DROP TABLE idocdbctlg;
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
DROP TABLE idocbtblctlg;
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

DROP SEQUENCE SCR_SEQCNT;