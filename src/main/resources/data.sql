INSERT INTO users (id, user_name, password)
VALUES
    (1, 'alice', 'alice')
    ,(2, 'bob', 'bob')
    ,(3, 'carol', 'carol')
    ,(4, 'dave', 'dave')
ON CONFLICT (id) DO NOTHING;