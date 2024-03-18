using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    class VotingPost
    {
        private Voter? _voter;
        private Dictionary<string, int>? _votes;

        [JsonPropertyName("voter")]
        public Voter? Voter { get { return _voter; } set { _voter = value; } }
        [JsonPropertyName("votes")]
        public Dictionary<string, int>? Votes { get { return _votes; } set { _votes = value; } }



    }
}
