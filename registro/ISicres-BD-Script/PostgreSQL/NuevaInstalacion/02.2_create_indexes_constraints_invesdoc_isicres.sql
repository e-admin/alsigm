-- TABLA idocappevent
CREATE UNIQUE INDEX idocappevent1 ON idocappevent USING btree (appid, eventid);

-- TABLA idocarchdet
CREATE UNIQUE INDEX idocarchdet1 ON idocarchdet USING btree (archid, dettype);
CREATE INDEX idocarchdet2 ON idocarchdet USING btree (archid);

-- TABLA idocarchhdr
CREATE UNIQUE INDEX idocarchhdr1 ON idocarchhdr USING btree (id);
CREATE INDEX idocarchhdr2 ON idocarchhdr USING btree (type);

-- TABLA idocautonumctlg
CREATE UNIQUE INDEX idocautonumctlg1 ON idocautonumctlg USING btree (id);
CREATE UNIQUE INDEX idocautonumctlg2 ON idocautonumctlg USING btree (name);

-- TABLA idocbtblctlg
CREATE UNIQUE INDEX idocbtblctlg1 ON idocbtblctlg USING btree (id);

-- TABLA idoccompusg
CREATE INDEX idoccompusg1 ON idoccompusg USING btree (compid, comptype);

-- TABLA idocdatnode
CREATE UNIQUE INDEX idocdatnode1 ON idocdatnode USING btree (id, type);
CREATE INDEX idocdatnode2 ON idocdatnode USING btree (parentid);
CREATE INDEX idocdatnode3 ON idocdatnode USING btree (type);

-- TABLA idocdbctlg
CREATE UNIQUE INDEX idocdbctlg1 ON idocdbctlg USING btree (id);
CREATE UNIQUE INDEX idocdbctlg2 ON idocdbctlg USING btree (name);

-- TABLA idocdirhdr
CREATE UNIQUE INDEX idocdirhdr1 ON idocdirhdr USING btree (id);
CREATE INDEX idocdirhdr2 ON idocdirhdr USING btree (type);

-- TABLA idocfdrlink
CREATE UNIQUE INDEX idocfdrlink1 ON idocfdrlink USING btree (id);
CREATE INDEX idocfdrlink2 ON idocfdrlink USING btree (cntrdbid, cntrarchid, cntrfdrid, cntrclfid);
CREATE INDEX idocfdrlink3 ON idocfdrlink USING btree (srvrdbid, srvrarchid, srvrfdrid);

-- TABLA idocfdrlinkx
CREATE UNIQUE INDEX idocfdrlinkx1 ON idocfdrlinkx USING btree (id);
CREATE INDEX idocfdrlinkx2 ON idocfdrlinkx USING btree (cntrdbid, cntrarchid, cntrfdrid, cntrclfid);
CREATE INDEX idocfdrlinkx3 ON idocfdrlinkx USING btree (srvrdbid, srvrarchid, srvrfdrid);

-- TABLA idocfdrstat
CREATE UNIQUE INDEX idocfdrstat1 ON idocfdrstat USING btree (archid, fdrid);
CREATE INDEX idocfdrstat2 ON idocfdrstat USING btree (stat);

-- TABLA idocfiauxtblctlg
CREATE UNIQUE INDEX idocfiauxtblctlg1 ON idocfiauxtblctlg USING btree (id);
CREATE UNIQUE INDEX idocfiauxtblctlg2 ON idocfiauxtblctlg USING btree (name);
CREATE INDEX idocfiauxtblctlg3 ON idocfiauxtblctlg USING btree (userid);

-- TABLA idocfmt
CREATE UNIQUE INDEX idocfmt1 ON idocfmt USING btree (id);
CREATE INDEX idocfmt2 ON idocfmt USING btree (archid, type);

-- TABLA idocglbdoch
CREATE UNIQUE INDEX idocglbdoch1 ON idocglbdoch USING btree (id);
CREATE INDEX idocglbdoch2 ON idocglbdoch USING btree (archid, fdrid);
CREATE INDEX idocglbdoch3 ON idocglbdoch USING btree (archid, fdrid, clfid);

-- TABLA idocmacro
CREATE UNIQUE INDEX idocmacro1 ON idocmacro USING btree (id);
CREATE UNIQUE INDEX idocmacro2 ON idocmacro USING btree (name);

-- TABLA idocnextid
CREATE UNIQUE INDEX idocnextid1 ON idocnextid USING btree (type);

