insert into USERS (USERNAME, EMAIL, PASSWORD, ENABLED)
VALUES ('mantiscrab', 'mantiscrab@budgetr.pl', '{bcrypt}$2y$10$cPGQpBcjRtTuT5Y97wGabOMxYOZ58vi2.NmLeXpk.LauV7Zjl7niS',
        TRUE);
insert into AUTHORITIES (AUTHORITY, USERNAME)
VALUES ('ROLE_USER', 'mantiscrab');

insert into BUDGETR_USERS (USERNAME, EMAIL)
values ('mantiscrab', 'mantiscrab@budgetr.pl');

insert into BANK_ACCOUNT (INITIAL_BALANCE, NAME, USERNAME, BANK_ACCOUNTS_ORDER)
VALUES (0.00, 'Millennium', 'mantiscrab', 0);

insert into BANK_ACCOUNT (INITIAL_BALANCE, NAME, USERNAME, BANK_ACCOUNTS_ORDER)
VALUES (100.00, 'PKOBP', 'mantiscrab', 1);
