INSERT INTO location (name) VALUES ('Salle 203'),
                                   ('Salle 101'),
                                   ('Coworking 1');

INSERT INTO role (name) VALUES ('User'),
                               ('Admin');

INSERT INTO type (name) VALUES ('Tour'),
                               ('PC portable'),
                               ('Ecran');

INSERT INTO state (name) VALUES ('Neuf'),
                                ('Bon état'),
                                ('fonctionnel'),
                                ('non-fonctionnel');

INSERT INTO accreditation DEFAULT VALUES;
INSERT INTO accreditation DEFAULT VALUES;

INSERT INTO accreditation_type (accreditation_id, type_id) VALUES ('1', '1'),
                                                                  ('1', '2'),
                                                                  ('1', '3'),
                                                                  ('2', '1');
INSERT INTO documentation (title, url) VALUES ('Livret PC', 'www.tuto.com'),
                                              ('Vidéo tuto PC', 'www.tuto.com');
INSERT INTO request (date, content) VALUES ('02/03/2026', 'le PC ne fonctionne plus');