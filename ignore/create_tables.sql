USE dibmap;

CREATE TABLE IF NOT EXISTS parties (
    id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) DEFAULT 'I\'\m new to dibmap.',
    rating FLOAT(2,1) DEFAULT 0,
    raters SMALLINT DEFAULT 0,
    is_individual BOOLEAN DEFAULT 1 NOT NULL,
    owner INTEGER UNSIGNED DEFAULT NULL,
    last_logon_datetime DATETIME DEFAULT 0 NOT NULL, 
    creation_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(owner) REFERENCES parties(id) ON DELETE RESTRICT 
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS community_members (
    member_id INTEGER UNSIGNED NOT NULL,
    community_id INTEGER UNSIGNED NOT NULL,

    PRIMARY KEY(member_id, community_id),
    FOREIGN KEY(member_id) REFERENCES parties(id) ON DELETE CASCADE,
    FOREIGN KEY(community_id) REFERENCES parties(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(50) NOT NULL,
    pwd_hash CHAR(60) NOT NULL,
    party_id INTEGER UNSIGNED NOT NULL,
    confirmed BOOLEAN DEFAULT 0 NOT NULL,
    reg_key CHAR(32) NOT NULL,

    UNIQUE(party_id),
    PRIMARY KEY(email),
    FOREIGN KEY (party_id) REFERENCES parties(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS activity_types (
    id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    parent INTEGER UNSIGNED DEFAULT 0,

    PRIMARY KEY(id),
    FOREIGN KEY(parent) REFERENCES activity_types(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS locations (
    id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    longitude FLOAT,
    latitude FLOAT,

    PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS activities (
    id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    creator_id INTEGER UNSIGNED NOT NULL, 
    activity_type_id INTEGER UNSIGNED NOT NULL, 
    location_id INTEGER UNSIGNED NOT NULL,
    description VARCHAR(255),
    intensity TINYINT NOT NULL, 
    
    duration INTEGER UNSIGNED, 
    specific_time TIMESTAMP,
    
    general_time_monday_morning BOOLEAN DEFAULT 0,
    general_time_monday_afternoon BOOLEAN DEFAULT 0,
    general_time_monday_evening BOOLEAN DEFAULT 0,
    general_time_monday_night BOOLEAN DEFAULT 0,
    
    general_time_tuesday_morning BOOLEAN DEFAULT 0,
    general_time_tuesday_afternoon BOOLEAN DEFAULT 0,
    general_time_tuesday_evening BOOLEAN DEFAULT 0,
    general_time_tuesday_night BOOLEAN DEFAULT 0,

    general_time_wednesday_morning BOOLEAN DEFAULT 0,
    general_time_wednesday_afternoon BOOLEAN DEFAULT 0,
    general_time_wednesday_evening BOOLEAN DEFAULT 0,
    general_time_wednesday_night BOOLEAN DEFAULT 0,

    general_time_thursday_morning BOOLEAN DEFAULT 0,
    general_time_thursday_afternoon BOOLEAN DEFAULT 0,
    general_time_thursday_evening BOOLEAN DEFAULT 0,
    general_time_thursday_night BOOLEAN DEFAULT 0,

    general_time_friday_morning BOOLEAN DEFAULT 0,
    general_time_friday_afternoon BOOLEAN DEFAULT 0,
    general_time_friday_evening BOOLEAN DEFAULT 0,
    general_time_friday_night BOOLEAN DEFAULT 0,

    general_time_saturday_morning BOOLEAN DEFAULT 0,
    general_time_saturday_afternoon BOOLEAN DEFAULT 0,
    general_time_saturday_evening BOOLEAN DEFAULT 0,
    general_time_saturday_night BOOLEAN DEFAULT 0,

    general_time_sunday_morning BOOLEAN DEFAULT 0,
    general_time_sunday_afternoon BOOLEAN DEFAULT 0,
    general_time_sunday_evening BOOLEAN DEFAULT 0,
    general_time_sunday_night BOOLEAN DEFAULT 0,

    PRIMARY KEY(id),
    FOREIGN KEY(creator_id) REFERENCES parties(id) ON DELETE CASCADE,
    FOREIGN KEY(activity_type_id) REFERENCES activity_types(id) ON DELETE RESTRICT,
    FOREIGN KEY(location_id) REFERENCES locations(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS activity_attendees (
    activity_id INTEGER UNSIGNED NOT NULL,
    attendee_id INTEGER UNSIGNED NOT NULL,
    rsvp_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    PRIMARY KEY(activity_id, attendee_id),
    FOREIGN KEY(activity_id) REFERENCES activities(id) ON DELETE CASCADE,
    FOREIGN KEY(attendee_id) REFERENCES parties(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reviews (
    reviewer_id INTEGER UNSIGNED NOT NULL,
    reviewee_id INTEGER UNSIGNED NOT NULL,
    activity_id INTEGER UNSIGNED NOT NULL,
    review VARCHAR(255) NOT NULL,
    rating TINYINT UNSIGNED NOT NULL,
    reviewed_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    PRIMARY KEY(reviewer_id, reviewee_id, activity_id),
    FOREIGN KEY(reviewer_id) REFERENCES parties(id) ON DELETE NO ACTION,
    FOREIGN KEY(reviewee_id) REFERENCES parties(id) ON DELETE CASCADE,
    FOREIGN KEY(activity_id) REFERENCES activities(id) ON DELETE NO ACTION
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS messages (
    id INTEGER UNSIGNED AUTO_INCREMENT NOT NULL,
    activity_id INTEGER UNSIGNED NOT NULL,
    sender_id INTEGER UNSIGNED NOT NULL,
    depth SMALLINT UNSIGNED NOT NULL,
    sent_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    body VARCHAR(255) NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(activity_id) REFERENCES activities(id) ON DELETE NO ACTION,
    FOREIGN KEY(sender_id) REFERENCES parties(id) ON DELETE NO ACTION
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS message_receivers (
    message_id INTEGER UNSIGNED NOT NULL,
    receiver_id INTEGER UNSIGNED NOT NULL,

    PRIMARY KEY(message_id, receiver_id),
    FOREIGN KEY(message_id) REFERENCES messages(id) ON DELETE CASCADE,
    FOREIGN KEY(receiver_id) REFERENCES parties(id) ON DELETE CASCADE 
) ENGINE=InnoDB;

