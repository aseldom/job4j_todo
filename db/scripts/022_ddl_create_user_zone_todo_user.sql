ALTER TABLE todo_user ADD COLUMN user_zone varchar DEFAULT 'UTC';

UPDATE todo_user SET user_zone = 'Europe/Moscow';