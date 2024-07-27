INSERT INTO user_management.users (id, deleted, "version", created_at, created_by, updated_at, updated_by, username, "password")
SELECT '6b42b26f-ffd8-4dc3-9caf-8629add26a92'::uuid, false, 0, current_timestamp, null, null, null, 'adj2', '$2a$12$LN5AqiA9X8P715qOAKJi4O2ACrfq6t6XeSiEM0uo8Am.CS86ggsiK'
WHERE (SELECT count(*) FROM user_management.users u2) = 0;
