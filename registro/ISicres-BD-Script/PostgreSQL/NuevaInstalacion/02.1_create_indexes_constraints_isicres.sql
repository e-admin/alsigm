----------------------------------
--         INDICES              --
----------------------------------

-- TABLA scr_actw
CREATE UNIQUE INDEX actws_idx1 ON scr_actws USING btree (idpuesto);

-- TABLA scr_deltas
CREATE UNIQUE INDEX deltas_idx1 ON scr_deltas USING btree (idcambio);

-- TABLA scr_address
CREATE INDEX scr_address1 ON scr_address USING btree (id_person);

-- TABLA scr_addrtel
CREATE UNIQUE INDEX scr_addrtel0 ON scr_addrtel USING btree (id);

-- TABLA scr_bookadmin
CREATE INDEX scr_bookadmin0 ON scr_bookadmin USING btree (idbook);
CREATE UNIQUE INDEX scr_bookadmin1 ON scr_bookadmin USING btree (idbook, iduser);

-- TABLA scr_bookasoc
CREATE INDEX scr_bookasoc0 ON scr_bookasoc USING btree (id_book);

-- TABLA scr_bookconfig
CREATE UNIQUE INDEX scr_bookconfig0 ON scr_bookconfig USING btree (bookid);

-- TABLA scr_bookofic
CREATE UNIQUE INDEX scr_bookofic0 ON scr_bookofic USING btree (id);
CREATE INDEX scr_bookofic1 ON scr_bookofic USING btree (id_book);
CREATE INDEX scr_bookofic2 ON scr_bookofic USING btree (id_ofic);

-- TABLA scr_booktypeconfig
CREATE UNIQUE INDEX scr_booktypeconfig0 ON scr_booktypeconfig USING btree (booktype);

-- TABLA scr_caadm
CREATE UNIQUE INDEX scr_caadm0 ON scr_caadm USING btree (id);
CREATE INDEX scr_caadm1 ON scr_caadm USING btree (iduser);
CREATE INDEX scr_caadm2 ON scr_caadm USING btree (idca);

-- TABLA scr_cadocs
CREATE UNIQUE INDEX scr_cadocsidx1 ON scr_cadocs USING btree (id);
CREATE INDEX scr_cadocsidx2 ON scr_cadocs USING btree (id_matter);

-- TABLA scr_caofic
CREATE UNIQUE INDEX scr_caoficidx1 ON scr_caofic USING btree (id);
CREATE INDEX scr_caoficidx2 ON scr_caofic USING btree (id_matter);

-- TABLA scr_cities
CREATE UNIQUE INDEX scr_cities0 ON scr_cities USING btree (id);
CREATE INDEX scr_cities1 ON scr_cities USING btree (name);
CREATE INDEX scr_cities2 ON scr_cities USING btree (id_prov);

-- TABLA scr_dirofic
CREATE UNIQUE INDEX scr_dirofic0 ON scr_dirofic USING btree (id_ofic);

-- TABLA scr_dirorgs
CREATE UNIQUE INDEX scr_dirorgs0 ON scr_dirorgs USING btree (id_orgs);

-- TABLA scr_distaccept
CREATE INDEX scr_distaccept0 ON scr_distaccept USING btree (bookid);
CREATE INDEX scr_distaccept1 ON scr_distaccept USING btree (regid);
CREATE INDEX scr_distaccept2 ON scr_distaccept USING btree (officeid);

-- TABLA scr_distlist
CREATE INDEX scr_distlist0 ON scr_distlist USING btree (id);
CREATE INDEX scr_distlist1 ON scr_distlist USING btree (id_orgs);

-- TABLA scr_dom
CREATE UNIQUE INDEX scr_dom0 ON scr_dom USING btree (id);

-- TABLA scr_entregsnd
CREATE UNIQUE INDEX scr_entregsnd0 ON scr_entregsnd USING btree (id_entreg);

