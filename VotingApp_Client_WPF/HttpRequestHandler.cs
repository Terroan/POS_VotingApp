using System;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace VotingApp_Client_WPF
{
    public enum RequestType
    {
        SignIn,
        LogIn,
        CreateSession,
        FetchSessions,
        FetchSession,
        PostResults,
        StartSession,
        EndSession,
        DeleteSession,
        UpdateSession
    }

    internal class HttpRequestHandler
    {
        private const string serverPort = "5010";
        // API - Routes
        //  Users
        private static string createUserRoute = "http://localhost:"+serverPort+"/api/user/create"; //post
        private static string loginUserRoute = "http://localhost:"+serverPort+"/api/user/login"; //post

        // Sessions
        private static string createSessionRoute = "http://localhost:"+serverPort+"/api/session"; //post
        private static string startSessionRoute = "http://localhost:"+serverPort+"/api/session/start/"; //post
        private static string endSessionRoute = "http://localhost:"+serverPort+"/api/session/end/"; //post
        private static string postResultsRoute = "http://localhost:"+serverPort+"/api/session/results/"; //post
        private static string fetchSessionsRoute = "http://localhost:"+serverPort+"/api/sessions/user"; //post
        private static string fetchSessionRoute = "http://localhost:"+serverPort+"/api/session/"; // get
        private static string deleteSessionRoute = "http://localhost:"+serverPort+"/api/session/"; //delete
        private static string updateSessionRoute = "http://localhost:"+serverPort+"/api/session/"; //put

        // HTTP - Client
        private static HttpClient _client = new();

        public static async Task<HttpResponseMessage> SendHttpRequestAsync(RequestType requestType, string content, string routeExtension)
        {
            // decide which route to use
            switch(requestType)
            {
                case RequestType.SignIn: return await SendPostRequestAsync(createUserRoute+routeExtension, content);
                case RequestType.LogIn: return await SendPostRequestAsync(loginUserRoute+routeExtension, content);
                case RequestType.CreateSession: return await SendPostRequestAsync(createSessionRoute+routeExtension, content);
                case RequestType.FetchSessions: return await SendPostRequestAsync(fetchSessionsRoute+routeExtension, content);
                case RequestType.FetchSession: return await SendGetRequestAsync(fetchSessionRoute+routeExtension);
                case RequestType.PostResults: return await SendPostRequestAsync(postResultsRoute+routeExtension, content);
                case RequestType.StartSession: return await SendPostRequestAsync(startSessionRoute+routeExtension, content);
                case RequestType.EndSession: return await SendPostRequestAsync(endSessionRoute+routeExtension, content);
                case RequestType.DeleteSession: return await SendDeleteRequestAsync(deleteSessionRoute+routeExtension, content);
                case RequestType.UpdateSession: return await SendPutRequestAsync(updateSessionRoute+routeExtension, content);
            }
            throw new NotSupportedException("Unsupported request type: " + requestType.ToString());
        }

        private static async Task<HttpResponseMessage> SendGetRequestAsync(string route)
        {
            // send get request
            return await _client.GetAsync(route);
        }

        private static async Task<HttpResponseMessage> SendPostRequestAsync(string route, string content)
        {
            // send post request
            return await _client.PostAsync(route, new StringContent(content, Encoding.UTF8, "application/json"));
        }

        private static async Task<HttpResponseMessage> SendDeleteRequestAsync(string route, string content)
        {
            // config delete request (_client.DeleteAsync() doesnt provide requestbody functionality)
            var request = new HttpRequestMessage
            {
                Method = HttpMethod.Delete,
                RequestUri = new Uri(route),
                Content = new StringContent(content, Encoding.UTF8, "application/json")
            };

            // send delete request
            return await _client.SendAsync(request);
        }

        private static async Task<HttpResponseMessage> SendPutRequestAsync(string route, string content)
        {
            // send delete request
            return await _client.PutAsync(route, new StringContent(content, Encoding.UTF8, "application/json"));
        }
    }
}
