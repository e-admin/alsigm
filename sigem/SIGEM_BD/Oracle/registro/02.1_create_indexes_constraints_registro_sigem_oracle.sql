
-----------------
--   INDICES   --
-----------------

-- TABLA scr_actws
CREATE UNIQUE INDEX actws_idx1 ON scr_actws (idpuesto);

-- TABLA scr_deltas
CREATE UNIQUE INDEX deltas_idx1 ON scr_deltas (idcambio);

-- TABLA scr_address
CREATE INDEX scr_address1 ON scr_address (id_person);

-- TABLA scr_addrtel
CREATE UNIQUE INDEX scr_addrtel0 ON scr_addrtel (id);

-- TABLA scr_bookadmin
CREATE INDEX scr_bookadmin0 ON scr_bookadmin (idbook);
CREATE UNIQUE INDEX scr_bookadmin1 ON scr_bookadmin (idbook, iduser);

-- TABLA scr_bookasoc
CREATE INDEX scr_bookasoc0 ON scr_bookasoc (id_book);

-- TABLA scr_bookconfig
CREATE UNIQUE INDEX scr_bookconfig0 ON scr_bookconfig (bookid);

-- TABLA scr_bookofic
CREATE UNIQUE INDEX scr_bookofic0 ON scr_bookofic (id);
CREATE INDEX scr_bookofic1 ON scr_bookofic (id_book);
CREATE INDEX scr_bookofic2 ON scr_bookofic (id_ofic);

-- TABLA scr_booktypeconfig
CREATE UNIQUE INDEX scr_booktypeconfig ON scr_booktypeconfig (booktype);

-- TABLA scr_caadm
CREATE UNIQUE INDEX scr_caadm0 ON scr_caadm (id);
CREATE INDEX scr_caadm1 ON scr_caadm (iduser);
CREATE INDEX scr_caadm2 ON scr_caadm (idca);

-- TABLA scr_cadocs
CREATE UNIQUE INDEX scr_cadocsidx1 ON scr_cadocs (id);
CREATE INDEX scr_cadocsidx2 ON scr_cadocs (id_matter);

-- TABLA scr_caofic
CREATE UNIQUE INDEX scr_caoficidx1 ON scr_caofic (id);
CREATE INDEX scr_caoficidx2 ON scr_caofic (id_matter);

-- TABLA scr_cities
CREATE UNIQUE INDEX scr_cities0 ON scr_cities (id);
CREATE INDEX scr_cities1 ON scr_cities (name);
CREATE INDEX scr_cities2 ON scr_cities (id_prov);

-- TABLA scr_dirofic
CREATE UNIQUE INDEX scr_dirofic0 ON scr_dirofic (id_ofic);

-- TABLA scr_dirorgs
CREATE UNIQUE INDEX scr_dirorgs0 ON scr_dirorgs (id_orgs);

-- TABLA scr_distaccept
CREATE INDEX scr_distaccept0 ON scr_distaccept (bookid);
CREATE INDEX scr_distaccept1 ON scr_distaccept (regid);
CREATE INDEX scr_distaccept2 ON scr_distaccept (officeid);

-- TABLA scr_distlist
CREATE INDEX scr_distlist0 ON scr_distlist (id);
CREATE INDEX scr_distlist1 ON scr_distlist (id_orgs);

-- TABLA scr_dom
CREATE UNIQUE INDEX scr_dom0 ON scr_dom (id);

-- TABLA scr_entregsnd
CREATE UNIQUE INDEX scr_entregsnd0 ON scr_entregsnd (id_entreg);

-- TABLA scr_expcnts
CREATE UNIQUE INDEX scr_expcnts0 ON scr_expcnts (id);
CREATE UNIQUE INDEX scr_expcnts1 ON scr_expcnts (id_task);

-- TABLA scr_fldaccperms
CREATE UNIQUE INDEX scr_fldaccperms0 ON scr_fldaccperms (archivetype, idfld);

-- TABLA scr_fldpermarch
CREATE UNIQUE INDEX scr_fldpermarch0 ON scr_fldpermarch (idarchive, idfld);

-- TABLA scr_fldpermuser
CREATE UNIQUE INDEX scr_fldpermuser0 ON scr_fldpermuser (idarchive, idfld);

