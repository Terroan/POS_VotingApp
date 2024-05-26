// Define the VotingPost class
export class VotingPost {
    constructor() {
      this._voter = "";
      this._votes = {};
    }
  
    get Voter() {
      return this._voter;
    }
  
    set Voter(value) {
      this._voter = value;
    }
  
    get Votes() {
      return this._votes;
    }
  
    set Votes(value) {
      this._votes = value;
    }

    // Method to convert the object to JSON with custom property names
    toJSON() {
      return {
          voter: this._voter,
          votes: this._votes
      };
  }
  }
  