-- TABLA scr_expcnts
CREATE UNIQUE INDEX scr_expcnts0 ON scr_expcnts USING btree (id);
CREATE UNIQUE INDEX scr_expcnts1 ON scr_expcnts USING btree (id_task);

-- TABLA scr_fldaccperms
CREATE UNIQUE INDEX scr_fldaccperms0 ON scr_fldaccperms USING btree (archivetype, idfld);

-- TABLA scr_fldpermarch
CREATE UNIQUE INDEX scr_fldpermarch0 ON scr_fldpermarch USING btree (idarchive, idfld);

-- TABLA scr_fldpermuser
CREATE UNIQUE INDEX scr_fldpermuser0 ON scr_fldpermuser USING btree (idarchive, idfld);

-- TABLA scr_genperms
CREATE UNIQUE INDEX scr_genperms0 ON scr_genperms USING btree (id);

-- TABLA scr_lastregister
CREATE INDEX scr_lastregister0 ON scr_lastregister USING btree (bookid);
CREATE INDEX scr_lastregister1 ON scr_lastregister USING btree (userid);

-- TABLA scr_lockrelations
CREATE INDEX scr_lockrelations0 ON scr_lockrelations USING btree (typebook);
CREATE INDEX scr_lockrelations1 ON scr_lockrelations USING btree (typerel);
CREATE INDEX scr_lockrelations2 ON scr_lockrelations USING btree (idofic);

-- TABLA scr_modifreg
CREATE UNIQUE INDEX scr_modifreg0 ON scr_modifreg USING btree (id);
CREATE INDEX scr_modifreg1 ON scr_modifreg USING btree (modif_date);
CREATE INDEX scr_modifreg2 ON scr_modifreg USING btree (num_reg);
CREATE INDEX scr_modifreg3 ON scr_modifreg USING btree (id_fld);

-- TABLA scr_ofic
CREATE UNIQUE INDEX scr_ofic1 ON scr_ofic USING btree (code);
CREATE INDEX scr_ofic2 ON scr_ofic USING btree (creation_date);
CREATE INDEX scr_ofic3 ON scr_ofic USING btree (disable_date);
CREATE INDEX scr_ofic4 ON scr_ofic USING btree (id_orgs);
CREATE INDEX scr_ofic5 ON scr_ofic USING btree (deptid);

-- TABLA scr_oficadm
CREATE UNIQUE INDEX scr_oficadm0 ON scr_oficadm USING btree (id);
CREATE INDEX scr_oficadm1 ON scr_oficadm USING btree (iduser);
CREATE INDEX scr_oficadm2 ON scr_oficadm USING btree (idofic);

-- TABLA scr_orgs
CREATE UNIQUE INDEX scr_orgs1 ON scr_orgs USING btree (code);
CREATE INDEX scr_orgs2 ON scr_orgs USING btree (id_father);
CREATE INDEX scr_orgs3 ON scr_orgs USING btree (acron);
CREATE INDEX scr_orgs4 ON scr_orgs USING btree (creation_date, disable_date);
CREATE INDEX scr_orgs5 ON scr_orgs USING btree (id_father, type);

-- TABLA scr_pageinfo
CREATE INDEX scr_pageinfo0 ON scr_pageinfo USING btree (bookid);
CREATE INDEX scr_pageinfo1 ON scr_pageinfo USING btree (regid);
CREATE INDEX scr_pageinfo2 ON scr_pageinfo USING btree (pageid);

-- TABLA scr_pinfo
CREATE INDEX scr_pinfo0 ON scr_pinfo USING btree (id);
CREATE INDEX scr_pinfo1 ON scr_pinfo USING btree (officeid);

-- TABLA scr_prov
CREATE UNIQUE INDEX scr_prov0 ON scr_prov USING btree (id);
CREATE INDEX scr_prov1 ON scr_prov USING btree (name);

-- TABLA scr_regasoc
CREATE UNIQUE INDEX scr_regasoc0 ON scr_regasoc USING btree (id);
CREATE INDEX scr_regasoc1 ON scr_regasoc USING btree (id_archprim);
CREATE INDEX scr_regasoc2 ON scr_regasoc USING btree (id_fdrprim);

