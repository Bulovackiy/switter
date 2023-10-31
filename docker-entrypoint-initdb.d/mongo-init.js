db.createUser(
    {
        user: "admin",
        pwd: "qwerty12345",
        roles: [
            {
                role: "readWrite",
                db: "switter"
            }
        ]
    }
);

db.createCollection('tokens');
db.tokens.createIndex({username: 1}, {unique: true})
db.tokens.createIndex({createDate: 1}, {expireAfterSeconds: 1800})
