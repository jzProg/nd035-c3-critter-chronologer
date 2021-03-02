CREATE TABLE IF NOT EXISTS USERS (
    user_id BIGINT PRIMARY KEY auto_increment,
    dtype VARCHAR(31),
    name VARCHAR(50),
    phone_number VARCHAR(50),
    notes VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS PET (
    pet_id BIGINT PRIMARY KEY auto_increment,
    type VARCHAR(20),
    name VARCHAR(50),
    birth_date DATE,
    notes VARCHAR(100),
    owner_id BIGINT,
    foreign key (owner_id) references USERS(user_id)
);

CREATE TABLE IF NOT EXISTS SCHEDULE (
    id BIGINT PRIMARY KEY auto_increment,
    activities VARCHAR(20),
    date DATE
);