-- TABLA scr_genperms
CREATE UNIQUE INDEX scr_genperms0 ON scr_genperms (id);

-- TABLA scr_lastregister
CREATE INDEX scr_lastregister0 ON scr_lastregister (bookid);
CREATE INDEX scr_lastregister1 ON scr_lastregister (userid);

-- TABLA scr_lockrelations
CREATE INDEX scr_lockrelations0 ON scr_lockrelations (typebook);
CREATE INDEX scr_lockrelations1 ON scr_lockrelations (typerel);
CREATE INDEX scr_lockrelations2 ON scr_lockrelations (idofic);

-- TABLA scr_modifreg
CREATE UNIQUE INDEX scr_modifreg0 ON scr_modifreg (id);

-- TABLA scr_modifreg
CREATE INDEX scr_modifreg1 ON scr_modifreg (modif_date);
CREATE INDEX scr_modifreg2 ON scr_modifreg (num_reg);
CREATE INDEX scr_modifreg3 ON scr_modifreg (id_fld);

-- TABLA scr_ofic
CREATE UNIQUE INDEX scr_ofic1 ON scr_ofic (code);
CREATE INDEX scr_ofic2 ON scr_ofic (creation_date);
CREATE INDEX scr_ofic3 ON scr_ofic (disable_date);
CREATE INDEX scr_ofic4 ON scr_ofic (id_orgs);
CREATE INDEX scr_ofic5 ON scr_ofic (deptid);

-- TABLA scr_oficadm
CREATE UNIQUE INDEX scr_oficadm0 ON scr_oficadm (id);
CREATE INDEX scr_oficadm1 ON scr_oficadm (iduser);
CREATE INDEX scr_oficadm2 ON scr_oficadm (idofic);

-- TABLA scr_orgs
CREATE UNIQUE INDEX scr_orgs1 ON scr_orgs (code);
CREATE INDEX scr_orgs2 ON scr_orgs (id_father);
CREATE INDEX scr_orgs3 ON scr_orgs (acron);
CREATE INDEX scr_orgs4 ON scr_orgs (creation_date, disable_date);
CREATE INDEX scr_orgs5 ON scr_orgs (id_father, type);

-- TABLA scr_pageinfo
CREATE INDEX scr_pageinfo0 ON scr_pageinfo (bookid);
CREATE INDEX scr_pageinfo1 ON scr_pageinfo (regid);
CREATE INDEX scr_pageinfo2 ON scr_pageinfo (pageid);

-- TABLA scr_pinfo
CREATE INDEX scr_pinfo0 ON scr_pinfo (id);
CREATE INDEX scr_pinfo1 ON scr_pinfo (officeid);

-- TABLA scr_prov
CREATE UNIQUE INDEX scr_prov0 ON scr_prov (id);
CREATE INDEX scr_prov1 ON scr_prov (name);

-- TABLA scr_regasoc
CREATE UNIQUE INDEX scr_regasoc0 ON scr_regasoc (id);
CREATE INDEX scr_regasoc1 ON scr_regasoc (id_archprim);
CREATE INDEX scr_regasoc2 ON scr_regasoc (id_fdrprim);

-- TABLA scr_regasocex
CREATE UNIQUE INDEX scr_regasocex0 ON scr_regasocex (id_asoc);

-- TABLA scr_regint
CREATE UNIQUE INDEX scr_regint0 ON scr_regint (id);
CREATE INDEX scr_regint1 ON scr_regint (id_arch);
CREATE INDEX scr_regint2 ON scr_regint (id_fdr);
CREATE INDEX scr_regint3 ON scr_regint (name);

-- TABLA scr_regpdocs
CREATE UNIQUE INDEX scr_regpdocs0 ON scr_regpdocs (id);
CREATE INDEX scr_regpdocs1 ON scr_regpdocs (id_arch);
CREATE INDEX scr_regpdocs2 ON scr_regpdocs (id_fdr);

-- TABLA scr_regstate
CREATE UNIQUE INDEX scr_regstate0 ON scr_regstate (id);
CREATE INDEX scr_regstate1 ON scr_regstate (idarchreg);

