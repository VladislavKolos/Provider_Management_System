INSERT INTO "role" (id, role_name)
VALUES (gen_random_uuid(), 'ROLE_ADMIN'),
       (gen_random_uuid(), 'ROLE_CLIENT');

INSERT INTO "status" (id, status_name)
VALUES (gen_random_uuid(), 'active'),
       (gen_random_uuid(), 'inactive'),
       (gen_random_uuid(), 'banned');
