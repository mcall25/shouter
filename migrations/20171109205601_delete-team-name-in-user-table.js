

exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.users DROP COLUMN team_name;`)
};

exports.down = function(knex, Promise) {

};
