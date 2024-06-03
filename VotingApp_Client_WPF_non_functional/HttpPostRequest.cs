using System.Text.Json.Serialization;

namespace VotingApp_Client_WPF
{
    // wrapper class for http request
    internal class HttpPostRequest
    {
        private VotingSessionEgress _session;
        private VoterEgress _user;

        public HttpPostRequest(VotingSessionEgress session, VoterEgress user)
        {
            _session = session;
            _user = user;
        }

        [JsonPropertyName("votingSession")]
        public VotingSessionEgress VotingSession { get { return _session; }  set { _session = value; } }
        [JsonPropertyName("voter")]
        public VoterEgress User { get { return _user; } set { _user = value; } }

    }
}