-- TABLA scr_relations
CREATE INDEX scr_relations0 ON scr_relations (typebook);
CREATE INDEX scr_relations1 ON scr_relations (typerel);
CREATE INDEX scr_relations2 ON scr_relations (relyear);
CREATE INDEX scr_relations3 ON scr_relations (relmonth);
CREATE INDEX scr_relations4 ON scr_relations (relday);
CREATE INDEX scr_relations5 ON scr_relations (idofic);
CREATE INDEX scr_relations6 ON scr_relations (idunit);

-- TABLA scr_typeadm
CREATE UNIQUE INDEX scr_typeadm1 ON scr_typeadm (code);

-- TABLA scr_userfilter
CREATE INDEX scr_userfilter0 ON scr_userfilter (idarch);
CREATE INDEX scr_userfilter1 ON scr_userfilter (iduser);
CREATE UNIQUE INDEX scr_userfilter2 ON scr_userfilter (idarch, iduser, type_obj);

-- TABLA scr_usrident
CREATE UNIQUE INDEX scr_usrident0 ON scr_usrident (userid);

-- TABLA scr_usrloc
CREATE UNIQUE INDEX scr_usrloc0 ON scr_usrloc (userid);

-- TABLA scr_usrofic
CREATE UNIQUE INDEX scr_usrofic0 ON scr_usrofic (id);
CREATE INDEX scr_usrofic1 ON scr_usrofic (iduser);
CREATE INDEX scr_usrofic2 ON scr_usrofic (idofic);

-- TABLA scr_usrperms
CREATE UNIQUE INDEX scr_usrperms0 ON scr_usrperms (id_usr);

-- TABLA scr_valdate
CREATE INDEX scr_valdate0 ON scr_valdate (id);

-- TABLA scr_valnum
CREATE INDEX scr_valnum0 ON scr_valnum (id);

-- TABLA scr_valstr
CREATE INDEX scr_valstr0 ON scr_valstr (id);

-- TABLA scr_ws
CREATE UNIQUE INDEX scr_ws0 ON scr_ws (id);
CREATE UNIQUE INDEX scr_ws1 ON scr_ws (code);

-- TABLA scr_bookrepository
CREATE UNIQUE INDEX scrbookrepository0 ON scr_bookrepository (bookid);

-- TABLA scr_ca
CREATE UNIQUE INDEX scrca1 ON scr_ca (code);

-- TABLA scr_caaux
CREATE UNIQUE INDEX scrcaaux0 ON scr_caaux (id);
CREATE INDEX scrcaaux1 ON scr_caaux (id_matter);

-- TABLA scr_distreg
CREATE UNIQUE INDEX scrdistreg0 ON scr_distreg (id);
CREATE INDEX scrdistreg1 ON scr_distreg (id_arch);
CREATE INDEX scrdistreg2 ON scr_distreg (id_fdr);
CREATE INDEX scrdistreg3 ON scr_distreg (type_dest);
CREATE INDEX scrdistreg4 ON scr_distreg (id_dest);
CREATE INDEX scrdistreg5 ON scr_distreg (state);
CREATE INDEX scrdistreg6 ON scr_distreg (state_date);
CREATE INDEX scrdistreg7 ON scr_distreg (type_orig);
CREATE INDEX scrdistreg8 ON scr_distreg (id_orig);

-- TABLA scr_distregstate
CREATE UNIQUE INDEX scrdistregstate0 ON scr_distregstate (id);
CREATE INDEX scrdistregstate1 ON scr_distregstate (id_dist);

-- TABLA scr_expfilters
CREATE UNIQUE INDEX screxpfilters0 ON scr_expfilters (id);
CREATE UNIQUE INDEX screxpfilters1 ON scr_expfilters (id_task);

-- TABLA scr_exptasks
CREATE UNIQUE INDEX screxptasks1 ON scr_exptasks (id);
CREATE INDEX screxptasks2 ON scr_exptasks (id_user);

-- TABLA scr_pagerepository
CREATE INDEX scrfdrrepository1 ON scr_pagerepository (fdrid);
CREATE INDEX scrfdrrepository2 ON scr_pagerepository (pageid);

-- TABLA scr_lastconnection
CREATE UNIQUE INDEX scrlastconnection0 ON scr_lastconnection (iduser);

