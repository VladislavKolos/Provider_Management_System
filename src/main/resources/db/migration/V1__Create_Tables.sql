CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE "role"
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE "status"
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE "tariff"
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tariff_name  VARCHAR(100) UNIQUE NOT NULL,
    description  TEXT,
    monthly_cost DECIMAL(10, 2)      NOT NULL,
    data_limit   DOUBLE PRECISION,
    voice_limit  DOUBLE PRECISION
);

CREATE TABLE "user"
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username   VARCHAR(32) UNIQUE  NOT NULL,
    "password" VARCHAR(256)        NOT NULL,
    email      VARCHAR(256) UNIQUE NOT NULL,
    phone      VARCHAR(18) UNIQUE  NOT NULL,
    role_id    UUID                REFERENCES "role" (id) ON DELETE SET NULL,
    status_id  UUID                REFERENCES "status" (id) ON DELETE SET NULL
);

CREATE TABLE "email_token"
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    token          VARCHAR(256) NOT NULL UNIQUE,
    expiry_date    TIMESTAMP    NOT NULL,
    user_id        UUID REFERENCES "user" (id) ON DELETE CASCADE,
    email          VARCHAR(255) NOT NULL,
    username       VARCHAR(32)  NOT NULL,
    phone          VARCHAR(18)  NOT NULL
);

CREATE TABLE "plan"
(
    id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tariff_id   UUID REFERENCES tariff (id) ON DELETE CASCADE,
    plan_name   VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    start_date  DATE,
    end_date    DATE
);

CREATE TABLE "subscription"
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID REFERENCES "user" (id) ON DELETE CASCADE,
    plan_id         UUID REFERENCES plan (id) ON DELETE CASCADE,
    status          VARCHAR(20)
);

CREATE TABLE "promotion"
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title               VARCHAR(100) UNIQUE NOT NULL,
    description         TEXT,
    discount_percentage DECIMAL(5, 2),
    start_date          DATE,
    end_date            DATE
);

CREATE TABLE "promotions_tariffs"
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    promotion_id UUID REFERENCES promotion (id) ON DELETE CASCADE,
    tariff_id    UUID REFERENCES tariff (id) ON DELETE CASCADE,
    UNIQUE (promotion_id, tariff_id)
);