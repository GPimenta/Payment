#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER paymentserviceuser WITH PASSWORD 'root';
  CREATE DATABASE paymentservice;
  GRANT ALL PRIVILEGES ON DATABASE paymentservice TO paymentserviceuser;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "paymentservice" <<-EOSQL
  CREATE TABLE IF NOT EXISTS payments (
    id uuid primary key,
    created_at timestamp default NOW(),
    amount varchar,
    currency varchar,
    debtor_account_number varchar,
    creditor_account_number varchar
  );
EOSQL