-- TABLA scr_nackrecvmsg
CREATE UNIQUE INDEX scrnackrecvmsg0 ON scr_nackrecvmsg (id);
CREATE UNIQUE INDEX scrnackrecvreg0 ON scr_nackrecvreg (id);
CREATE UNIQUE INDEX scrnacksendmsg0 ON scr_nacksendmsg (id);
CREATE UNIQUE INDEX scrnacksendreg0 ON scr_nacksendreg (id);

-- TABLA scr_pagerepository
CREATE INDEX scrpagerepository0 ON scr_pagerepository (bookid);

-- TABLA scr_pfis
CREATE UNIQUE INDEX scrpfis0 ON scr_pfis (id);
CREATE INDEX scrpfis1 ON scr_pfis (nif);
CREATE INDEX scrpfis2 ON scr_pfis (first_name);
CREATE INDEX scrpfis3 ON scr_pfis (second_name);
CREATE INDEX scrpfis4 ON scr_pfis (surname);

-- TABLA scr_pjur
CREATE UNIQUE INDEX scrpjur0 ON scr_pjur (id);
CREATE INDEX scrpjur1 ON scr_pjur (cif);
CREATE INDEX scrpjur2 ON scr_pjur (name);

-- TABLA scr_privorgs
CREATE UNIQUE INDEX scrprivorgs0 ON scr_privorgs (id);
CREATE INDEX scrprivorgs1 ON scr_privorgs (idorgs);
CREATE INDEX scrprivorgs2 ON scr_privorgs (idofic);

-- TABLA scr_procreg
CREATE UNIQUE INDEX scrprocreg0 ON scr_procreg (id);
CREATE INDEX scrprocreg1 ON scr_procreg (id_dist);

-- TABLA scr_recvmsg
CREATE UNIQUE INDEX scrrecvmsg0 ON scr_recvmsg (id);
CREATE UNIQUE INDEX scrrecvmsg1 ON scr_recvmsg (file_name);
CREATE UNIQUE INDEX scrrecvreg0 ON scr_recvreg (id);

-- TABLA scr_reports
CREATE UNIQUE INDEX scrreport0 ON scr_reports(id);
CREATE INDEX scrreport1 ON scr_reports(type_report);
CREATE INDEX scrreport2 ON scr_reports(type_arch);




-- TABLA scr_reportarch
CREATE UNIQUE INDEX scrreportarc0 ON scr_reportarch (id);
CREATE INDEX scrreportarch1 ON scr_reportarch (id_report);

-- TABLA scr_reportofic
CREATE UNIQUE INDEX scrreportofic0 ON scr_reportofic (id);
CREATE INDEX scrreportofic1 ON scr_reportofic (id_report);

-- TABLA scr_reportperf
CREATE UNIQUE INDEX scrreportperf0 ON scr_reportperf (id);
CREATE INDEX scrreportperf1 ON scr_reportperf (id_report);

-- TABLA scr_repository
CREATE UNIQUE INDEX scrrepository0 ON scr_repository (id);

-- TABLA scr_sendmsg
CREATE INDEX scrsendmsg0 ON scr_sendmsg (id);

-- TABLA scr_sendreg
CREATE UNIQUE INDEX scrsendreg0 ON scr_sendreg (id);
CREATE INDEX scrsendreg1 ON scr_sendreg (id_msg);
CREATE INDEX scrsendreg2 ON scr_sendreg (rcv_state);

-- TABLA scr_sharedfiles
CREATE INDEX scrsharedfiles0 ON scr_sharedfiles (fileid);

-- TABLA scr_translatedfmt
CREATE UNIQUE INDEX scrtransfmt0 ON scr_translatedfmt (id);
CREATE INDEX scrtransfmt1 ON scr_translatedfmt (langid);
CREATE INDEX scrtransfmt2 ON scr_translatedfmt (spa_text);

-- TABLA scr_userconfig
CREATE UNIQUE INDEX scruserconfig0 ON scr_userconfig (userid);

-- TABLA scr_contador
CREATE UNIQUE INDEX tablacntidx ON scr_contador (tablaid);

-- TABLA scr_unitadm
CREATE INDEX scr_unitadm0 ON scr_unitadm (userid);
CREATE INDEX scr_unitadm1 ON scr_unitadm (objid);

-- TABLA scr_lockitems
CREATE INDEX scrlockitems0 ON scr_lockitems (objid);
CREATE INDEX scrlockitems1 ON scr_lockitems (userid);