-- TABLA scr_regasocex
CREATE UNIQUE INDEX scr_regasocex0 ON scr_regasocex USING btree (id_asoc);

-- TABLA scr_regint
CREATE UNIQUE INDEX scr_regint0 ON scr_regint USING btree (id);
CREATE INDEX scr_regint1 ON scr_regint USING btree (id_arch);
CREATE INDEX scr_regint2 ON scr_regint USING btree (id_fdr);
CREATE INDEX scr_regint3 ON scr_regint USING btree (name);

-- TABLA scr_regpdocs
CREATE UNIQUE INDEX scr_regpdocs0 ON scr_regpdocs USING btree (id);
CREATE INDEX scr_regpdocs1 ON scr_regpdocs USING btree (id_arch);
CREATE INDEX scr_regpdocs2 ON scr_regpdocs USING btree (id_fdr);

-- TABLA scr_regstate
CREATE UNIQUE INDEX scr_regstate0 ON scr_regstate USING btree (id);
CREATE INDEX scr_regstate1 ON scr_regstate USING btree (idarchreg);

-- TABLA scr_relations
CREATE INDEX scr_relations0 ON scr_relations USING btree (typebook);
CREATE INDEX scr_relations1 ON scr_relations USING btree (typerel);
CREATE INDEX scr_relations2 ON scr_relations USING btree (relyear);
CREATE INDEX scr_relations3 ON scr_relations USING btree (relmonth);
CREATE INDEX scr_relations4 ON scr_relations USING btree (relday);
CREATE INDEX scr_relations5 ON scr_relations USING btree (idofic);
CREATE INDEX scr_relations6 ON scr_relations USING btree (idunit);

-- TABLA scr_typeadm
CREATE UNIQUE INDEX scr_typeadm1 ON scr_typeadm USING btree (code);

-- TABLA scr_userfilter
CREATE INDEX scr_userfilter0 ON scr_userfilter USING btree (idarch);
CREATE INDEX scr_userfilter1 ON scr_userfilter USING btree (iduser);
CREATE UNIQUE INDEX scr_userfilter2 ON scr_userfilter USING btree (idarch, iduser, type_obj);

-- TABLA scr_usrident
CREATE UNIQUE INDEX scr_usrident0 ON scr_usrident USING btree (userid);

-- TABLA scr_usrloc
CREATE UNIQUE INDEX scr_usrloc0 ON scr_usrloc USING btree (userid);

-- TABLA scr_usrofic
CREATE UNIQUE INDEX scr_usrofic0 ON scr_usrofic USING btree (id);
CREATE INDEX scr_usrofic1 ON scr_usrofic USING btree (iduser);
CREATE INDEX scr_usrofic2 ON scr_usrofic USING btree (idofic);

-- TABLA scr_usrperms
CREATE UNIQUE INDEX scr_usrperms0 ON scr_usrperms USING btree (id_usr);

-- TABLA scr_valdate
CREATE INDEX scr_valdate0 ON scr_valdate USING btree (id);

-- TABLA scr_valnum
CREATE INDEX scr_valnum0 ON scr_valnum USING btree (id);

-- TABLA scr_valstr
CREATE INDEX scr_valstr0 ON scr_valstr USING btree (id);

-- TABLA scr_ws
CREATE UNIQUE INDEX scr_ws0 ON scr_ws USING btree (id);
CREATE UNIQUE INDEX scr_ws1 ON scr_ws USING btree (code);

-- TABLA scr_bookrepository
CREATE UNIQUE INDEX scrbookrepository0 ON scr_bookrepository USING btree (bookid);

-- TABLA scr_ca
CREATE UNIQUE INDEX scrca1 ON scr_ca USING btree (code);

-- TABLA scr_caaux
CREATE UNIQUE INDEX scrcaaux0 ON scr_caaux USING btree (id);
CREATE INDEX scrcaaux1 ON scr_caaux USING btree (id_matter);

