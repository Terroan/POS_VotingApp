using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    class VontingSessionIngress
    {
        private string? _sessionID;
        private string? _title;
        private VotingSessionCreator? _creator;
        private List<VotingQuestion?>? _questions;
        private List<VotingPost?>? _results;

        [JsonPropertyName("sessionID")]
        public string? SessionID { get { return _sessionID; } set { _sessionID = value; } }
        [JsonPropertyName("title")]
        public string? Title { get { return _title;} set { _title = value; } }
        [JsonPropertyName("creator")]
        public VotingSessionCreator Creator { get { return _creator; } }
        [JsonPropertyName("questions")]
        public List<VotingQuestion?>? Questions { get { return _questions; } }
        [JsonPropertyName("results")]
        public List<VotingPost?>? Results { get { return _results; } }

    }
}
