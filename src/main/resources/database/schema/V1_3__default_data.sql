INSERT INTO public.roles (role_id, role) VALUES (1, 'ADMIN');
INSERT INTO public.roles (role_id, role) VALUES (2, 'USER');

ALTER SEQUENCE roles_seq_pk RESTART WITH 3;

-- pwd cepera = $2a$10$ihXeOCKGWFSPOE/uahVnAuXKaz9anlPhQ/roNFOn08MkBvyqL0WAG
-- pwd test = $2a$10$rhxqCdgCOx80lAeoXeaasuJivKqc7zZz5fWmaHhQF.QxML0m0MmeS
INSERT INTO public.users (user_id, login, password, enabled, role_id) VALUES (1, 'cepera', '$2a$10$ihXeOCKGWFSPOE/uahVnAuXKaz9anlPhQ/roNFOn08MkBvyqL0WAG', true, 1);
INSERT INTO public.users (user_id, login, password, enabled, role_id) VALUES (2, 'test', '$2a$10$rhxqCdgCOx80lAeoXeaasuJivKqc7zZz5fWmaHhQF.QxML0m0MmeS', true, 2);

ALTER SEQUENCE users_seq_pk RESTART WITH 3;
