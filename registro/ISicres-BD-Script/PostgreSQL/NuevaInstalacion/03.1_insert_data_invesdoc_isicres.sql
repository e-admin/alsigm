-- TABLA idocdirhdr

-- TABLA idocarchdet

-- TABLA idocarchhdr

-- TABLA idocdbinfo
INSERT INTO idocdbinfo (id, name, graldbname, graldbuser, graldbpassword, misc) VALUES (1, 'Local', NULL, NULL, NULL, '"01.04"|"9.2.0"|2|"doc","txt"|0,0,""|0');


-- TABLA idocdatnode


-- TABLA idocfmt

-- TABLA idocnextid
INSERT INTO idocnextid (type, id) VALUES (1, 1);
INSERT INTO idocnextid (type, id) VALUES (2, 1);
INSERT INTO idocnextid (type, id) VALUES (4, 1);
INSERT INTO idocnextid (type, id) VALUES (7, 1);
INSERT INTO idocnextid (type, id) VALUES (9, 1);
INSERT INTO idocnextid (type, id) VALUES (10, 1);
INSERT INTO idocnextid (type, id) VALUES (11, 1);
INSERT INTO idocnextid (type, id) VALUES (12, 1);
INSERT INTO idocnextid (type, id) VALUES (13, 1);
INSERT INTO idocnextid (type, id) VALUES (14, 1);
INSERT INTO idocnextid (type, id) VALUES (15, 1);
INSERT INTO idocnextid (type, id) VALUES (16, 1);
INSERT INTO idocnextid (type, id) VALUES (17, 2);
INSERT INTO idocnextid (type, id) VALUES (18, 1);
INSERT INTO idocnextid (type, id) VALUES (19, 1);


-- TABLA idocpreffmt

-- TABLA idocxnextid

-- TABLA iuserdepthdr

-- TABLA iusergenperms

-- TABLA iusergrouphdr

-- TABLA iusergroupuser

-- TABLA iuserlicences

-- TABLA iusernextid
INSERT INTO iusernextid (type, id) VALUES (1, 1);
INSERT INTO iusernextid (type, id) VALUES (2, 1);
INSERT INTO iusernextid (type, id) VALUES (3, 1);
INSERT INTO iusernextid (type, id) VALUES (4, 4);
INSERT INTO iusernextid (type, id) VALUES (5, 8);


-- TABLA iuserobjhdr
INSERT INTO iuserobjhdr (id, prodid, type, extid1, extid2, extid3, ownertype, ownerid, crtrid, crtndate, updrid, upddate) VALUES (1, 3, 4, 0, 0, 0, 1, 0, 0, '2010-02-19 17:44:07', NULL, NULL);

-- TABLA iuseruserhdr
INSERT INTO iuseruserhdr (id, name, password, deptid, flags, stat, numbadcnts, remarks, crtrid, crtndate, updrid, upddate, pwdlastupdts, pwdmbc, pwdvpcheck) VALUES (0, 'SYSSUPERUSER', 'ADd6PK3AWOSc5e4=', 0, 0, 0, 0, NULL, 0, '2010-02-19 17:35:09', NULL, NULL, 343073, 'N', 'N');


-- TABLA iuserusersys
INSERT INTO iuserusersys (maxbadcnts, pwdvp, pwdmbc, pwdminlen, pwdexpiredap, numpwdlock, flags, updrid, upddate) VALUES (3, -1, 'N', 3, -1, 1, 0, 2147483646, NULL);


-- TABLA iuserusertype
INSERT INTO iuserusertype (userid, prodid, type) VALUES (0, 1, 3);
-- TABLA ivolactdir


-- TABLA ivolarchlist

-- TABLA ivollisthdr

-- TABLA ivollistvol


-- TABLA ivolnextid
INSERT INTO ivolnextid (type, id) VALUES (1, 1);
INSERT INTO ivolnextid (type, id) VALUES (2, 1);
INSERT INTO ivolnextid (type, id) VALUES (3, 1);
INSERT INTO ivolnextid (type, id) VALUES (4, 1);

-- TABLA ivolrephdr


-- TABLA ivolvolhdr






