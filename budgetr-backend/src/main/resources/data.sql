insert into USERS (USERNAME, EMAIL, PASSWORD, ENABLED)
VALUES ('mantiscrab', 'mantiscrab@budgetr.com', '{bcrypt}$2y$10$cPGQpBcjRtTuT5Y97wGabOMxYOZ58vi2.NmLeXpk.LauV7Zjl7niS', TRUE);
insert into AUTHORITIES (AUTHORITY, USERNAME)
VALUES ('ROLE_USER', 'mantiscrab');