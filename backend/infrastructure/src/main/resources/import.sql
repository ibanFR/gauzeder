-- Seed data: loaded by Hibernate on application startup when hbm2ddl.auto=create or create-drop
-- For hbm2ddl.auto=update, this script runs on every deployment but INSERT is idempotent
-- due to the ON CONFLICT DO NOTHING clause.
INSERT INTO hello_message (id, text) VALUES (1, 'Hello, World!') ON CONFLICT DO NOTHING;
