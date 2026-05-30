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
       ('GPU');

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
       ('2', '1');

INSERT INTO documentation (title, url)
VALUES ('Livret PC', 'www.tuto.com'),
       ('Vidéo tuto PC', 'www.tuto.com');

INSERT INTO model (is_component, type_id, description, name)
VALUES (false, 2, 'description', 'Lenovo 35X'),
       (true, 4, 'description', 'GeForce 4080');

INSERT INTO composition (parent_id, component_id, amount)
VALUES (1, 2, 1);

-- Mdp : blaBlabla6!
INSERT INTO app_user (name, surname, email, password, accreditation_id, role_id)
VALUES ('Nicolas', 'Launay', 'nicolas.launay@email.com', '$2a$10$5pY5tTl.mP/ybcGMeE9bbelROnRQxSx46l3yt6vx58H2NrNV9UZES', 1, 1),
       ('Celia', 'Godfrin', 'celia.godfrin@email.com', '$2a$10$5pY5tTl.mP/ybcGMeE9bbelROnRQxSx46l3yt6vx58H2NrNV9UZES', 2, 3);

INSERT INTO equipment (name, condition, model_id, location_id)
VALUES ('PC 1', 'éraflures sur capot', 1, 3);

INSERT INTO loan (start_date, end_date, return_date, user_id, equipment_id)
VALUES ('2026-04-16', '2026-04-16', '2026-04-16', 1, 1),
       ('2026-04-16', '2026-05-16', null, 2, 1),
        ('2025-04-16', '2025-05-16', '2025-04-16', 2, 1);

INSERT INTO request (date, content, loan_id)
VALUES ('02-03-2026', 'le PC ne fonctionne plus', 1);

INSERT INTO modification (date, author_id, equipment_id, new_state_id)
VALUES ('2026-04-15', 1, 1, 4);