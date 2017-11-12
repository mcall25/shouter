
exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.team
    MODIFY COLUMN entity_id varchar(45) NOT NULL;`)
};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.team
     MODIFY COLUMN entity_id binary(16) NOT NULL;`)
};