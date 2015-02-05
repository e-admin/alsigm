
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


-- TABLA a1clfh1
CREATE UNIQUE INDEX a1clfh1 ON a1clfh (fdrid, id);
CREATE INDEX a1clfh2 ON a1clfh (fdrid);

-- TABLA a1clfhx1
CREATE UNIQUE INDEX a1clfhx1 ON a1clfhx (fdrid, id);
CREATE INDEX a1clfhx2 ON a1clfhx (fdrid);

-- TABLA a1doch
CREATE UNIQUE INDEX a1doch1 ON a1doch (id);
CREATE INDEX a1doch2 ON a1doch (fdrid);
CREATE INDEX a1doch3 ON a1doch (fdrid, clfid);

-- TABLA a1dochx1
CREATE UNIQUE INDEX a1dochx1 ON a1dochx (id);
CREATE INDEX a1dochx2 ON a1dochx (fdrid);
CREATE INDEX a1dochx3 ON a1dochx (fdrid, clfid);

-- TABLA a1fdrh
CREATE UNIQUE INDEX a1fdrh1 ON a1fdrh (id);

-- TABLA a1pageh
CREATE UNIQUE INDEX a1pageh1 ON a1pageh (fdrid, id);
CREATE INDEX a1pageh2 ON a1pageh (fdrid);
CREATE INDEX a1pageh3 ON a1pageh (fdrid, docid);
CREATE INDEX a1pageh4 ON a1pageh (fileid);

-- TABLA a1pagehx
CREATE UNIQUE INDEX a1pagehx1 ON a1pagehx (fdrid, id);
CREATE INDEX a1pagehx2 ON a1pagehx (fdrid);
CREATE INDEX a1pagehx3 ON a1pagehx (fdrid, docid);
CREATE INDEX a1pagehx4 ON a1pagehx (fileid);

-- TABLA a1sf
CREATE UNIQUE INDEX a1sf0 ON a1sf (fdrid);
CREATE UNIQUE INDEX a1sf1 ON a1sf (fld1);
CREATE INDEX a1sf2 ON a1sf (fld2);
CREATE INDEX a1sf3 ON a1sf (fld4);
CREATE INDEX a1sf4 ON a1sf (fld6);
CREATE INDEX a1sf5 ON a1sf (fld7);
CREATE INDEX a1sf6 ON a1sf (fld8);
CREATE INDEX a1sf7 ON a1sf (fld9);
CREATE INDEX a1sf8 ON a1sf (fld5);

-- TABLA a1xf
CREATE UNIQUE INDEX a1xf1 ON a1xf (id);
CREATE UNIQUE INDEX a1xf2 ON a1xf (fdrid, fldid);
CREATE INDEX a1xf3 ON a1xf (fdrid);

-- TABLA a1xnid
CREATE UNIQUE INDEX a1xnid1 ON a1xnid (type, parentid); 

-- TABLA a2clfh
CREATE UNIQUE INDEX a2clfh1 ON a2clfh (fdrid, id);
CREATE INDEX a2clfh2 ON a2clfh (fdrid);

-- TABLA a2clfhx
CREATE UNIQUE INDEX a2clfhx1 ON a2clfhx (fdrid, id);
CREATE INDEX a2clfhx2 ON a2clfhx (fdrid);

-- TABLA a2doch
CREATE UNIQUE INDEX a2doch1 ON a2doch (id);
CREATE INDEX a2doch2 ON a2doch (fdrid);
CREATE INDEX a2doch3 ON a2doch (fdrid, clfid);

-- TABLA a2dochx
CREATE UNIQUE INDEX a2dochx1 ON a2dochx (id);
CREATE INDEX a2dochx2 ON a2dochx (fdrid);
CREATE INDEX a2dochx3 ON a2dochx (fdrid, clfid);

-- TABLA a2fdrh
CREATE UNIQUE INDEX a2fdrh1 ON a2fdrh (id);

