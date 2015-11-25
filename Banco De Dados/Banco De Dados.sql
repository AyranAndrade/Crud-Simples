create database bd_crud_simples;

use bd_crud_simples;

create table cliente
(
cd_cliente int not null auto_increment,
nm_cliente varchar(60) not null,
cd_cpf_cliente char(11) not null unique,
constraint pk_cliente
primary key(cd_cliente)
);

create table produto
(
cd_produto int not null auto_increment,
nm_produto varchar(30) not null unique,
nm_descricao_produto varchar(200),
constraint pk_produto
primary key(cd_produto)
);

create table compra
(
cd_cliente int,
cd_produto int,
dt_compra datetime not null,
constraint fk_produto_cliente_1
foreign key(cd_cliente)
references cliente(cd_cliente),
constraint fk_produto_cliente_2
foreign key(cd_produto)
references produto(cd_produto)
);