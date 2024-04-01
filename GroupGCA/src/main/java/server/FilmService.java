package server;

public class FilmService {
    // Address Info
    public static final String HOST = "localhost";
    public static final int PORT = 41235;

    // Protocol Commands
    public static final String ADDED = "ADDED";
    public static final String REJECTED = "REJECTED";
    public static final String SUCCESSADMIN = "SUCCESS_ADMIN";
    public static final String SUCCESSUSER = "SUCCESS_USER";
    public static final String LOGOUTOUT = "LOGGED_OUT";
    public static final String FAILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";
    public static final String INVALIDRATING = "INVALID_RATING_SUPPLIED";
    public static final String NOLOGIN = "NOT_LOGGED_IN";
    public static final String NOMATCH = "NO_MATCH_FOUND";

    // DELIMITER
    public static final String DELIMITER = "%%";

}