-- TABLA a2pageh
CREATE UNIQUE INDEX a2pageh1 ON a2pageh (fdrid, id);
CREATE INDEX a2pageh2 ON a2pageh (fdrid);
CREATE INDEX a2pageh3 ON a2pageh (fdrid, docid);
CREATE INDEX a2pageh4 ON a2pageh (fileid);

-- TABLA a2pagehx
CREATE UNIQUE INDEX a2pagehx1 ON a2pagehx (fdrid, id);
CREATE INDEX a2pagehx2 ON a2pagehx (fdrid);
CREATE INDEX a2pagehx3 ON a2pagehx (fdrid, docid);
CREATE INDEX a2pagehx4 ON a2pagehx (fileid);

-- TABLA a2sf
CREATE UNIQUE INDEX a2sf0 ON a2sf (fdrid);
CREATE UNIQUE INDEX a2sf1 ON a2sf (fld1);
CREATE INDEX a2sf2 ON a2sf (fld2);
CREATE INDEX a2sf3 ON a2sf (fld4);
CREATE INDEX a2sf4 ON a2sf (fld6);
CREATE INDEX a2sf5 ON a2sf (fld7);
CREATE INDEX a2sf6 ON a2sf (fld8);
CREATE INDEX a2sf7 ON a2sf (fld9);
CREATE INDEX a2sf8 ON a2sf (fld5);

-- TABLA a2xf
CREATE UNIQUE INDEX a2xf1 ON a2xf (id);
CREATE UNIQUE INDEX a2xf2 ON a2xf (fdrid, fldid);
CREATE INDEX a2xf3 ON a2xf (fdrid);

-- TABLA a2xnid
CREATE UNIQUE INDEX a2xnid1 ON a2xnid (type, parentid);

-- TABLA a3clfh
CREATE UNIQUE INDEX a3clfh1 ON a3clfh (fdrid, id);
CREATE INDEX a3clfh2 ON a3clfh (fdrid);

-- TABLA a3clfhx
CREATE UNIQUE INDEX a3clfhx1 ON a3clfhx (fdrid, id);
CREATE INDEX a3clfhx2 ON a3clfhx (fdrid);

-- TABLA a3doch
CREATE UNIQUE INDEX a3doch1 ON a3doch (id);
CREATE INDEX a3doch2 ON a3doch (fdrid);
CREATE INDEX a3doch3 ON a3doch (fdrid, clfid);

-- TABLA a3dochx
CREATE UNIQUE INDEX a3dochx1 ON a3dochx (id);
CREATE INDEX a3dochx2 ON a3dochx (fdrid);
CREATE INDEX a3dochx3 ON a3dochx (fdrid, clfid);

-- TABLA a3fdrh
CREATE UNIQUE INDEX a3fdrh1 ON a3fdrh (id);

-- TABLA a3pageh
CREATE UNIQUE INDEX a3pageh1 ON a3pageh (fdrid, id);
CREATE INDEX a3pageh2 ON a3pageh (fdrid);
CREATE INDEX a3pageh3 ON a3pageh (fdrid, docid);
CREATE INDEX a3pageh4 ON a3pageh (fileid);

-- TABLA a3pagehx
CREATE UNIQUE INDEX a3pagehx1 ON a3pagehx (fdrid, id);
CREATE INDEX a3pagehx2 ON a3pagehx (fdrid);
CREATE INDEX a3pagehx3 ON a3pagehx (fdrid, docid);
CREATE INDEX a3pagehx4 ON a3pagehx (fileid);

-- TABLA a3sf
CREATE UNIQUE INDEX a3sf0 ON a3sf (fdrid);
CREATE INDEX a3sf1 ON a3sf (fld1);
CREATE INDEX a3sf2 ON a3sf (fld4);

-- TABLA a3xf
CREATE UNIQUE INDEX a3xf1 ON a3xf (id);
CREATE UNIQUE INDEX a3xf2 ON a3xf (fdrid, fldid);
CREATE INDEX a3xf3 ON a3xf (fdrid);

