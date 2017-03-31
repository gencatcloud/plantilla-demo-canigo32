-------------------------------------------------------------------------------------------------------------
--
-- Arxiu de inserció de dades d'exemple a la bbdd h2
-- ---------------------------------------------------
--
--	Insercions:
--		
--		Taula "USERS": s'inserta el nom d'usuari, la contrasenya i si està habilitat o no. (1 = si, 0 = no).
--		Taula "GROUPS": s'inserta l'identificador i el nom del grup.
--		Taula "GROUP_AUTHORITIES": s'inserta l'identificador del grup i el rol.
--		Taula "GROUP_MEMBERS": s'inserta l'identificador, el nom d'usuari i el grup al que pertany.
--		Taula "AUTHORITIES": s'inserta el nom d'usuari i el rol al que pertany.
--
--
-------------------------------------------------------------------------------------------------------------
insert into groups(group_name) values ('Users');
insert into groups(group_name) values ('Administrators');

insert into group_authorities(group_id, authority) select id,'ROLE_USER' from groups where group_name='Users'; 
insert into group_authorities(group_id, authority) select id,'ROLE_USER' from groups where group_name='Administrators'; 
insert into group_authorities(group_id, authority) select id,'ROLE_ADMIN' from groups where group_name='Administrators'; 

insert into users(username, password, enabled) values ('user','password',true);
insert into users(username, password, enabled) values ('admin','password',true);

insert into group_members(group_id, username) select id,'user' from groups where group_name='Users';
insert into group_members(group_id, username) select id,'admin' from groups where group_name='Administrators';

insert into authorities (username, authority) VALUES ('user', 'ROLE_USER');
insert into authorities (username, authority) VALUES ('admin', 'ROLE_USER');
insert into authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');

commit;
