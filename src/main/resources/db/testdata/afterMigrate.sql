set foreign_key_checks=0;

delete from user_test;
delete from produto;
delete from cliente;

set foreign_key_checks=0;

alter table user_test auto_increment=1;
alter table produto auto_increment=1;
alter table cliente auto_increment=1;

insert into user_test(nome, senha) values('admin', '$2a$10$TFXwZRJnR5r2Y1nO3KC27OdMsMO0rmmS6wSsKMY36xb49CxKz0ULq');