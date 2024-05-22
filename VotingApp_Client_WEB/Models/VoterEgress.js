// Define a class for user data outgoing
export class VoterEgress {
    constructor() {
        this._name = null;
        this._password = null;
    }

    // Getter and setter for the 'name' property
    get Name() {
        return this._name;
    }
    set Name(value) {
        this._name = value;
    }

    // Getter and setter for the 'password' property
    get Password() {
        return this._password;
    }
    set Password(value) {
        this._password = value;
    }

    // Method to convert the object to JSON with custom property names
    toJSON() {
        return {
            name: this._name,
            password: this._password
        };
    }
}
