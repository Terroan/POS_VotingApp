// Define the VotingSessionEgress class
export class VotingSessionEgress {
    constructor() {
      this._sessionTitle = "";
      this._creator = "";
      this._questions = [];
    }
  
    get SessionTitle() {
      return this._sessionTitle;
    }
  
    set SessionTitle(value) {
      this._sessionTitle = value;
    }
  
    get Creator() {
      return this._creator;
    }
  
    set Creator(value) {
      this._creator = value;
    }
  
    get Questions() {
      return this._questions;
    }
  
    set Questions(value) {
      this._questions = value;
    }

        // Method to convert the object to JSON with custom property names
        toJSON() {
            return {
                title: this._sessionTitle,
                creator: this._creator,
                questions: this._questions
            };
        }
  }
  