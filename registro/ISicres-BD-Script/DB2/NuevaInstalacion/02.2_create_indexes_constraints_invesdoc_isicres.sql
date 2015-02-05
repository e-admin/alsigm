---------------
-- INDICES
---------------

-- TABLA idocappevent
CREATE UNIQUE INDEX idocappevent1 ON idocappevent (appid, eventid);

-- TABLA idocarchdet
CREATE UNIQUE INDEX idocarchdet1 ON idocarchdet (archid, dettype);
CREATE INDEX idocarchdet2 ON idocarchdet (archid);

-- TABLA idocarchhdr
CREATE UNIQUE INDEX idocarchhdr1 ON idocarchhdr (id);
CREATE INDEX idocarchhdr2 ON idocarchhdr (type);

-- TABLA idocautonumctlg
CREATE UNIQUE INDEX idocautonumctlg1 ON idocautonumctlg (id);
CREATE UNIQUE INDEX idocautonumctlg2 ON idocautonumctlg (name);

-- TABLA idocbtblctlg
CREATE UNIQUE INDEX idocbtblctlg1 ON idocbtblctlg (id);

-- TABLA idoccompusg
CREATE INDEX idoccompusg1 ON idoccompusg (compid, comptype);

-- TABLA idocdatnode
CREATE UNIQUE INDEX idocdatnode1 ON idocdatnode (id, type);
CREATE INDEX idocdatnode2 ON idocdatnode (parentid);
CREATE INDEX idocdatnode3 ON idocdatnode (type);

-- TABLA idocdbctlg
CREATE UNIQUE INDEX idocdbctlg1 ON idocdbctlg (id);
CREATE UNIQUE INDEX idocdbctlg2 ON idocdbctlg (name);

-- TABLA idocdirhdr
CREATE UNIQUE INDEX idocdirhdr1 ON idocdirhdr (id);
CREATE INDEX idocdirhdr2 ON idocdirhdr (type);

-- TABLA idocfdrlink
CREATE UNIQUE INDEX idocfdrlink1 ON idocfdrlink (id);
CREATE INDEX idocfdrlink2 ON idocfdrlink (cntrdbid, cntrarchid, cntrfdrid, cntrclfid);
CREATE INDEX idocfdrlink3 ON idocfdrlink (srvrdbid, srvrarchid, srvrfdrid);

-- TABLA idocfdrlinkx
CREATE UNIQUE INDEX idocfdrlinkx1 ON idocfdrlinkx (id);
CREATE INDEX idocfdrlinkx2 ON idocfdrlinkx (cntrdbid, cntrarchid, cntrfdrid, cntrclfid);
CREATE INDEX idocfdrlinkx3 ON idocfdrlinkx (srvrdbid, srvrarchid, srvrfdrid);

-- TABLA idocfdrstat
CREATE UNIQUE INDEX idocfdrstat1 ON idocfdrstat (archid, fdrid);
CREATE INDEX idocfdrstat2 ON idocfdrstat (stat);

-- TABLA idocfiauxtblctlg
CREATE UNIQUE INDEX idocfiauxtblctlg1 ON idocfiauxtblctlg (id);
CREATE UNIQUE INDEX idocfiauxtblctlg2 ON idocfiauxtblctlg (name);
CREATE INDEX idocfiauxtblctlg3 ON idocfiauxtblctlg (userid);

-- TABLA idocfmt
CREATE UNIQUE INDEX idocfmt1 ON idocfmt (id);
CREATE INDEX idocfmt2 ON idocfmt (archid, type);

-- TABLA idocglbdoch
CREATE UNIQUE INDEX idocglbdoch1 ON idocglbdoch (id);
CREATE INDEX idocglbdoch2 ON idocglbdoch (archid, fdrid);
CREATE INDEX idocglbdoch3 ON idocglbdoch (archid, fdrid, clfid);

-- TABLA idocmacro
CREATE UNIQUE INDEX idocmacro1 ON idocmacro (id);
CREATE UNIQUE INDEX idocmacro2 ON idocmacro (name);

-- TABLA idocnextid
CREATE UNIQUE INDEX idocnextid1 ON idocnextid (type);

