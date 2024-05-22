// Define the VotingPost class
class VotingPost {
    constructor() {
      this._voter = null;
      this._votes = null;
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
  }
  