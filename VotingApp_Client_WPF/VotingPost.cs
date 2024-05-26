using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace VotingApp_Client_WPF
{
    // container class for post request outgoing
    public class VotingPost
    {
        private string? _voter;
        private Dictionary<string, int>? _votes;

        public VotingPost() { }

        [JsonPropertyName("voter")]
        public string? Voter { get { return _voter; } set { _voter = value; } }
        [JsonPropertyName("votes")]
        public Dictionary<string, int>? Votes { get { return _votes; } set { _votes = value; } }

    }
}
