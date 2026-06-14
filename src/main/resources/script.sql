INSERT INTO roles (name, active) VALUES
('ADMIN', true),
('USER', true)
ON CONFLICT (name) DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN permissions p
WHERE r.name = 'USER' AND p.method = 'GET'
ON CONFLICT DO NOTHING;

select * from role_permissions;
select * from permissions;

INSERT INTO users (
    username,
    name,
    surname,
    email,
    password,
    blocked,
    role_id
)
VALUES (
    'admin',
    'Admin',
    'Principal',
    'admin@uca.edu.sv',
    '$2a$10$npAOJac06H4gOQen/4f.1.nHJSFEm14RJpEFhJoY0YlbP3ic.eIhi',
    false,
    1
)
ON CONFLICT (username) DO NOTHING;