-- TABLA idocpageanns
CREATE UNIQUE INDEX idocpageanns1 ON idocpageanns USING btree (id);

-- TABLA idocpict
CREATE UNIQUE INDEX idocpict1 ON idocpict USING btree (id);
CREATE UNIQUE INDEX idocpict2 ON idocpict USING btree (name);

-- TABLA idocpreffmt
CREATE UNIQUE INDEX idocpreffmt1 ON idocpreffmt USING btree (archid, fmttype, userid);

-- TABLA idocprefwfmt
CREATE UNIQUE INDEX idocprefwfmt1 ON idocprefwfmt USING btree (archid, fmttype, userid);

-- TABLA idocrpt
CREATE UNIQUE INDEX idocrpt1 ON idocrpt USING btree (id);
CREATE INDEX idocrpt2 ON idocrpt USING btree (archid);

-- TABLA idocrptauxtbl
CREATE UNIQUE INDEX idocrptauxtbl1 ON idocrptauxtbl USING btree (fdrid);

-- TABLA idocsrvstatedet
CREATE UNIQUE INDEX idocsrvstatedet1 ON idocsrvstatedet USING btree (id);
CREATE INDEX idocsrvstatedet2 ON idocsrvstatedet USING btree (sessionid);
CREATE INDEX idocsrvstatedet3 ON idocsrvstatedet USING btree (parentid);

-- TABLA idocsrvstatehdr
CREATE UNIQUE INDEX idocsrvstatehdr1 ON idocsrvstatehdr USING btree (id);
CREATE UNIQUE INDEX idocsrvstatehdr2 ON idocsrvstatehdr USING btree (userid, prodid);
CREATE INDEX idocsrvstatehdr3 ON idocsrvstatehdr USING btree ("timestamp");

-- TABLA idocvtblctlg
CREATE UNIQUE INDEX idocvtblctlg1 ON idocvtblctlg USING btree (id);

-- TABLA idocwmacro
CREATE UNIQUE INDEX idocwmacro1 ON idocwmacro USING btree (id);
CREATE UNIQUE INDEX idocwmacro2 ON idocwmacro USING btree (name);

-- TABLA idocxnextid
CREATE UNIQUE INDEX idocxnextid1 ON idocxnextid USING btree (type, parentid);

-- TABLA itdsmssess
CREATE UNIQUE INDEX itdsmssess1 ON itdsmssess USING btree (cid);
CREATE INDEX itdsmssess2 ON itdsmssess USING btree (cappid);
CREATE INDEX itdsmssess3 ON itdsmssess USING btree (cuserid);

-- TABLA iusercurrcnt
CREATE UNIQUE INDEX iusercurrcnt1 ON iusercurrcnt USING btree (id);
CREATE UNIQUE INDEX iusercurrcnt2 ON iusercurrcnt USING btree (userid, prodid, appid);

-- TABLA iuserdepthdr
CREATE UNIQUE INDEX iuserdepthdr1 ON iuserdepthdr USING btree (id);
CREATE UNIQUE INDEX iuserdepthdr2 ON iuserdepthdr USING btree (name);
CREATE INDEX iuserdepthdr3 ON iuserdepthdr USING btree (parentid);
CREATE INDEX iuserdepthdr4 ON iuserdepthdr USING btree (mgrid);
CREATE INDEX iuserdepthdr5 ON iuserdepthdr USING btree (type);

-- TABLA iusergenperms
CREATE INDEX iusergenperms1 ON iusergenperms USING btree (dsttype, dstid);
CREATE UNIQUE INDEX iusergenperms2 ON iusergenperms USING btree (dsttype, dstid, prodid);

-- TABLA iusergrouphdr
CREATE UNIQUE INDEX iusergrouphdr1 ON iusergrouphdr USING btree (id);
CREATE UNIQUE INDEX iusergrouphdr2 ON iusergrouphdr USING btree (name);
CREATE INDEX iusergrouphdr3 ON iusergrouphdr USING btree (mgrid);
CREATE INDEX iusergrouphdr4 ON iusergrouphdr USING btree (type);

-- TABLA iusergroupuser
CREATE INDEX iusergroupuser1 ON iusergroupuser USING btree (groupid);
CREATE INDEX iusergroupuser2 ON iusergroupuser USING btree (userid);

-- TABLA iuserldapgrphdr
CREATE UNIQUE INDEX iuserldapgrphdr1 ON iuserldapgrphdr USING btree (id);
CREATE UNIQUE INDEX iuserldapgrphdr2 ON iuserldapgrphdr USING btree (ldapguid);