-- TABLA scr_versionitems
CREATE INDEX scrversionitems0 ON scr_versionitems (code);
CREATE INDEX scrversionitems1 ON scr_versionitems (objid);

-- TABLA scr_orgslang
CREATE INDEX scr_orgslang0 ON scr_orgslang(id);
CREATE UNIQUE INDEX scr_orgslang1 ON scr_orgslang(id,language);

-- TABLA scr_typeadmlang
CREATE INDEX scr_typeadmlang0 ON scr_typeadmlang(id);
CREATE UNIQUE INDEX scr_typeadmlang1 ON scr_typeadmlang(id,language);

-- TABLA scr_oficlang
CREATE INDEX scr_oficlang0 ON scr_oficlang(id);
CREATE UNIQUE INDEX scr_oficlang1 ON scr_oficlang (id,language);

-- TABLA scr_calang
CREATE INDEX scr_calang0 ON scr_calang(id);
CREATE UNIQUE INDEX scr_calang1 ON scr_calang(id,language);

-- TABLA scr_ttlang
CREATE INDEX scr_ttlang0 ON scr_ttlang (id);
CREATE UNIQUE INDEX scr_ttlang1 ON scr_ttlang (id,language);

-- TABLA scr_reportslang
CREATE INDEX scr_reportslang0 ON scr_reportslang (id);
CREATE UNIQUE INDEX scr_reportslang1 ON scr_reportslang (id,language);

-- TABLA scr_bookslang
CREATE INDEX scr_bookslang0 ON scr_bookslang (id);
CREATE UNIQUE INDEX scr_bookslang1 ON scr_bookslang (id,language);

-- TABLA scr_repositoryconf
--CREATE INDEX scr_repoconf0 ON scr_repositoryconf (id);
CREATE UNIQUE INDEX scr_repoconf1 ON scr_repositoryconf (id);

-- TABLA scr_repobooktype
--CREATE INDEX scr_repbooktype0 ON scr_repobooktype (book_type);
CREATE UNIQUE INDEX scr_repbooktype1 ON scr_repobooktype (book_type);

-- TABLA scr_doclocator
CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator (locator);


-----------------
-- CONSTRAINTS --
-----------------

-- TABLA scr_address
ALTER TABLE scr_address ADD CONSTRAINT pk_scr_address0 PRIMARY KEY (id);

-- TABLA scr_ca
ALTER TABLE scr_ca ADD CONSTRAINT pk_scr_ca PRIMARY KEY (id);

-- TABLA scr_ofic
ALTER TABLE scr_ofic ADD CONSTRAINT pk_scr_ofic PRIMARY KEY (id);

-- TABLA scr_orgs
ALTER TABLE scr_orgs ADD CONSTRAINT pk_scr_orgs PRIMARY KEY (id);

-- TABLA scr_typeaddress
ALTER TABLE scr_typeaddress ADD CONSTRAINT pk_scr_typeaddress PRIMARY KEY (id);

-- TABLA scr_typeadm
ALTER TABLE scr_typeadm ADD CONSTRAINT pk_scr_typeadm PRIMARY KEY (id);

-- TABLA scr_typedoc
ALTER TABLE scr_typedoc ADD CONSTRAINT pk_scr_typedoc0 PRIMARY KEY (id);

-- TABLA scr_typeofic
ALTER TABLE scr_typeofic ADD CONSTRAINT pk_scr_typeofic PRIMARY KEY (id);

-- TABLA scr_addrtel
ALTER TABLE scr_addrtel ADD CONSTRAINT fk_scr_addrtel0 FOREIGN KEY (type) REFERENCES scr_typeaddress(id);

-- TABLA scr_caofic
ALTER TABLE scr_caofic ADD CONSTRAINT fk_scr_caofic0 FOREIGN KEY (id_ofic) REFERENCES scr_ofic(id);

-- TABLA scr_ofic
ALTER TABLE scr_ofic ADD CONSTRAINT fk_scr_ofic0 FOREIGN KEY (id_orgs) REFERENCES scr_orgs(id);
ALTER TABLE scr_ofic ADD CONSTRAINT fk_scr_ofic1 FOREIGN KEY (type) REFERENCES scr_typeofic(id);

