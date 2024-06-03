using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    public class VontingSessionIngress
    {
        private string? _sessionID;
        private string? _title;
        private VotingSessionCreator? _creator;
        private List<VotingQuestion?>? _questions;
        private List<VotingPost?>? _results;

        public VontingSessionIngress() { }

        [JsonPropertyName("sessionID")]
        public string? SessionID { get { return _sessionID; } set { _sessionID = value; } }
        [JsonPropertyName("title")]
        public string? Title { get { return _title;} set { _title = value; } }
        [JsonPropertyName("creator")]
        public VotingSessionCreator Creator { get { return _creator; } set { _creator = value; } }
        [JsonPropertyName("questions")]
        public List<VotingQuestion?>? Questions { get { return _questions; } set { _questions = value; } }
        [JsonPropertyName("results")]
        public List<VotingPost?>? Results { get { return _results; } set { _results = value; } }

    }
}
