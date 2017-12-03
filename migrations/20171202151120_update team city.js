
exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.team
    MODIFY COLUMN team_city varchar(45) NOT NULL;`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.team
     MODIFY COLUMN team_city binary(10) NOT NULL;`)
};