\c catalogs;

insert into categories values (
'2f729dce-3ee1-11ee-be56-0242ac120001', 
	'2023-08-19 16:13:57.039' , 
	null ,
	'2023-08-19 16:13:57.039', 
	null, 
	'Choose your weapon',
	'2023-08-19 16:13:57.039',
	null,
	'https://assetstorev1-prd-cdn.unity3d.com/package-screenshot/30028882-bbe7-439f-8ad3-dfe86cc4273a_scaled.jpg',
	'weapons',
	'PUBLIC',
	'VISIBLE',
	null
	
);

insert into categories values (
'2f729dce-3ee1-11ee-be56-0242ac120002', 
	'2023-08-19 16:13:57.039' , 
	null ,
	'2023-08-19 16:13:57.039', 
	null, 
	'Best defence here',
	'2023-08-19 16:13:57.039',
	null,
	'https://assetstorev1-prd-cdn.unity3d.com/package-screenshot/41275d50-8c7b-495b-81a9-fc1276baf10c_scaled.jpg',
	'armors',
	'PUBLIC',
	'VISIBLE',
	null
	
);



insert into categories values (
'2f729dce-3ee1-11ee-be56-0242ac120003', 
	'2023-08-19 16:13:57.039' , 
	null ,
	'2023-08-19 16:13:57.039', 
	null, 
	'Be best gic',
	'2023-08-19 16:13:57.039',
	null,
	'https://www.renderhub.com/mykola1985/sci-fi-robot-head/sci-fi-robot-head-01.jpg',
	'technologies',
	'PUBLIC',
	'VISIBLE',
	null
	
);



insert into categories values (
'2f729dce-3ee1-11ee-be56-0242ac120004', 
	'2023-08-19 16:13:57.039' , 
	null ,
	'2023-08-19 16:13:57.039', 
	null, 
	'Bigger, stronger, Powerfull',
	'2023-08-19 16:13:57.039',
	null,
	'https://cdna.artstation.com/p/assets/images/images/027/740/874/large/alex-ichim-disabled-tank.jpg',
	'vehicles',
	'PUBLIC',
	'VISIBLE',
	null
	
);

INSERT INTO deals (id, dt_create, dt_delete, dt_update, meta, discount_enabled, discount_percent, dt_from, dt_to, limited_time_deal_timer, status, type)
VALUES
(
    '7f729dce-3ee1-11ee-be56-0242ac120000',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00', 
    NULL, 
    true, 
    15.00,
    '2023-09-01 00:00:00',
    '2024-09-15 23:59:59',
    true,
	'ACTIVE',
    'DISCOUNT'
);


INSERT INTO deals (id, dt_create, dt_delete, dt_update, meta, discount_enabled, discount_percent, dt_from, dt_to, limited_time_deal_timer, status, type)
VALUES
(
    '7f729dce-3ee1-11ee-be56-0242ac120001',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00', 
    NULL, 
    true, 
    20.00,
    '2023-09-01 00:00:00',
    '2024-09-15 23:59:59',
    true,
	'ACTIVE',
    'FLASH'
);


INSERT INTO deals (id, dt_create, dt_delete, dt_update, meta, discount_enabled, discount_percent, dt_from, dt_to, limited_time_deal_timer, status, type)
VALUES
(
    '7f729dce-3ee1-11ee-be56-0242ac120002',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00', 
    NULL, 
    true, 
    20.00,
    '2023-09-01 00:00:00',
    '2024-09-15 23:59:59',
    true,
	'ACTIVE',
    'SALE'
);


INSERT INTO characteristics (id, dt_create, dt_delete, dt_update, meta, description) VALUES (
	'7f729dce-3ee1-11ee-be56-0242ac120000',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00',
    NULL, 
	'Unleash devastation with the plasma-infused particle blaster, a cutting-edge sci-fi weapon.'
);


INSERT INTO characteristics (id, dt_create, dt_delete, dt_update, meta, description) VALUES (
	'7f729dce-3ee1-11ee-be56-0242ac120001',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00',
    NULL, 
	'Plasma-Infused Particle Blaster: Unleash devastation with the plasma-infused particle blaster, a cutting-edge sci-fi weapon.'
);

