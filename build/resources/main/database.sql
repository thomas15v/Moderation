create table Players(
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  uniqueId char(16) not null,
  lastname char(16) not null,
  firstJoined datetime not null default now(),
  lastJoined datetime not null default now()
);

create table Punishments(
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  playerId int not null,
  type enum("ban","mute","kick") not null,
  moderatorId int not null,
  expires datetime not null,
  message char(240)
);