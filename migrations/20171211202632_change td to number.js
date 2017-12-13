exports.up = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.td
    MODIFY COLUMN td_num INT(5) NOT NULL;`)

};

exports.down = function(knex, Promise) {
    return knex.schema.raw(`ALTER TABLE shouter.td
    MODIFY COLUMN td_num varchar(45) NOT NULL;`)
};