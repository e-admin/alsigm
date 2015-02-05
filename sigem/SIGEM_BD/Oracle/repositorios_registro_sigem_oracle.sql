
-- TABLA ivolactdir
insert into ivolactdir (volid, actdir, numfiles) values (4,1,0);
insert into ivolactdir (volid, actdir, numfiles) values (5,1,0);
insert into ivolactdir (volid, actdir, numfiles) values (6,1,0);

-- TABLA ivollistvol
insert into ivollistvol (listid, volid, sortorder) values (3,4,1);
insert into ivollistvol (listid, volid, sortorder) values (4,5,1);
insert into ivollistvol (listid, volid, sortorder) values (5,5,1);
insert into ivollistvol (listid, volid, sortorder) values (6,6,1);

-- TABLA ivolrephdr
insert into ivolrephdr (id, name, type, info, stat, remarks, crtrid, crtndate, updrid, upddate) values (5,'SIGEM_REGISTRO',1,'"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/sigem/000_REPOSITORIO_REGISTRO"|1|3|3|0|0',0,'',0,sysdate,null,null);
insert into ivolrephdr (id, name, type, info, stat, remarks, crtrid, crtndate, updrid, upddate) values (6,'SIGEM_TRAMITACION',1,'"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/sigem/000_SIGEM_TRAMITACION"|1|3|3|0|0',0,'',0,sysdate,null,null);
insert into ivolrephdr (id, name, type, info, stat, remarks, crtrid, crtndate, updrid, upddate) values (7,'SIGEM_ARCHIVO',1,'"01.01"|"127.0.0.1,21,sigem,Ok04ddM=,/home/sigem/000_SIGEM_ARCHIVO"|1|3|3|0|0',0,'',0,sysdate,null,null);

-- TABLA ivolvolhdr
insert into ivolvolhdr (id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate, updrid, upddate) values (4,'Volumen01',5,'"01.01"|1|"Volumen01"|"524288000"|0',0,'0',0,0,'',0,sysdate,null,null);
insert into ivolvolhdr (id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate, updrid, upddate) values (5,'TramitadorVol01',6,'"01.01"|1|"TramitadorVol01"|"524288000"|0',0,'0',0,0,'',0,sysdate,null,null);
insert into ivolvolhdr (id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate, updrid, upddate) values (6,'ArchivoVol01',7,'"01.01"|1|"ArchivoVol01"|"524288000"|0',0,'0',0,0,'',0,sysdate,null,null);

