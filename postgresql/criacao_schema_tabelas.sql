create schema pessoas_owner;

create table pessoas_owner.pessoas (
    id bigserial not null primary key,
    nome character varying(200) not null,
    cpf character varying(11) not null,
    data_nascimento date not null,
    aud_dh_criacao timestamp not null default CURRENT_TIMESTAMP,
    aud_dh_alteracao timestamp not null default CURRENT_TIMESTAMP
);

create table pessoas_owner.pessoas_contatos (
    id bigserial not null primary key,
    i_pessoas bigserial not null references pessoas_owner.pessoas(id),
    nome character varying(200) not null,
    telefone character varying(11) not null,
    email character varying(100) not null,
    aud_dh_criacao timestamp not null default CURRENT_TIMESTAMP,
    aud_dh_alteracao timestamp not null default CURRENT_TIMESTAMP
);
