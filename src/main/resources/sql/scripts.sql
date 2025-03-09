create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

Insert INTO users VALUES('user','{noop}123',true)
    Insert INTO users VALUES('admin','{bcrypt}$2a$12$7jKwyOzzRm7mZj1BCOZPeO1Fo5dSXBBB67YxijJYJX12vBPVPKy6y',true)




Insert INTO users VALUES('user','{noop}123',true)
Insert INTO users VALUES('admin','{bcrypt}$2a$12$7jKwyOzzRm7mZj1BCOZPeO1Fo5dSXBBB67YxijJYJX12vBPVPKy6y',true)
Insert INTO authorities VALUES('user','read')
Insert INTO authorities VALUES('admin','admin')

