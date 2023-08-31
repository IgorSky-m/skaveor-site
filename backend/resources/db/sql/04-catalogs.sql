\c catalogs;


create table categories (
	id uuid not null,
	dt_create timestamp(6), 
	dt_delete timestamp(6), 
	dt_update timestamp(6), 
	meta varchar(255), 
	
	description varchar(255), 
	dt_from timestamp(6), 
	dt_to timestamp(6), 
	icon varchar(255), 
	name varchar(255), 
	
	privacy varchar(255), 
	visibility varchar(255), 
	
	parent_category_id uuid, 
	
	primary key (id)

);


create table characteristics (
	id uuid not null, 
	dt_create timestamp(6), 
	dt_delete timestamp(6), 
	dt_update timestamp(6), 
	meta varchar(255), 
	description varchar(255), 
	primary key (id)
);



create table deals (
	id uuid not null, 
	dt_create timestamp(6), 
	dt_delete timestamp(6), 
	dt_update timestamp(6), 
	meta varchar(255), 
	discount_enabled boolean not null, 
	discount_percent numeric(38,2), 
	dt_from timestamp(6), 
	dt_to timestamp(6), 
	limited_time_deal_timer boolean not null, 
	status smallint, 
	type smallint, 
	primary key (id)
);


create table items (
	id uuid not null, 
	dt_create timestamp(6), 
	dt_delete timestamp(6), 
	dt_update timestamp(6), 
	meta varchar(255), 
	dt_from timestamp(6), 
	dt_to timestamp(6), 
	price bigint, 
	privacy varchar(255), 
	summary varchar(255), 
	title varchar(255), 
	title_picture varchar(255), 
	type varchar(255), 
	visibility varchar(255), 
	characteristics_id uuid, 
	primary key (id)
);



create table items_categories (
	item_id uuid not null, 
	categories_id uuid not null
);


create table items_deals (
	item_id uuid not null, 
	deals_id uuid not null
);

create table item_pictures (
	item_id uuid not null, 
	pictures varchar(255)
);



alter table if exists categories add constraint catalog_categories_parent_category_id_FK foreign key (parent_category_id) references categories;


alter table if exists items_categories add constraint catalog_item_categories_categories_id_FK foreign key (categories_id) references categories;


alter table if exists items_deals add constraint catalog_items_deals_unic_deals_CONST unique (deals_id);

alter table if exists items add constraint catalog_items_characteristics_id_FK foreign key (characteristics_id) references characteristics;
alter table if exists items_categories add constraint catalog_items_categories_item_id_FK foreign key (item_id) references items;
alter table if exists items_deals add constraint catalog_items_deals_deals_id_FK foreign key (deals_id) references deals;
alter table if exists items_deals add constraint catalog_items_deals_item_id_FK foreign key (item_id) references items;
alter table if exists item_pictures add constraint catalog_items_pictures_item_id_FK foreign key (item_id) references items;