-- TABLA idocpageanns
CREATE UNIQUE INDEX idocpageanns1 ON idocpageanns (id);

-- TABLA idocpict
CREATE UNIQUE INDEX idocpict1 ON idocpict (id);
CREATE UNIQUE INDEX idocpict2 ON idocpict (name);

-- TABLA idocpreffmt
CREATE UNIQUE INDEX idocpreffmt1 ON idocpreffmt (archid, fmttype, userid);

-- TABLA idocprefwfmt
CREATE UNIQUE INDEX idocprefwfmt1 ON idocprefwfmt (archid, fmttype, userid);

-- TABLA idocrpt
CREATE UNIQUE INDEX idocrpt1 ON idocrpt (id);
CREATE INDEX idocrpt2 ON idocrpt (archid);

-- TABLA idocrptauxtbl
CREATE UNIQUE INDEX idocrptauxtbl1 ON idocrptauxtbl (fdrid);

-- TABLA idocsrvstatedet
CREATE UNIQUE INDEX idocsrvstatedet1 ON idocsrvstatedet (id);
CREATE INDEX idocsrvstatedet2 ON idocsrvstatedet (sessionid);
CREATE INDEX idocsrvstatedet3 ON idocsrvstatedet (parentid);

-- TABLA idocsrvstatehdr
CREATE UNIQUE INDEX idocsrvstatehdr1 ON idocsrvstatehdr (id);
CREATE UNIQUE INDEX idocsrvstatehdr2 ON idocsrvstatehdr (userid, prodid);
CREATE INDEX idocsrvstatehdr3 ON idocsrvstatehdr (timestamp);

-- TABLA idocvtblctlg
CREATE UNIQUE INDEX idocvtblctlg1 ON idocvtblctlg (id);

-- TABLA idocwmacro
CREATE UNIQUE INDEX idocwmacro1 ON idocwmacro (id);
CREATE UNIQUE INDEX idocwmacro2 ON idocwmacro (name);

-- TABLA idocxnextid
CREATE UNIQUE INDEX idocxnextid1 ON idocxnextid (type, parentid);

-- TABLA itdsmssess
CREATE UNIQUE INDEX itdsmssess1 ON itdsmssess (cid);
CREATE INDEX itdsmssess2 ON itdsmssess (cappid);
CREATE INDEX itdsmssess3 ON itdsmssess (cuserid);

-- TABLA iusercurrcnt
CREATE UNIQUE INDEX iusercurrcnt1 ON iusercurrcnt (id);
CREATE UNIQUE INDEX iusercurrcnt2 ON iusercurrcnt (userid, prodid, appid);

-- TABLA iuserdepthdr
CREATE UNIQUE INDEX iuserdepthdr1 ON iuserdepthdr (id);
CREATE UNIQUE INDEX iuserdepthdr2 ON iuserdepthdr (name);
CREATE INDEX iuserdepthdr3 ON iuserdepthdr (parentid);
CREATE INDEX iuserdepthdr4 ON iuserdepthdr (mgrid);
CREATE INDEX iuserdepthdr5 ON iuserdepthdr (type);

-- TABLA iusergenperms
CREATE INDEX iusergenperms1 ON iusergenperms (dsttype, dstid);
CREATE UNIQUE INDEX iusergenperms2 ON iusergenperms (dsttype, dstid, prodid);

-- TABLA iusergrouphdr
CREATE UNIQUE INDEX iusergrouphdr1 ON iusergrouphdr (id);
CREATE UNIQUE INDEX iusergrouphdr2 ON iusergrouphdr (name);
CREATE INDEX iusergrouphdr3 ON iusergrouphdr (mgrid);
CREATE INDEX iusergrouphdr4 ON iusergrouphdr (type);

-- TABLA iusergroupuser
CREATE INDEX iusergroupuser1 ON iusergroupuser (groupid);
CREATE INDEX iusergroupuser2 ON iusergroupuser (userid);

