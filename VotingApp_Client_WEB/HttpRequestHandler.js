// Define the RequestType enum
export const RequestType = {
    SignIn: 'SignIn',
    LogIn: 'LogIn',
    CreateSession: 'CreateSession',
    FetchSessions: 'FetchSessions',
    FetchSession: 'FetchSession',
    PostResults: 'PostResults',
    StartSession: 'StartSession',
    EndSession: 'EndSession',
    DeleteSession: 'DeleteSession',
    UpdateSession: 'UpdateSession'
};

const serverPort = "5010";

// Define the HttpRequestHandler class
export class HttpRequestHandler {
    // API Routes
    static createUserRoute = "http://localhost:"+serverPort+"/api/user/create"; //post
    static loginUserRoute = "http://localhost:"+serverPort+"/api/user/login"; //post
    static createSessionRoute = "http://localhost:"+serverPort+"/api/session"; //post
    static startSessionRoute = "http://localhost:"+serverPort+"/api/session/start/"; //post
    static endSessionRoute = "http://localhost:"+serverPort+"/api/session/end/"; //post
    static postResultsRoute = "http://localhost:"+serverPort+"/api/session/results/"; //post
    static fetchSessionsRoute = "http://localhost:"+serverPort+"/api/sessions/user"; //post
    static fetchSessionRoute = "http://localhost:"+serverPort+"/api/session/"; // get
    static deleteSessionRoute = "http://localhost:"+serverPort+"/api/session/"; //delete
    static updateSessionRoute = "http://localhost:"+serverPort+"/api/session/"; //put

    static async sendHttpRequestAsync(requestType, content, routeExtension) {
        // decide which route to use
        let route;
        switch(requestType) {
            case RequestType.SignIn:
                route = this.createUserRoute + routeExtension;
                break;
            case RequestType.LogIn:
                route = this.loginUserRoute + routeExtension;
                break;
            case RequestType.CreateSession:
                route = this.createSessionRoute + routeExtension;
                break;
            case RequestType.FetchSessions:
                route = this.fetchSessionsRoute + routeExtension;
                break;
            case RequestType.FetchSession:
                route = this.fetchSessionRoute + routeExtension;
                break;
            case RequestType.PostResults:
                route = this.postResultsRoute + routeExtension;
                break;
            case RequestType.StartSession:
                route = this.startSessionRoute + routeExtension;
                break;
            case RequestType.EndSession:
                route = this.endSessionRoute + routeExtension;
                break;
            case RequestType.DeleteSession:
                route = this.deleteSessionRoute + routeExtension;
                break;
            case RequestType.UpdateSession:
                route = this.updateSessionRoute + routeExtension;
                break;
            default:
                throw new Error("Unsupported request type: " + requestType);
        }

        // Configuring request options
        const requestOptions = {
            method: '',
            headers: {
                'Content-Type': 'application/json'
            },
            body: content
        };

        // Determine HTTP method based on request type
        switch (requestType) {
            case RequestType.SignIn:
                requestOptions.method = 'POST';
                break;
            case RequestType.LogIn:
                requestOptions.method = 'POST';
                break;
            case RequestType.CreateSession:
                requestOptions.method = 'POST';
                break;
            case RequestType.FetchSessions:
                requestOptions.method = 'POST';
                break;
            case RequestType.PostResults:
                requestOptions.method = 'POST';
                break;
            case RequestType.StartSession:
                requestOptions.method = 'POST';
                break;
            case RequestType.EndSession:
                requestOptions.method = 'POST';
                break;
            case RequestType.UpdateSession:
                requestOptions.method = 'PUT';
                break;
            case RequestType.FetchSession:
                requestOptions.method = 'GET';
                requestOptions.body = null;
                break;
            case RequestType.DeleteSession:
                requestOptions.method = 'DELETE';
                break;
            default:
                throw new Error('Unsupported request type: ' + requestType);
        }

        // Send the HTTP request
        try {
            console.log(content);
            console.log(requestOptions.body);
            const response = await fetch(route, requestOptions);
            return response;
        } catch (error) {
            throw new Error('Failed to fetch: ' + error.message);
        }
    }
}
