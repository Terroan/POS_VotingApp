using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VotingApp_Client_WPF
{
    // container class for session data incoming
    public class VotingSessionIngress
    {
        private string? _objectId;
        private string? _title;
        private string? _creator;
        private List<VotingQuestion?>? _questions;
        private List<VotingPost?>? _results;

        public VotingSessionIngress() { }

        [JsonPropertyName("id")]
        public string? ObjectID { get { return _objectId; } set { _objectId = value; } }
        [JsonPropertyName("title")]
        public string? Title { get { return _title;} set { _title = value; } }
        [JsonPropertyName("creator")]
        public string? Creator { get { return _creator; } set { _creator = value; } }
        [JsonPropertyName("questions")]
        public List<VotingQuestion?>? Questions { get { return _questions; } set { _questions = value; } }
        [JsonPropertyName("results")]
        public List<VotingPost?>? Results { get { return _results; } set { _results = value; } }

        public override string ToString()
        {
            return _objectId+" "+_title+" "+_creator+" "+_questions?.ToString()+" "+_results?.ToString();
        }
    }
}
