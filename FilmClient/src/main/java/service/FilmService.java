package service;

public class FilmService {
    // Address Info
    public static final String HOST = "localhost";
    public static final int PORT = 41235;

    // Protocol Requests
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String RATE = "rate";
    public static final String SEARCHNAME = "searchByName";
    public static final String SEARCHGENRE = "searchByGenre";
    public static final String EXIT = "exit";
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String SHUTDOWN = "shutdown";
    public static final String RANDOM_FILM = "randomFilm";

    public static final String RANDOM_GENRE_FILM = "randomGenreFilm";
    public static  final  String HIGHEST_GENRE = "highestGenre";


    // Protocol Responses
    public static final String ADDED = "ADDED";
    public static final String REJECTED = "REJECTED";
    public static final String SUCCESSADMIN = "SUCCESS_ADMIN";
    public static final String SUCCESSUSER = "SUCCESS_USER";
    public static final String LOGOUTOUT = "LOGGED_OUT";
    public static final String FAILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";
    public static final String EXISTS = "EXISTS";
    public static final String REMOVED = "REMOVED";
    public static final String NOTFOUND = "NOT_FOUND";
    public static final String INVALIDRATING = "INVALID_RATING_SUPPLIED";
    public static final String NOLOGIN = "NOT_LOGGED_IN";
    public static final String NOMATCH = "NO_MATCH_FOUND";
    public static final String INVALID = "INVALID_REQUEST";
    public static final String SHUTTINGDOWN = "SHUTTING_DOWN";
    public static final String NOPERMS = "INSUFFICIENT_PERMISSIONS";
    public static final String GOODBYE = "GOODBYE";
    public static final String NOFILMGENRE = "NO_FILMS_FOUND_FOR_GENRE";

    // DELIMITER
    public static final String DELIMITER = "%%";

}