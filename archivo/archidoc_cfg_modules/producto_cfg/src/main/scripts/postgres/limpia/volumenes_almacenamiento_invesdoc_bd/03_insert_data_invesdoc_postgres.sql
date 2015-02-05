BEGIN TRANSACTION;

INSERT INTO IVOLACTDIR ( volid, actdir, numfiles ) VALUES (1, 1, 0); 
 
INSERT INTO IVOLLISTHDR ( id, name, flags, stat, remarks, crtrid, crtndate, updrid,
upddate ) VALUES (6, 'ListBD', 0, 0, NULL, 0,  now(), NULL, NULL); 
 
INSERT INTO IVOLLISTVOL ( listid, volid, sortorder ) VALUES (6, 1, 1);  
 
INSERT INTO IVOLNEXTID ( "type", id ) VALUES (1, 7); 
INSERT INTO IVOLNEXTID ( "type", id ) VALUES (2, 7); 
INSERT INTO IVOLNEXTID ( "type", id ) VALUES (3, 7); 
INSERT INTO IVOLNEXTID ( "type", id ) VALUES (4, 130); 

INSERT INTO IVOLREPHDR ( id, name, "type", info, stat, remarks, crtrid, crtndate, updrid,
upddate ) VALUES (1, 'RepBD', 5, '"01.01"|""|5|0|0|0|0' , 0, NULL, 0, now(), NULL, NULL); 

INSERT INTO IVOLVOLHDR ( id, name, repid, info, itemp, actsize, numfiles, stat, remarks, crtrid, crtndate,
updrid, upddate ) VALUES (1, 'RepBD', 1, '"01.01"|5|"IVOLVOLTBL,LOCID,varchar(32),FILEVAL,image"|"1073741824"|0', 0, '0', 0, 0, NULL, 0,  now(), 0,  now()); 

COMMIT;