INSERT INTO characteristics (id, dt_create, dt_delete, dt_update, meta, description) VALUES (
	'7f729dce-3ee1-11ee-be56-0242ac120002',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00',
    NULL, 
	'The nebulas wont be your only source of awe. Engage enemies with the neutrino disruptor rifle, a marvel of advanced technology.'
);



INSERT INTO items (id, dt_create, dt_delete, dt_update, meta, dt_from, dt_to, price, privacy, summary, title, title_picture, type, visibility, characteristics_id)
VALUES
(
    '5f729dce-3ee1-11ee-be56-0242ac120001',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00', 
    NULL,
    40000, 
    'PUBLIC',
	'Plasma rifle',
    'Neutrino Disruptor Rifle',
    'https://assetstorev1-prd-cdn.unity3d.com/package-screenshot/928a4f6e-ee4f-4605-844c-61b006a0519e_scaled.jpg', 
    'Weapon', 
    'VISIBLE', 
    '7f729dce-3ee1-11ee-be56-0242ac120001'
);


INSERT INTO items (id, dt_create, dt_delete, dt_update, meta, dt_from, dt_to, price, privacy, summary, title, title_picture, type, visibility, characteristics_id)
VALUES
(
    '5f729dce-3ee1-11ee-be56-0242ac120002',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00', 
    NULL,
    49999, 
    'PUBLIC',
	'Plasma rifle',
    'Nebulas',
    'https://cdn1.epicgames.com/ue/product/Screenshot/05-1920x1080-b384f633c7ad5772a62c3217ec5658a0.jpg', 
    'Weapon', 
    'VISIBLE', 
    '7f729dce-3ee1-11ee-be56-0242ac120002'
);




INSERT INTO items (id, dt_create, dt_delete, dt_update, meta, dt_from, dt_to, price, privacy, summary, title, title_picture, type, visibility, characteristics_id)
VALUES
(
    '5f729dce-3ee1-11ee-be56-0242ac120000',
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00',
    NULL, 
    '2023-08-31 00:00:00', 
    NULL,
    20000, 
    'PUBLIC',
    'A powerful weapon for futuristic battles.',
    'Sci-Fi Game Weapon',
    'https://i.pinimg.com/originals/47/33/1d/47331dcf38b84b9d0287a9bbb851c81e.jpg', 
    'Weapon', 
    'VISIBLE', 
    '7f729dce-3ee1-11ee-be56-0242ac120000'
);


INSERT INTO items_categories (item_id, categories_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120000', '2f729dce-3ee1-11ee-be56-0242ac120001');
INSERT INTO items_categories (item_id, categories_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120001', '2f729dce-3ee1-11ee-be56-0242ac120001');
INSERT INTO items_categories (item_id, categories_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120002', '2f729dce-3ee1-11ee-be56-0242ac120001');

INSERT INTO items_deals (item_id, deals_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120000', '7f729dce-3ee1-11ee-be56-0242ac120000');
INSERT INTO items_deals (item_id, deals_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120001', '7f729dce-3ee1-11ee-be56-0242ac120001');
INSERT INTO items_deals (item_id, deals_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120001', '7f729dce-3ee1-11ee-be56-0242ac120000');
INSERT INTO items_deals (item_id, deals_id) VALUES ('5f729dce-3ee1-11ee-be56-0242ac120002', '7f729dce-3ee1-11ee-be56-0242ac120002');




INSERT INTO item_pictures (item_id, pictures) VALUES (
	'5f729dce-3ee1-11ee-be56-0242ac120002',
	'https://cdn1.epicgames.com/ue/product/Featured/LightSifiWeapons_featured-894x488-eafbdc28e5ef9b87828f9874c2889fd1.png'
);

INSERT INTO item_pictures (item_id, pictures) VALUES (
	'5f729dce-3ee1-11ee-be56-0242ac120002',
	'https://cdn1.epicgames.com/ue/product/Screenshot/10-1920x1080-75a792e41643dbb12333b6e1c04b716f.jpg'
);