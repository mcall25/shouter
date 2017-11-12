

exports.up = function(knex, Promise) {
    return knex.schema.raw(`CREATE TABLE shouter.games(
entity_id VARCHAR(40) NOT NULL,
location_state VARCHAR(10)  NOT NULL,
location_city VARCHAR(15) NOT NULL,
winner_person_id VARCHAR(40) NOT NULL,
losser_person_id VARCHAR(40) NOT NULL,
date_sent DATETIME NOT NULL,
PRIMARY KEY (entity_id));`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`DROP TABLE shouter.games`)
};
