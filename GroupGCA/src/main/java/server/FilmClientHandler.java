package server;

import film_service.business.Film;
import film_service.business.FilmManager;
import film_service.business.User;
import film_service.business.UserManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FilmClientHandler implements Runnable{

    private final FilmManager filmManager;
    private final UserManager userManager;
    private Socket dataSocket;
    private static User currentUser = null;


    public FilmClientHandler(Socket dataSocket, UserManager userManager , FilmManager filmManager) {
        this.dataSocket = dataSocket;
        this.userManager = userManager;
        this.filmManager = filmManager;
    }
    @Override
    public void run() {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                boolean validSession = true;
                while (validSession) {
                    String message = input.nextLine();
                    System.out.println("Message received: " + message);
                    String[] components = message.split(FilmService.DELIMITER);
                    String response = null;
                    switch (components[0]) {
                        case (FilmService.REGISTER):
                            response = register(components);
                            break;
                        case (FilmService.LOGIN):
                            response = login(components);
                            break;
                        case (FilmService.LOGOUT):
                            response = logout(components);
                            break;
                        case (FilmService.RATE):
                            response = rate(components);
                            break;
                        case (FilmService.SEARCHNAME):
                            response = searchName(components);
                            break;
                        case (FilmService.SEARCHGENRE):
                            response = searchGenre(components);
                            break;
                        case (FilmService.RANDOM_FILM):
                            response = getRandomFilm(components);
                            break;
                        case (FilmService.RANDOM_GENRE_FILM):
                            response = getRandomFilmInGenre(components);
                            break;
                        case (FilmService.ADD):
                            response = addFilm(components);
                            break;
                        case (FilmService.REMOVE):
                            response = removeFilm(components);
                            break;
                        case (FilmService.EXIT):
                            if(components.length == 1) {
                                response = FilmService.GOODBYE;
                                validSession = false;
                            } else {
                                response = FilmService.INVALID;
                            }
                            break;
                        case (FilmService.SHUTDOWN):
                            if(components.length == 1) {
                                if (currentUser == null || !currentUser.isAdmin()) {
                                    response = FilmService.NOPERMS;
                                } else {
                                    response = FilmService.SHUTTINGDOWN;
                                    dataSocket.close();
                                }
                            }
                            break;
                        default:
                            response = FilmService.INVALID;
                    }
                    output.println(response);
                    output.flush();
                }
            } catch (NoSuchElementException e) {
                System.out.println("The user sent an invalid request: ");
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("An IOException occurred when trying to communicate with: " + dataSocket.getInetAddress() + ":" + dataSocket.getPort());
                System.out.println(e.getMessage());
            }
        }

    private String register(String[] components) {
        String response;
        if (components.length != 3) {
            response = FilmService.REJECTED;
        } else {
            String username = components[1];
            String password = components[2];
            boolean registrationResult = userManager.addUser(username, password, false);

            if (registrationResult) {
                response = FilmService.ADDED; // User registered successfully
            } else {
                response = FilmService.REJECTED; // Registration failed (username already exists)
            }
        }
        return response;
    }

    private String login(String[] components) {
        String response;
        if (components.length != 3) {
            response = FilmService.REJECTED;
        } else {
            String username = components[1];
            String password = components[2];
            currentUser = userManager.authenticateUser(username, password);
            if (currentUser != null) {
                if(currentUser.isAdmin()){
                    response = FilmService.SUCCESSADMIN;
                } else {
                    response= FilmService.SUCCESSUSER;
                }
            } else {
                response = FilmService.FAILED;
            }
        }
        return response;
    }

    private static String logout(String[] components) {
        String response;
        if (components.length != 1) {
            response = FilmService.REJECTED;
        } else {
            currentUser = null;
            response = FilmService.LOGOUTOUT;
        }
        return response;
    }

    private String rate(String[] components) {
        String response;
        if (components.length < 3) {
            response = FilmService.REJECTED;
        } else {
            String title = components[1];
            try {
                int rating = Integer.parseInt(components[2]);
                    if (currentUser != null) {
                        boolean rated = filmManager.rateFilm(title, rating);
                        if (rated) {
                            response = FilmService.SUCCESS;
                        } else {
                            response = FilmService.INVALIDRATING;
                        }
                    } else {
                        response = FilmService.NOLOGIN;
                    }
            } catch (NumberFormatException e) {
                response = FilmService.INVALIDRATING;
            }
        }
        return response;
    }

    private String searchName(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.INVALID;
        } else {
                String title = components[1];
                Film filmResult = filmManager.searchByTitle(title);
                if (filmResult != null) {
                    response = filmResult.encode("%%");
                } else {
                    response = FilmService.NOMATCH;
                }

        }
        return response;
    }

    private String searchGenre(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.INVALID;
        } else {
            String genre = components[1];
            List<Film> resultList = filmManager.searchByGenre(genre);
            if (!resultList.isEmpty()) {

                    response = filmManager.encode(genre, "~~", "%%");
            } else {
                response = FilmService.NOMATCH;
            }

            }
        return response;
    }

    private String getRandomFilm(String[] components) {
        String response;
        if (components.length != 1) {
            response = FilmService.REJECTED;
        } else {
            Film randomFilm = filmManager.getRandomFilm();
            if (randomFilm != null) {
                return randomFilm.encode("%%");
            } else {
                response = FilmService.NOMATCH;
            }
        }
        return response;
    }

    private String getRandomFilmInGenre(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.REJECTED;
        } else {
            String genre = components[1];
            Film randomFilm = filmManager.getRandomFilmInGenre(genre);
            if (randomFilm != null) {
                return randomFilm.encode("%%");
            } else {
                response = FilmService.NOFILMGENRE;
            }
        }
        return response;
    }



    private String addFilm(String[] components) {
        String response;
        if (components.length != 3) {
            response = FilmService.REJECTED;
        } else {
            if (currentUser == null || !currentUser.isAdmin()) {
                response = FilmService.NOPERMS;
            } else {
                String title = components[1];
                String genre = components[2];
                Film newFilm = new Film(title, genre);
                boolean isAdded = filmManager.addFilm(newFilm);
                if(isAdded) {
                    response = FilmService.ADDED;
                } else {
                    response = FilmService.EXISTS;
                }
            }
        }
        return response;
    }


    private String removeFilm(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.REJECTED;
        } else {
            if (currentUser == null || !currentUser.isAdmin()) {
                response = FilmService.NOPERMS;
            } else {
                String title = components[1];
                boolean isRemoved = filmManager.removeFilm(title);
                if(isRemoved) {
                    response = FilmService.REMOVED;
                } else {
                    response = FilmService.NOTFOUND;
                }
            }
        }
        return response;
    }

    private String searchGenreHighestRating(String[] components) {
        String response;
        if (components.length != 2) {
            response = FilmService.INVALID;
        } else {
            String genre = components[1];
            List<Film> resultList = filmManager.searchByGenre(genre);
            if (!resultList.isEmpty()) {
                Film highestRatedFilm = findHighestRatedFilm(resultList);
                if (highestRatedFilm != null) {
                    response = filmManager.encode(highestRatedFilm.getTitle() + " - Rating: " + highestRatedFilm.getTotalRatings(), "~~", "%%");
                } else {
                    response = "No films found in the genre " + genre;
                }
            } else {
                response = FilmService.NOMATCH;
            }
        }
        return response;
    }

    private Film findHighestRatedFilm(List<Film> films) {
        Film highestRated = null;
        int maxRating = Integer.MIN_VALUE;
        for (Film film : films) {
            if (film.getTotalRatings() > maxRating) {
                highestRated = film;
                maxRating = film.getTotalRatings();
            }
        }
        return highestRated;
    }
}
