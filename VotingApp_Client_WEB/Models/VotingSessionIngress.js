// Define the VotingSessionIngress class
class VotingSessionIngress {
    constructor() {
      this._objectId = "";
      this._title = "";
      this._creator = "";
      this._questions = [];
      this._results = [];
    }
  
    get ObjectID() {
      return this._objectId;
    }
  
    set ObjectID(value) {
      this._objectId = value;
    }
  
    get Title() {
      return this._title;
    }
  
    set Title(value) {
      this._title = value;
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
  
    get Results() {
      return this._results;
    }
  
    set Results(value) {
      this._results = value;
    }
  
    toString() {
      return `${this._objectId} ${this._title} ${this._creator} ${this._questions} ${this._results}`;
    }
  }
  