-- TABLA scr_distreg
CREATE UNIQUE INDEX scrdistreg0 ON scr_distreg USING btree (id);
CREATE INDEX scrdistreg1 ON scr_distreg USING btree (id_arch);
CREATE INDEX scrdistreg2 ON scr_distreg USING btree (id_fdr);
CREATE INDEX scrdistreg3 ON scr_distreg USING btree (type_dest);
CREATE INDEX scrdistreg4 ON scr_distreg USING btree (id_dest);
CREATE INDEX scrdistreg5 ON scr_distreg USING btree (state);
CREATE INDEX scrdistreg6 ON scr_distreg USING btree (state_date);
CREATE INDEX scrdistreg7 ON scr_distreg USING btree (type_orig);
CREATE INDEX scrdistreg8 ON scr_distreg USING btree (id_orig);

-- TABLA scr_distregstate
CREATE UNIQUE INDEX scrdistregstate0 ON scr_distregstate USING btree (id);
CREATE INDEX scrdistregstate1 ON scr_distregstate USING btree (id_dist);

-- TABLA scr_expfilters
CREATE UNIQUE INDEX screxpfilters0 ON scr_expfilters USING btree (id);
CREATE UNIQUE INDEX screxpfilters1 ON scr_expfilters USING btree (id_task);

-- TABLA scr_screxptaks
CREATE UNIQUE INDEX screxptasks1 ON scr_exptasks USING btree (id);
CREATE INDEX screxptasks2 ON scr_exptasks USING btree (id_user);

-- TABLA scr_pagerepository
CREATE INDEX scrfdrrepository1 ON scr_pagerepository USING btree (fdrid);
CREATE INDEX scrfdrrepository2 ON scr_pagerepository USING btree (pageid);

-- TABLA scr_scrlastconnection
CREATE UNIQUE INDEX scrlastconnection0 ON scr_lastconnection USING btree (iduser);

-- TABLA scr_nackrecvmsg
CREATE UNIQUE INDEX scrnackrecvmsg0 ON scr_nackrecvmsg USING btree (id);
CREATE UNIQUE INDEX scrnackrecvreg0 ON scr_nackrecvreg USING btree (id);
CREATE UNIQUE INDEX scrnacksendmsg0 ON scr_nacksendmsg USING btree (id);
CREATE UNIQUE INDEX scrnacksendreg0 ON scr_nacksendreg USING btree (id);

-- TABLA scr_pagerepository
CREATE INDEX scrpagerepository0 ON scr_pagerepository USING btree (bookid);

-- TABLA scr_pfis
CREATE UNIQUE INDEX scrpfis0 ON scr_pfis USING btree (id);
CREATE INDEX scrpfis1 ON scr_pfis USING btree (nif);
CREATE INDEX scrpfis2 ON scr_pfis USING btree (first_name);
CREATE INDEX scrpfis3 ON scr_pfis USING btree (second_name);
CREATE INDEX scrpfis4 ON scr_pfis USING btree (surname);

-- TABLA scr_pjur
CREATE UNIQUE INDEX scrpjur0 ON scr_pjur USING btree (id);
CREATE INDEX scrpjur1 ON scr_pjur USING btree (cif);
CREATE INDEX scrpjur2 ON scr_pjur USING btree (name);

-- TABLA scr_privorgs
CREATE UNIQUE INDEX scrprivorgs0 ON scr_privorgs USING btree (id);
CREATE INDEX scrprivorgs1 ON scr_privorgs USING btree (idorgs);
CREATE INDEX scrprivorgs2 ON scr_privorgs USING btree (idofic);

-- TABLA scr_proceg
CREATE UNIQUE INDEX scrprocreg0 ON scr_procreg USING btree (id);
CREATE INDEX scrprocreg1 ON scr_procreg USING btree (id_dist);

-- TABLA scr_recvmsg
CREATE UNIQUE INDEX scrrecvmsg0 ON scr_recvmsg USING btree (id);
CREATE UNIQUE INDEX scrrecvmsg1 ON scr_recvmsg USING btree (file_name);
CREATE UNIQUE INDEX scrrecvreg0 ON scr_recvreg USING btree (id);

