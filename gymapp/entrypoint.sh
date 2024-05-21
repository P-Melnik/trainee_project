#!/usr/bin/env sh

echo "Starting gymapp application and waiting for Hibernate to create tables..."

java -jar gymapp.jar &

DB_HOST="postgres"
DB_USERNAME="postgres"
DB_NAME="gym_app"
DB_PASSWORD="31667"
IMPORT_FILE="/app/import.sql"

SLEEP_TIME=180
echo "Sleeping for $SLEEP_TIME seconds to allow Hibernate to complete table creation..."
sleep $SLEEP_TIME

echo "Waiting for training_types table to be created..."
until PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -U "$DB_USERNAME" -d "$DB_NAME" -c "\dt training_types" >/dev/null 2>&1; do
  >&2 echo "training_types table is not available - sleeping"
  sleep 1
done

echo "training_types table is available - executing script"
PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -U "$DB_USERNAME" -d "$DB_NAME" -f "$IMPORT_FILE"

wait