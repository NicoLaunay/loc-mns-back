INSERT INTO location (name)
VALUES ('Salle 203'),
       ('Salle 101'),
       ('Coworking 1');

INSERT INTO role (name)
VALUES ('OWNER'),
       ('ADMIN'),
       ('USER');

INSERT INTO type (name)
VALUES ('Tour'),
       ('PC portable'),
       ('Ecran'),
       ('GPU'),
       ('Vidéoprojecteur'),
       ('Casque VR'),
       ('Clavier'),
       ('Souris'),
       ('Station d''accueil'),
       ('Casque audio');

INSERT INTO state (name)
VALUES ('Neuf'),
       ('Bon état'),
       ('fonctionnel'),
       ('non-fonctionnel');

INSERT INTO accreditation DEFAULT VALUES;
INSERT INTO accreditation DEFAULT VALUES;

INSERT INTO accreditation_type (accreditation_id, type_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('1', '3'),

       ('2', '1'),
       ('2', '2');

INSERT INTO documentation (title, url)
VALUES ('Livret PC', 'www.tuto.com'),
       ('Vidéo tuto PC', 'www.tuto.com'),
       ('Guide GPU GeForce 4080', 'https://docs.locnes.fr/gpu-4080'),
       ('Notice écran', 'https://docs.locnes.fr/notice-ecran'),
       ('Manuel casque VR', 'https://docs.locnes.fr/manuel-vr'),
       ('Consignes de sécurité', 'https://docs.locnes.fr/securite'),
       ('Procédure de retour matériel', 'https://docs.locnes.fr/retour'),
       ('FAQ station d''accueil', 'https://docs.locnes.fr/faq-dock'),
       ('Tutoriel vidéoprojecteur', 'https://docs.locnes.fr/tuto-videoproj'),
       ('Fiche entretien PC portable', 'https://docs.locnes.fr/entretien-pc'),
       ('Notice tour Dell OptiPlex 7010', 'https://docs.locnes.fr/notice-optiplex'),
       ('Guide clavier Logitech MX Keys', 'https://docs.locnes.fr/guide-mx-keys'),
       ('Guide souris Logitech MX Master 3S', 'https://docs.locnes.fr/guide-mx-master'),
       ('Manuel casque audio Jabra Evolve 65', 'https://docs.locnes.fr/manuel-jabra');

INSERT INTO model (is_component, type_id, description, name)
VALUES (false, 2, 'description', 'Lenovo 35X'),
       (true, 4, 'description', 'GeForce 4080'),
       (false, 1, 'Tour bureautique compacte', 'Dell OptiPlex 7010'),
       (false, 3, 'Écran 27 pouces QHD', 'Dell P2723QE'),
       (false, 5, 'Vidéoprojecteur Full HD 5000 lumens', 'Epson EB-2250U'),
       (false, 6, 'Casque de réalité virtuelle autonome', 'Meta Quest 3'),
       (false, 7, 'Clavier sans fil rétroéclairé', 'Logitech MX Keys'),
       (false, 8, 'Souris ergonomique sans fil', 'Logitech MX Master 3S'),
       (false, 9, 'Station d''accueil USB-C', 'Dell WD19'),
       (false, 10, 'Casque audio stéréo à réduction de bruit', 'Jabra Evolve 65');

INSERT INTO model_documentation (model_id, documentation_id)
VALUES (1, 1),
       (1, 2),
       (1, 6),
       (1, 10),
       (2, 3),
       (2, 6),
       (3, 6),
       (3, 7),
       (3, 11),
       (4, 4),
       (5, 6),
       (5, 9),
       (6, 5),
       (6, 6),
       (7, 12),
       (8, 13),
       (9, 8),
       (10, 14);

INSERT INTO composition (parent_id, component_id, amount)
VALUES (1, 2, 1);

-- Mdp : toto
INSERT INTO app_user (name, surname, email, password, accreditation_id, role_id)
VALUES ('Nicolas', 'Launay', 'nicolas.launay@email.com', '$2y$10$54HZRPCEjt3iL6nVdBwjzusNwmww69xyA0/brhSCAVYkyjQhBwMBW', 1, 1),
       ('Celia', 'Godfrin', 'celia.godfrin@email.com', '$2y$10$54HZRPCEjt3iL6nVdBwjzusNwmww69xyA0/brhSCAVYkyjQhBwMBW', 2, 3),
       ('Amina', 'N''Diaye', 'amina.ndiaye@email.com', '$2y$10$54HZRPCEjt3iL6nVdBwjzusNwmww69xyA0/brhSCAVYkyjQhBwMBW', 2, 3),
       ('Franck', 'Doyen', 'franck.doyen@email.com', '$2y$10$54HZRPCEjt3iL6nVdBwjzusNwmww69xyA0/brhSCAVYkyjQhBwMBW', 2, 3);;

INSERT INTO equipment (name, condition, model_id, location_id)
VALUES ('PC 1', 'éraflures sur capot', 1, 3),
       ('PC 2', 'Neuf, sous emballage', 1, 1),
       ('PC 3', 'RAS', 1, 1),
       ('PC 4', 'Bon état général', 1, 2),
       ('PC 5', 'Rayure écran mineure', 1, 2),
       ('PC 6', 'RAS', 1, 3),
       ('PC 7', 'Batterie à surveiller', 1, 1),
       ('PC 8', 'RAS', 1, 2),
       ('Tour 1', 'RAS', 3, 1),
       ('Tour 2', 'Neuf, sous emballage', 3, 2),
       ('Ecran 1', 'RAS', 4, 1),
       ('Ecran 2', 'Bon état général', 4, 3),
       ('Vidéoprojecteur 1', 'RAS', 5, 1),
       ('Casque VR 1', 'Neuf', 6, 2),
       ('Casque VR 2', 'RAS', 6, 2),
       ('Clavier 1', 'RAS', 7, 1),
       ('Souris 1', 'RAS', 8, 1),
       ('Station accueil 1', 'RAS', 9, 3),
       ('Casque audio 1', 'Neuf', 10, 2);

INSERT INTO loan (start_date, end_date, return_date, user_id, equipment_id)
VALUES ('2026-04-16', '2026-04-16', '2026-04-16', 1, 1),
       ('2026-04-16', '2026-05-16', null, 2, 1),
       ('2025-04-16', '2025-05-16', '2025-04-16', 2, 1),
       ('2026-05-01', '2026-05-20', '2026-05-18', 2, 2),
       ('2026-06-01', '2026-06-15', '2026-06-14', 1, 2),
       ('2026-07-01', '2026-08-30', null, 2, 3),
       ('2026-06-10', '2026-07-10', null, 1, 4),
       ('2026-07-05', '2026-07-25', '2026-07-19', 2, 5),
       ('2026-08-01', '2026-09-01', null, 1, 6),
       ('2026-09-10', '2026-09-30', null, 2, 7);

INSERT INTO request (date, content, loan_id)
VALUES ('02-03-2026', 'le PC ne fonctionne plus', 1),
       ('2026-05-10', 'Demande de prolongation du prêt', 4),
       ('2026-06-05', 'Signalement d''une rayure sur le capot', 5),
       ('2026-07-15', 'Demande de prolongation du prêt en cours', 6),
       ('2026-07-15', 'Relance : matériel non restitué à l''échéance', 7),
       ('2026-07-10', 'Demande de retour anticipé pour panne', 8),
       ('2026-07-18', 'Le PC s''éteint tout seul, dysfonctionnement', 8),
       ('2026-08-02', 'Nouvelle demande de prolongation', 6),
       ('2026-08-03', 'Signalement : adaptateur secteur manquant', 9),
       ('2026-08-04', 'Tout fonctionne correctement, RAS', 9);

INSERT INTO modification (date, author_id, equipment_id, new_state_id)
VALUES ('2026-04-15', 1, 1, 4),
       ('2026-01-10', 1, 2, 1),
       ('2026-01-10', 1, 3, 3),
       ('2026-02-01', 1, 4, 2),
       ('2026-02-15', 1, 5, 3),
       ('2026-03-01', 1, 6, 1),
       ('2026-03-05', 1, 7, 3),
       ('2026-03-10', 1, 8, 2),
       ('2026-06-01', 1, 9, 1),
       ('2026-07-20', 1, 5, 4);