-- TABLA scr_reports
CREATE UNIQUE INDEX scrreport0 ON scr_reports USING btree (id);
CREATE INDEX scrreport1 ON scr_reports USING btree (type_report);
CREATE INDEX scrreport2 ON scr_reports USING btree (type_arch);


-- TABLA scr_reportarch
CREATE UNIQUE INDEX scrreportarc0 ON scr_reportarch USING btree (id);
CREATE INDEX scrreportarch1 ON scr_reportarch USING btree (id_report);

-- TABLA scr_reportofic
CREATE UNIQUE INDEX scrreportofic0 ON scr_reportofic USING btree (id);
CREATE INDEX scrreportofic1 ON scr_reportofic USING btree (id_report);

-- TABLA scr_reportperf
CREATE UNIQUE INDEX scrreportperf0 ON scr_reportperf USING btree (id);
CREATE INDEX scrreportperf1 ON scr_reportperf USING btree (id_report);

-- TABLA scr_repository
CREATE UNIQUE INDEX scrrepository0 ON scr_repository USING btree (id);

-- TABLA scr_sendmsg
CREATE INDEX scrsendmsg0 ON scr_sendmsg USING btree (id);

-- TABLA scr_sendreg
CREATE UNIQUE INDEX scrsendreg0 ON scr_sendreg USING btree (id);
CREATE INDEX scrsendreg1 ON scr_sendreg USING btree (id_msg);
CREATE INDEX scrsendreg2 ON scr_sendreg USING btree (rcv_state);

-- TABLA scr_sharedfiles
CREATE INDEX scrsharedfiles0 ON scr_sharedfiles USING btree (fileid);

-- TABLA scr_translatedfmt
CREATE UNIQUE INDEX scrtransfmt0 ON scr_translatedfmt USING btree (id);
CREATE INDEX scrtransfmt1 ON scr_translatedfmt USING btree (langid);
CREATE INDEX scrtransfmt2 ON scr_translatedfmt USING btree (spa_text);

-- TABLA scr_userconfig
CREATE UNIQUE INDEX scruserconfig0 ON scr_userconfig USING btree (userid);

-- TABLA scr_contador
CREATE UNIQUE INDEX tablacntidx ON scr_contador USING btree (tablaid);

-- TABLA scr_unitadm
CREATE INDEX scr_unitadm0 ON scr_unitadm USING btree (userid);
CREATE INDEX scr_unitadm1 ON scr_unitadm USING btree (objid);

-- TABLA scr_lockitems
CREATE INDEX scrlockitems0 ON scr_lockitems USING btree (objid);
CREATE INDEX scrlockitems1 ON scr_lockitems USING btree (userid);

-- TABLA scr_versionitems
CREATE INDEX scrversionitems0 ON scr_versionitems USING btree (code);
CREATE INDEX scrversionitems1 ON scr_versionitems USING btree (objid);

-- TABLA scr_orgslang
CREATE INDEX scr_orgslang0 ON scr_orgslang USING btree (id);
CREATE UNIQUE INDEX scr_orgslang1 ON scr_orgslang USING btree (id,language);

-- TABLA scr_typeadmlang
CREATE INDEX scr_typeadmlang0 ON scr_typeadmlang USING btree (id);
CREATE UNIQUE INDEX scr_typeadmlang1 ON scr_typeadmlang USING btree (id,language);

-- TABLA scr_oficlang
CREATE INDEX scr_oficlang0 ON scr_oficlang USING btree (id);
CREATE UNIQUE INDEX scr_oficlang1 ON scr_oficlang USING btree (id,language);

-- TABLA scr_calang
CREATE INDEX scr_calang0 ON scr_calang USING btree (id);
CREATE UNIQUE INDEX scr_calang1 ON scr_calang USING btree (id,language);

