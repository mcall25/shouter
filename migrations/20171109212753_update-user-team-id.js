

exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.users
    MODIFY COLUMN team_id varchar(45) NOT NULL;`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.users
     MODIFY COLUMN team_id binary(16) NOT NULL;`)
};