-- TABLA iuserldapuserhdr
CREATE UNIQUE INDEX iuserldapuserhdr1 ON iuserldapuserhdr USING btree (id);
CREATE UNIQUE INDEX iuserldapuserhdr2 ON iuserldapuserhdr USING btree (ldapguid);

-- TABLA iusernextid
CREATE UNIQUE INDEX iusernextid1 ON iusernextid USING btree (type);

-- TABLA iuserobjhdr
CREATE UNIQUE INDEX iuserobjhdr1 ON iuserobjhdr USING btree (id);
CREATE INDEX iuserobjhdr2 ON iuserobjhdr USING btree (ownertype, ownerid);
CREATE INDEX iuserobjhdr3 ON iuserobjhdr USING btree (prodid);
CREATE INDEX iuserobjhdr4 ON iuserobjhdr USING btree (prodid, type);
CREATE INDEX iuserobjhdr5 ON iuserobjhdr USING btree (prodid, type, extid1);

-- TABLA iuserobjperm
CREATE INDEX iuserobjperm1 ON iuserobjperm USING btree (dsttype, dstid);
CREATE INDEX iuserobjperm2 ON iuserobjperm USING btree (objid);
CREATE INDEX iuserobjperm3 ON iuserobjperm USING btree (dsttype, dstid, objid);

-- TABLA iuserremuser
CREATE UNIQUE INDEX iuserremuser1 ON iuserremuser USING btree (id);

-- TABLA iuseruserhdr
CREATE UNIQUE INDEX iuseruserhdr1 ON iuseruserhdr USING btree (id);
CREATE UNIQUE INDEX iuseruserhdr2 ON iuseruserhdr USING btree (name);
CREATE INDEX iuseruserhdr3 ON iuseruserhdr USING btree (deptid);

-- TABLA iuserusermap
CREATE INDEX iuserusermap1 ON iuserusermap USING btree (srcdbid, srcuserid);
CREATE INDEX iuserusermap2 ON iuserusermap USING btree (dstdbid, dstuserid);
CREATE UNIQUE INDEX iuserusermap3 ON iuserusermap USING btree (srcdbid, srcuserid, dstdbid);

-- TABLA iuserusertype
CREATE INDEX iuserusertype1 ON iuserusertype USING btree (userid);
CREATE UNIQUE INDEX iuserusertype2 ON iuserusertype USING btree (userid, prodid);

-- TABLA ivolactdir
CREATE UNIQUE INDEX ivolactdir1 ON ivolactdir USING btree (volid);

-- TABLA ivolarchlist
CREATE UNIQUE INDEX ivolarchlist1 ON ivolarchlist USING btree (archid);
CREATE INDEX ivolarchlist2 ON ivolarchlist USING btree (listid);

-- TABLA ivolfilefts
CREATE UNIQUE INDEX ivolfilefts1 ON ivolfilefts USING btree (id);

-- TABLA ivolfilehdr
CREATE UNIQUE INDEX ivolfilehdr1 ON ivolfilehdr USING btree (id);
CREATE INDEX ivolfilehdr2 ON ivolfilehdr USING btree (volid);

-- TABLA ivollisthdr
CREATE UNIQUE INDEX ivollisthdr1 ON ivollisthdr USING btree (id);

-- TABLA ivollistvol
CREATE INDEX ivollistvol1 ON ivollistvol USING btree (listid);
CREATE INDEX ivollistvol2 ON ivollistvol USING btree (volid);

-- TABLA ivolnextid
CREATE UNIQUE INDEX ivolnextid1 ON ivolnextid USING btree (type);

-- TABLA ivolrephdr
CREATE UNIQUE INDEX ivolrephdr1 ON ivolrephdr USING btree (id);

-- TABLA ivolvolhdr
CREATE UNIQUE INDEX ivolvolhdr1 ON ivolvolhdr USING btree (id);
CREATE INDEX ivolvolhdr2 ON ivolvolhdr USING btree (repid);

-- TABLA iuseruserpwds
CREATE INDEX iuseruserpwds1 ON iuseruserpwds USING btree (userid);
CREATE INDEX iuseruserpwds2 ON iuseruserpwds USING btree (password);

-- TABLA ivolvoltbl
CREATE UNIQUE INDEX ivolvoltbl1 ON ivolvoltbl USING btree (locid);
