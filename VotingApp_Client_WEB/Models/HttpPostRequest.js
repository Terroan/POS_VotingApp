// Define the HttpPostRequest class
export class HttpPostRequest {
    constructor(session, user) {
      this._session = session;
      this._user = user;
    }
  
    // Getter and setter for VotingSession property
    get VotingSession() {
      return this._session;
    }
  
    set VotingSession(session) {
      this._session = session;
    }
  
    // Getter and setter for User property
    get User() {
      return this._user;
    }
  
    set User(user) {
      this._user = user;
    }

    // Method to convert the object to JSON with custom property names
    toJSON() {
        return {
            votingSession: this._session,
            voter: this._user
        };
    }
  }
  