using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VotingApp_Client_WPF
{
    // container class for session data outgoing
    public class VotingSessionEgress
    {
        private string? _sessionTitle;
        private string? _creator;
        private List<VotingQuestion?>? _questions = new();

        public VotingSessionEgress() { }

        [JsonPropertyName("title")]
        public string? SessionTitle { get { return _sessionTitle; } set { _sessionTitle = value; } }
        [JsonPropertyName("creator")]
        public string? Creator { get { return _creator; } set { _creator = value; } }
        [JsonPropertyName("questions")]
        public List<VotingQuestion?>? Questions { get { return _questions; } set { _questions = value; } }
    }
}