-- TABLA scr_orgs
ALTER TABLE scr_orgs ADD CONSTRAINT fk_scr_orgs0 FOREIGN KEY (type) REFERENCES scr_typeadm(id);

-- TABLA scr_pfis
ALTER TABLE scr_pfis ADD CONSTRAINT fk_scr_pfis0 FOREIGN KEY (type_doc) REFERENCES scr_typedoc(id);

-- TABLA scr_pjur
ALTER TABLE scr_pjur ADD CONSTRAINT fk_scr_pjur0 FOREIGN KEY (type_doc) REFERENCES scr_typedoc(id);

-- TABLA scr_privorgs
ALTER TABLE scr_privorgs ADD CONSTRAINT fk_scr_privorgs0 FOREIGN KEY (idorgs) REFERENCES scr_orgs(id);

-- TABLA scr_recvmsg
ALTER TABLE scr_recvmsg ADD CONSTRAINT fk_scr_recvmsg0 FOREIGN KEY (sender) REFERENCES scr_orgs(id);
ALTER TABLE scr_recvmsg ADD CONSTRAINT fk_scr_recvmsg1 FOREIGN KEY (destination) REFERENCES scr_orgs(id);

-- TABLA scr_reportofic
ALTER TABLE scr_reportofic ADD CONSTRAINT fk_scr_reportofic0 FOREIGN KEY (id_ofic) REFERENCES scr_ofic(id);

-- TABLA scr_sendmsg
ALTER TABLE scr_sendmsg ADD CONSTRAINT fk_scr_sendmsg0 FOREIGN KEY (sender) REFERENCES scr_orgs(id);
ALTER TABLE scr_sendmsg ADD CONSTRAINT fk_scr_sendmsg1 FOREIGN KEY (destination) REFERENCES scr_orgs(id);

-- TABLA scr_sendreg
ALTER TABLE scr_sendreg ADD CONSTRAINT fk_scr_sendreg0 FOREIGN KEY (id_entreg_dest) REFERENCES scr_orgs(id);

-- TABLA scr_usrofic
ALTER TABLE scr_usrofic ADD CONSTRAINT fk_scr_usrofic0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_ws
ALTER TABLE scr_ws ADD CONSTRAINT fk_scr_ws0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_caadm
ALTER TABLE scr_caadm ADD CONSTRAINT fk_scrcaadm0 FOREIGN KEY (idca) REFERENCES scr_ca(id);

-- TABLA scr_lockrelations
ALTER TABLE scr_lockrelations ADD CONSTRAINT fk_scrlckrel0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_oficadm
ALTER TABLE scr_oficadm ADD CONSTRAINT fk_scroficadm0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_relations
ALTER TABLE scr_relations ADD CONSTRAINT fk_scrrels0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);
ALTER TABLE scr_relations ADD CONSTRAINT fk_scrrels1 FOREIGN KEY (idunit) REFERENCES scr_orgs(id);

--TABLA scr_pagetype
ALTER TABLE scr_pagetype ADD CONSTRAINT pk_pagetype PRIMARY KEY (id);

--TABLA scr_pagerepository
ALTER TABLE scr_pagerepository ADD CONSTRAINT fk_pagerepository0 FOREIGN KEY (id_pagetype) REFERENCES scr_pagetype(id);


-- TABLA scr_repositoryconf
ALTER TABLE scr_repositoryconf ADD CONSTRAINT pk_repositoryconf PRIMARY KEY (ID);

-- TABLA scr_repobooktype
ALTER TABLE scr_repobooktype ADD CONSTRAINT pk_repobooktype PRIMARY KEY (book_type);
ALTER TABLE scr_repobooktype ADD CONSTRAINT fk_repobooktype FOREIGN KEY (id_rep_conf) REFERENCES scr_repositoryconf(ID);

-- TABLA scr_prov
ALTER TABLE scr_prov ADD CONSTRAINT pk_prov PRIMARY KEY (id);

-- TABLA scr_cities
ALTER TABLE scr_cities ADD CONSTRAINT pk_cities PRIMARY KEY (id);

-- TABLA scr_distribucion_actual
ALTER TABLE scr_distribucion_actual ADD CONSTRAINT pk_scr_distribucion_actual PRIMARY KEY (id_dist);