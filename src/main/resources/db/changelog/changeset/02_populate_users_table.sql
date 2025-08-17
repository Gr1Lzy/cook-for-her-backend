--liquibase formatted sql

--changeset andrii.kolomoiets:02-populate-users-table
INSERT INTO public.users (username, email, password, role_id)
VALUES ('admin', 'admin@example.com',
        '$2a$12$hOe2VaoQoE699.vLGQgPCOTKZ3xo68UuUqv.DR.RyiKzxTJu1d4mK', 1) // 12345678
