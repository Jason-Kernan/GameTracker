drop table if exists users, roles, game, game_launcher, launcher, game_platform, platform cascade;

create table users (
	username varchar (250) primary key,
	password varchar (250)
);

create table roles (
	username varchar(250) references users(username),
	role varchar (250),
	primary key (username, role)
);

create table game (
	game_id serial primary key,
	game_name varchar (250) not null,
	studio varchar (250) not null,
	hours_played int not null,
	username varchar (250) not null
);
create table launcher ( 
	launcher_id serial not null  primary key,
	launcher_name  varchar (250) not null
);

create table game_launcher (
	game_id int not null references game(game_id),
	launcher_id serial not null references launcher(launcher_id)
);

create table platform (
	platform_id serial primary key,
	platform_name varchar (250) not null
);

create table game_platform(
	game_id int not null references game(game_id) ,
	platform_id int not null references platform(platform_id) 
);




insert into users (username, password) values ('Jason', 'Admin');
insert into roles (username, role) values ('Jason', 'ADMIN');



insert into game (game_name, studio, hours_played, username)
values 
('PlayerUnknowns Battle Grounds', 'Krafton', 300, 'Jason'),
('Skyrim', 'Bethesda', 600, 'Jason'),
('Minecraft', 'Microsoft', 400, 'Jason'),
('Spider-Man', 'Insomniac', 57, 'Jason');

insert into launcher (launcher_name) values ('Steam'), ('Epic'), ('Game Pass');

insert into platform (platform_name) values ('Xbox One'), ('Playstation 5'), ('Nintendo Switch'), ('PC');

insert into game_platform (game_id, platform_id) values (1, 1), (3, 4), (4, 2), (1,4);

insert into game_launcher (game_id, launcher_id) values (2, 1), (1,1), (3,3);