-- TABLA scr_ttlang
CREATE INDEX scr_ttlang0 ON scr_ttlang USING btree (id);
CREATE UNIQUE INDEX scr_ttlang1 ON scr_ttlang USING btree (id,language);

-- TABLA scr_reportslang
CREATE INDEX scr_reportslang0 ON scr_reportslang USING btree (id);
CREATE UNIQUE INDEX scr_reportslang1 ON scr_reportslang USING btree (id,language);

-- TABLA scr_bookslang
CREATE INDEX scr_bookslang0 ON scr_bookslang USING btree (id);
CREATE UNIQUE INDEX scr_bookslang1 ON scr_bookslang USING btree (id,language);

-- TABLA scr_repositoryconf
CREATE INDEX scr_repoconf0 ON scr_repositoryconf USING btree (id);
CREATE UNIQUE INDEX scr_repoconf1 ON scr_repositoryconf USING btree (id);

-- TABLA scr_repobooktype
CREATE INDEX scr_repbooktype0 ON scr_repobooktype USING btree (book_type);
CREATE UNIQUE INDEX scr_repbooktype1 ON scr_repobooktype USING btree (book_type);

-- TABLA scr_doclocator
CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator USING btree (locator);

----------------------------------
--       CONSTRAINTS            --
----------------------------------

-- TABLA scr_address
ALTER TABLE ONLY scr_address
    ADD CONSTRAINT pk_scr_address0 PRIMARY KEY (id);

-- TABLA scr_ca
ALTER TABLE ONLY scr_ca
    ADD CONSTRAINT pk_scr_ca PRIMARY KEY (id);

-- TABLA scr_ofic
ALTER TABLE ONLY scr_ofic
    ADD CONSTRAINT pk_scr_ofic PRIMARY KEY (id);

-- TABLA scr_orgs
ALTER TABLE ONLY scr_orgs
    ADD CONSTRAINT pk_scr_orgs PRIMARY KEY (id);

-- TABLA scr_typeaddress
ALTER TABLE ONLY scr_typeaddress
    ADD CONSTRAINT pk_scr_typeaddress0 PRIMARY KEY (id);

-- TABLA scr_typeadm
ALTER TABLE ONLY scr_typeadm
    ADD CONSTRAINT pk_scr_typeadm PRIMARY KEY (id);

-- TABLA scr_typedoc
ALTER TABLE ONLY scr_typedoc
    ADD CONSTRAINT pk_scr_typedoc0 PRIMARY KEY (id);

-- TABLA scr_typeofic
ALTER TABLE ONLY scr_typeofic
    ADD CONSTRAINT pk_scr_typeofic PRIMARY KEY (id);

-- TABLA scr_addrtel
ALTER TABLE ONLY scr_addrtel
    ADD CONSTRAINT fk_scr_addrtel0 FOREIGN KEY (type) REFERENCES scr_typeaddress(id);

-- TABLA scr_caofic
ALTER TABLE ONLY scr_caofic
    ADD CONSTRAINT fk_scr_caofic0 FOREIGN KEY (id_ofic) REFERENCES scr_ofic(id);

-- TABLA scr_ofic
ALTER TABLE ONLY scr_ofic
    ADD CONSTRAINT fk_scr_ofic0 FOREIGN KEY (id_orgs) REFERENCES scr_orgs(id);
ALTER TABLE ONLY scr_ofic
    ADD CONSTRAINT fk_scr_ofic1 FOREIGN KEY (type) REFERENCES scr_typeofic(id);

-- TABLA scr_orgs
ALTER TABLE ONLY scr_orgs
    ADD CONSTRAINT fk_scr_orgs0 FOREIGN KEY (type) REFERENCES scr_typeadm(id);

-- TABLA scr_pfis
ALTER TABLE ONLY scr_pfis
    ADD CONSTRAINT fk_scr_pfis0 FOREIGN KEY (type_doc) REFERENCES scr_typedoc(id);

-- TABLA scr_pjur
ALTER TABLE ONLY scr_pjur
    ADD CONSTRAINT fk_scr_pjur0 FOREIGN KEY (type_doc) REFERENCES scr_typedoc(id);

