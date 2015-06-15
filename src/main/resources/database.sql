create table Users(
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  uniqueId char(36) not null UNIQUE,
  lastname char(16) not null,
  firstJoined datetime not null default now(),
  lastSeen datetime not null default now()
);

create table Punishments(
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  userId int not null,
  type enum("ban","mute","kick") not null,
  moderatorId int not null,
  expires datetime not null,
  message char(240)
);