-- TABLA iuserldapgrphdr
CREATE UNIQUE INDEX iuserldapgrphdr1 ON iuserldapgrphdr (id);
CREATE UNIQUE INDEX iuserldapgrphdr2 ON iuserldapgrphdr (ldapguid);

-- TABLA iuserldapuserhdr
CREATE UNIQUE INDEX iuserldapuserhdr1 ON iuserldapuserhdr (id);
CREATE UNIQUE INDEX iuserldapuserhdr2 ON iuserldapuserhdr (ldapguid);

-- TABLA iusernextid
CREATE UNIQUE INDEX iusernextid1 ON iusernextid (type);

-- TABLA iuserobjhdr
CREATE UNIQUE INDEX iuserobjhdr1 ON iuserobjhdr (id);
CREATE INDEX iuserobjhdr2 ON iuserobjhdr (ownertype, ownerid);
CREATE INDEX iuserobjhdr3 ON iuserobjhdr (prodid);
CREATE INDEX iuserobjhdr4 ON iuserobjhdr (prodid, type);
CREATE INDEX iuserobjhdr5 ON iuserobjhdr (prodid, type, extid1);

-- TABLA iuserobjperm
CREATE INDEX iuserobjperm1 ON iuserobjperm (dsttype, dstid);
CREATE INDEX iuserobjperm2 ON iuserobjperm (objid);
CREATE INDEX iuserobjperm3 ON iuserobjperm (dsttype, dstid, objid);

-- TABLA iuserremuser
CREATE UNIQUE INDEX iuserremuser1 ON iuserremuser (id);

-- TABLA iuseruserhdr
CREATE UNIQUE INDEX iuseruserhdr1 ON iuseruserhdr (id);
CREATE UNIQUE INDEX iuseruserhdr2 ON iuseruserhdr (name);
CREATE INDEX iuseruserhdr3 ON iuseruserhdr (deptid);

-- TABLA iuserusermap
CREATE INDEX iuserusermap1 ON iuserusermap (srcdbid, srcuserid);
CREATE INDEX iuserusermap2 ON iuserusermap (dstdbid, dstuserid);
CREATE UNIQUE INDEX iuserusermap3 ON iuserusermap (srcdbid, srcuserid, dstdbid);

-- TABLA iuserusertype
CREATE INDEX iuserusertype1 ON iuserusertype (userid);
CREATE UNIQUE INDEX iuserusertype2 ON iuserusertype (userid, prodid);

-- TABLA ivolactdir
CREATE UNIQUE INDEX ivolactdir1 ON ivolactdir (volid);

-- TABLA ivolarchlist
CREATE UNIQUE INDEX ivolarchlist1 ON ivolarchlist (archid);
CREATE INDEX ivolarchlist2 ON ivolarchlist (listid);

-- TABLA ivolfilefts
CREATE UNIQUE INDEX ivolfilefts1 ON ivolfilefts (id);

-- TABLA ivolfilehdr
CREATE UNIQUE INDEX ivolfilehdr1 ON ivolfilehdr (id);
CREATE INDEX ivolfilehdr2 ON ivolfilehdr (volid);

-- TABLA ivollisthdr
CREATE UNIQUE INDEX ivollisthdr1 ON ivollisthdr (id);

-- TABLA ivollistvol
CREATE INDEX ivollistvol1 ON ivollistvol (listid);
CREATE INDEX ivollistvol2 ON ivollistvol (volid);

-- TABLA ivolnextid
CREATE UNIQUE INDEX ivolnextid1 ON ivolnextid (type);

-- TABLA ivolrephdr
CREATE UNIQUE INDEX ivolrephdr1 ON ivolrephdr (id);

-- TABLA ivolvolhdr
CREATE UNIQUE INDEX ivolvolhdr1 ON ivolvolhdr (id);
CREATE INDEX ivolvolhdr2 ON ivolvolhdr (repid);

-- TABLA iuseruserpwds
CREATE INDEX iuseruserpwds1 ON iuseruserpwds (userid);
CREATE INDEX iuseruserpwds2 ON iuseruserpwds (password);

-- TABLA ivolvoltbl
CREATE UNIQUE INDEX ivolvoltbl1 ON ivolvoltbl (locid);