-- TABLA a3xnid
CREATE UNIQUE INDEX a3xnid1 ON a3xnid (type, parentid);

-- TABLA a4clfh
CREATE UNIQUE INDEX a4clfh1 ON a4clfh (fdrid, id);
CREATE INDEX a4clfh2 ON a4clfh (fdrid);

-- TABLA a4clfhx
CREATE UNIQUE INDEX a4clfhx1 ON a4clfhx (fdrid, id);
CREATE INDEX a4clfhx2 ON a4clfhx (fdrid);

-- TABLA a4doch
CREATE UNIQUE INDEX a4doch1 ON a4doch (id);
CREATE INDEX a4doch2 ON a4doch (fdrid);
CREATE INDEX a4doch3 ON a4doch (fdrid, clfid);

-- TABLA a4dochx
CREATE UNIQUE INDEX a4dochx1 ON a4dochx (id);
CREATE INDEX a4dochx2 ON a4dochx (fdrid);
CREATE INDEX a4dochx3 ON a4dochx (fdrid, clfid);

-- TABLA a4fdrh
CREATE UNIQUE INDEX a4fdrh1 ON a4fdrh (id);

-- TABLA a4pageh
CREATE UNIQUE INDEX a4pageh1 ON a4pageh (fdrid, id);
CREATE INDEX a4pageh2 ON a4pageh (fdrid);
CREATE INDEX a4pageh3 ON a4pageh (fdrid, docid);
CREATE INDEX a4pageh4 ON a4pageh (fileid);

-- TABLA a4pagehx
CREATE UNIQUE INDEX a4pagehx1 ON a4pagehx (fdrid, id);
CREATE INDEX a4pagehx2 ON a4pagehx (fdrid);
CREATE INDEX a4pagehx3 ON a4pagehx (fdrid, docid);
CREATE INDEX a4pagehx4 ON a4pagehx (fileid);

-- TABLA a4sf
CREATE UNIQUE INDEX a4sf0 ON a4sf (fdrid);
CREATE INDEX a4sf1 ON a4sf (fld1);

-- TABLA a4xf
CREATE UNIQUE INDEX a4xf1 ON a4xf (id);
CREATE UNIQUE INDEX a4xf2 ON a4xf (fdrid, fldid);
CREATE INDEX a4xf3 ON a4xf (fdrid);

-- TABLA a4xnid
CREATE UNIQUE INDEX a4xnid1 ON a4xnid (type, parentid);

-----------------
-- CONSTRAINTS --
-----------------

-- TABLA a1sf
ALTER TABLE a1sf ADD CONSTRAINT fk_a1sf13 FOREIGN KEY (fld13) REFERENCES scr_orgs(id);
ALTER TABLE a1sf ADD CONSTRAINT fk_a1sf16 FOREIGN KEY (fld16) REFERENCES scr_ca(id);
ALTER TABLE a1sf ADD CONSTRAINT fk_a1sf5 FOREIGN KEY (fld5) REFERENCES scr_ofic(id);
ALTER TABLE a1sf ADD CONSTRAINT fk_a1sf7 FOREIGN KEY (fld7) REFERENCES scr_orgs(id);
ALTER TABLE a1sf ADD CONSTRAINT fk_a1sf8 FOREIGN KEY (fld8) REFERENCES scr_orgs(id);

-- TABLA a2sf
ALTER TABLE a2sf ADD CONSTRAINT fk_a2sf12 FOREIGN KEY (fld12) REFERENCES scr_ca(id);
ALTER TABLE a2sf ADD CONSTRAINT fk_a2sf5 FOREIGN KEY (fld5) REFERENCES scr_ofic(id);
ALTER TABLE a2sf ADD CONSTRAINT fk_a2sf7 FOREIGN KEY (fld7) REFERENCES scr_orgs(id);
ALTER TABLE a2sf ADD CONSTRAINT fk_a2sf8 FOREIGN KEY (fld8) REFERENCES scr_orgs(id);