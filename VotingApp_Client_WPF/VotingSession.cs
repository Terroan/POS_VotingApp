using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    internal class VotingSession
    {
        private string? _sessionTitle;
        private VotingSessionCreator? _creator = new();
        private List<VotingQuestion?>? _questions = new();
        private string? _password;

        [JsonPropertyName("title")]
        public string? SessionTitle { get { return _sessionTitle; } set { _sessionTitle = value; } }
        [JsonPropertyName("creator")]
        public VotingSessionCreator? Creator { get { return _creator; } }
        [JsonPropertyName("questions")]
        public List<VotingQuestion?>? Questions { get { return _questions; } }
        [JsonPropertyName("password")]
        public string? Password {  get { return _password; } set { _password = value; } }
    }
}
