

exports.up = function(knex, Promise) {
    return knex.schema.raw(`CREATE TABLE shouter.team(
entity_id BINARY(16) NOT NULL,
team_name VARCHAR(40)  NOT NULL,
team_state VARCHAR(45) NOT NULL,
team_city VARCHAR(10) NOT NULL,
state_name VARCHAR(10) NOT NULL,
PRIMARY KEY (entity_id));`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`DROP TABLE shouter.team`)
};
