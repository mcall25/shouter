
exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.games
    ADD COLUMN home_team_id VARCHAR(45) NOT NULL;`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.games
    DROP COLUMN home_team_id;`)
};
