// Define the VotingQuestion class
export class VotingQuestion {
    constructor() {
      this._question = "";
      this._options = [];
    }
  
    get Question() {
      return this._question;
    }
  
    set Question(value) {
      this._question = value;
    }
  
    get Options() {
      return this._options;
    }
  
    set Options(value) {
      this._options = value;
    }

    // Method to convert the object to JSON with custom property names
    toJSON() {
        return {
            question: this._question,
            options: this._options
        };
    }
  }
  