--liquibase formatted sql

--changeset andrii.kolomoiets:03_create_recipe_table
CREATE TABLE IF NOT EXISTS public.recipes
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       VARCHAR(50) NOT NULL UNIQUE,
    description TEXT        NOT NULL,
    created_by  BIGINT      NOT NULL REFERENCES public.users (id),
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);
