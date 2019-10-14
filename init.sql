-- CREATE URL TABLE
CREATE TABLE url_access (
        id serial NOT NULL,
        link text not null ,
        CONSTRAINT url_access_pkey PRIMARY KEY (id),
        CONSTRAINT url_access_link_key UNIQUE (link)
);

-- CREATE USER TABLE
CREATE TABLE public.user_access (
        id serial NOT NULL,
        user_name varchar(120) null,
        CONSTRAINT user_access_pkey PRIMARY KEY (id),
        CONSTRAINT user_access_user_name_key UNIQUE (user_name)
);

-- CREATE RELATION
create table url_relation (
id serial not null,
id_user int8 not null,
id_url int8 not null,
rating int4 not null
);
ALTER TABLE public.url_relation ADD CONSTRAINT fk_id_user FOREIGN KEY (id_user) REFERENCES user_access(id);
ALTER TABLE public.url_relation ADD CONSTRAINT fk_id_url FOREIGN KEY (id_url) REFERENCES url_access(id);