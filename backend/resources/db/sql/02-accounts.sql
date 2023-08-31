\c accounts;



CREATE TABLE users (
	id UUID NOT NULL,	
	dt_create TIMESTAMP(6),	
	dt_delete TIMESTAMP(6),	
	dt_update TIMESTAMP(6),	
	meta CHARACTER VARYING(255),	
	email CHARACTER VARYING(255) NOT NULL,	
	name CHARACTER VARYING(255),	
	password CHARACTER VARYING(255) NOT NULL,

	PRIMARY KEY(id)
);


CREATE TABLE roles (
	id SMALLINT NOT NULL,
	name CHARACTER VARYING(255) NOT NULL,
	description CHARACTER VARYING(255),
	
	PRIMARY KEY(id)
);

CREATE TABLE custom_user_roles(
	custom_user_id	UUID,
	roles SMALLINT NOT NULL,
	
	PRIMARY KEY(custom_user_id, roles)
);


ALTER TABLE IF EXISTS custom_user_roles ADD CONSTRAINT custom_user_roles_role_FK FOREIGN KEY (roles) REFERENCES roles(id);
ALTER TABLE IF EXISTS custom_user_roles ADD CONSTRAINT custom_user_roles_custom_user_id_FK FOREIGN KEY (custom_user_id) REFERENCES users(id);
