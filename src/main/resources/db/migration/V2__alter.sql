ALTER TABLE user_table ADD CONSTRAINT email_key UNIQUE (email);
ALTER TABLE user_table ALTER COLUMN email SET NOT NULL;
