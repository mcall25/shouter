

exports.up = function(knex, Promise) {
    return knex.schema.raw(`CREATE TABLE shouter.yds(
entity_id VARCHAR(40) NOT NULL,
person_id VARCHAR(40)  NOT NULL,
game_id VARCHAR(40) NOT NULL,
yds_num VARCHAR(10) NULL,
PRIMARY KEY (entity_id));`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`DROP TABLE shouter.yds`)
};
