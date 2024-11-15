public final class HttpMethods {
    private HttpMethods() {
        // Prevent instantiation
    }

    // Used to request data from a server. It is a read-only method and should not alter data on the server
    public static final String GET = "GET";
    // Used to send data to the server, often to create a new resource. It can modify the state on the server
    public static final String POST = "POST";
    // Used to update or create a resource at a specific location on the server.
    // If the resource does not exist, it will be created
    public static final String PUT = "PUT";
    // Deletes a specified resource from the server
    public static final String DELETE = "DELETE";
    // Similar to PUT, but used to apply partial updates to a resource instead of completely replacing it
    public static final String PATCH = "PATCH";
    // Similar to GET, but only requests the headers of the response without the response body.
    // Useful for checking what a GET request would return without transferring data
    public static final String HEAD = "HEAD";
    // Allows the client to discover which HTTP methods and other options are available for a resource on the server
    public static final String OPTIONS = "OPTIONS";
    // Used to establish a network connection, usually to set up a secure connection through a proxy
    public static final String CONNECT = "CONNECT";
    // Returns the request received by the server, useful for debugging to see what changes (if any) were made
    // to the request by intermediaries
    public static final String TRACE = "TRACE";
}
