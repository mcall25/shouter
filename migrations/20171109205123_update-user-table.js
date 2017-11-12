exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.users
    ADD COLUMN state_name VARCHAR(10) NOT NULL,
    ADD COLUMN team_id BINARY(16) NOT NULL;`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.users
    DROP COLUMN state_name,
    DROP COLUMN team_id;`)
};

