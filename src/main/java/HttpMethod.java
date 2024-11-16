public enum HttpMethod {
    // Used to request data from a server. It is a read-only method and should not alter data on the server
    GET("GET"),
    // Used to send data to the server, often to create a new resource. It can modify the state on the server
    POST("POST"),
    // Used to update or create a resource at a specific location on the server.
    // If the resource does not exist, it will be created
    PUT("PUT"),
    // Deletes a specified resource from the server
    DELETE("DELETE"),
    // Similar to PUT, but used to apply partial updates to a resource instead of completely replacing it
    PATCH("PATCH"),
    // Similar to GET, but only requests the headers of the response without the response body.
    // Useful for checking what a GET request would return without transferring data
    HEAD("HEAD"),
    // Allows the client to discover which HTTP methods and other options are available for a resource on the server
    OPTIONS("OPTIONS"),
    // Used to establish a network connection, usually to set up a secure connection through a proxy
    CONNECT("CONNECT"),
    // Returns the request received by the server, useful for debugging to see what changes (if any) were made
    // to the request by intermediaries
    TRACE("TRACE");

    private final String method;

    public static HttpMethod from(String method) {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            if (httpMethod.method.equalsIgnoreCase(method)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("Invalid HTTP method: " + method);
    }

    private HttpMethod(String method) {
        this.method = method;
    }
}