-- TABLA scr_privorgs
ALTER TABLE ONLY scr_privorgs
    ADD CONSTRAINT fk_scr_privorgs0 FOREIGN KEY (idorgs) REFERENCES scr_orgs(id);

-- TABLA scr_recvmsg
ALTER TABLE ONLY scr_recvmsg
    ADD CONSTRAINT fk_scr_recvmsg0 FOREIGN KEY (sender) REFERENCES scr_orgs(id);
ALTER TABLE ONLY scr_recvmsg
    ADD CONSTRAINT fk_scr_recvmsg1 FOREIGN KEY (destination) REFERENCES scr_orgs(id);

-- TABLA scr_reportofic
ALTER TABLE ONLY scr_reportofic
    ADD CONSTRAINT fk_scr_reportofic0 FOREIGN KEY (id_ofic) REFERENCES scr_ofic(id);

-- TABLA scr_sendmsg
ALTER TABLE ONLY scr_sendmsg
    ADD CONSTRAINT fk_scr_sendmsg0 FOREIGN KEY (sender) REFERENCES scr_orgs(id);
ALTER TABLE ONLY scr_sendmsg
    ADD CONSTRAINT fk_scr_sendmsg1 FOREIGN KEY (destination) REFERENCES scr_orgs(id);

-- TABLA scr_sendreg
ALTER TABLE ONLY scr_sendreg
    ADD CONSTRAINT fk_scr_sendreg0 FOREIGN KEY (id_entreg_dest) REFERENCES scr_orgs(id);

-- TABLA scr_usrofic
ALTER TABLE ONLY scr_usrofic
    ADD CONSTRAINT fk_scr_usrofic0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_ws
ALTER TABLE ONLY scr_ws
    ADD CONSTRAINT fk_scr_ws0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_caadm
ALTER TABLE ONLY scr_caadm
    ADD CONSTRAINT fk_scrcaadm0 FOREIGN KEY (idca) REFERENCES scr_ca(id);

-- TABLA scr_lockrelations
ALTER TABLE ONLY scr_lockrelations
    ADD CONSTRAINT fk_scrlckrel0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_oficadm
ALTER TABLE ONLY scr_oficadm
    ADD CONSTRAINT fk_scroficadm0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);

-- TABLA scr_relations
ALTER TABLE ONLY scr_relations
    ADD CONSTRAINT fk_scrrels0 FOREIGN KEY (idofic) REFERENCES scr_ofic(id);
ALTER TABLE ONLY scr_relations
    ADD CONSTRAINT fk_scrrels1 FOREIGN KEY (idunit) REFERENCES scr_orgs(id);

-- TABLA scr_repobooktype
ALTER TABLE ONLY scr_repobooktype
    ADD CONSTRAINT pk_repobooktype PRIMARY KEY (book_type);
ALTER TABLE ONLY scr_repobooktype
    ADD CONSTRAINT fk_repobooktype0 FOREIGN KEY (id_rep_conf) REFERENCES scr_repositoryconf(id);

-- TABLA scr_prov
ALTER TABLE ONLY scr_prov
    ADD CONSTRAINT pk_scr_prov0 PRIMARY KEY (id);

-- TABLA scr_cities
ALTER TABLE ONLY scr_cities
    ADD CONSTRAINT pk_scr_cities0 PRIMARY KEY (id);

--TABLA SCR_PAGETYPE
ALTER TABLE ONLY scr_pagetype
    ADD CONSTRAINT pk_scr_pagetype PRIMARY KEY (id);

--TABLA SCR_PAGEREPOSITORY
ALTER TABLE ONLY scr_pagerepository
    ADD CONSTRAINT fk_pagerepository0 FOREIGN KEY (id_pagetype) REFERENCES scr_pagetype(id);

-- TABLA SCR_DISTRIBUCION_ACTAL
ALTER TABLE scr_distribucion_actual
  ADD CONSTRAINT pk_scr_distribucion_actual PRIMARY KEY (id_dist);
