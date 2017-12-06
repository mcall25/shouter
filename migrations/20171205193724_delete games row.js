

exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.games DROP COLUMN location_city;`)
};

exports.down = function(knex, Promise) {

};
