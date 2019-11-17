package main.com.view;


import main.com.entities.Movie;
import main.com.entities.MovieShowing;
import main.com.services.CineplexQueryService;
import main.com.services.MovieQueryService;


import main.com.entities.*;


import java.util.List;
import java.util.Scanner;


public class SearchView {
    Scanner sc = new Scanner(System.in);

    MovieQueryService MQS = new MovieQueryService();
    CineplexQueryService CQS = new CineplexQueryService();

    public void optionsMenu() {
        System.out.println(" 1. Search movie and view details");
        System.out.println(" 2. Search movie by Rating");
        System.out.println(" 3. Search movie by Ticket Sales");
        System.out.println(" 4. Write Review");
        System.out.println(" 5. Get showtimes");
        System.out.println(" -1. 'Quit");
        System.out.println(" ");
    }
    public Movie movieSearch() {
        System.out.print("Enter movie name: ");
        String movie = sc.nextLine();
        List<Movie> movieList = MQS.getMovie(movie);
        if(!movieList.isEmpty()) {
            System.out.println("Movies: ");
            for (Movie value : movieList) {
                System.out.println(value.movie_title);
            }
            System.out.print(" Enter exact name: ");
            movie = sc.nextLine();
            for (Movie value : movieList) {
                if (movie.equals(value.movie_title))
                    return value;
            }
        }
        System.out.println(" Movie not found!");
        System.out.println();
        return null;
    }
    public List<Movie> ratingSearch() {
        System.out.print(" Enter The number of Top Rated Movie: ");
        int number = sc.nextInt();
        sc.nextLine();
        return MQS.getTopRatedMovie(number);
    }
    public List<Movie> ticketSaleSearch() {
        System.out.print(" Enter the number of Popular Movie: ");
        int number = sc.nextInt();
        return MQS.getPopularMovies(number);
    }
    public void writeReview(Movie movie) {
        System.out.print(" Enter review: ");
        String review = sc.nextLine();
        double rating = 0;
        do {
            System.out.print(" Enter rating (1-5): ");
            rating = sc.nextDouble();
            sc.nextLine();
            if (rating < 1 || rating > 5)
                System.out.println("Please try again!");

        }while (rating < 1 || rating > 5);
        MQS.addReviews(movie, review, rating);
    }

    public List<MovieShowing> viewShowtimes(Movie movie) {
        return MQS.getShowtimeTimeslots(movie);
    }
    public void viewMovieDetails(Movie movie) {
        try {
            System.out.println("Title: " + movie.movie_title);
            System.out.println("Duration: " + movie.durationMin);
            System.out.println("Status: " + movie.movieStatus);
            System.out.println("Rating: " + movie.rating);
            System.out.println("Director: " + movie.movie_director);
            System.out.println("Synopsis: " + movie.movie_synopsis);
            System.out.println("Cast: " + movie.cast);
            System.out.println("Reviews: ");
            if(movie.reviewStore!=null) {
                for (Review value : movie.reviewStore.reviews) {
                    System.out.println(value.first + ", Rating: " + value.second);
                }
            }else
                System.out.println("NaN");
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

    public Cineplex getCineplex(){
        List<Cineplex> cineplexList = CQS.getCineplexes();
        System.out.println("Cineplexes available:");
        if (!cineplexList.isEmpty()) {
            for (Cineplex value : cineplexList) {
                System.out.println(value.getCineplexName());
            }
            System.out.print(" Enter exact name: ");
            String cineplex_name = sc.nextLine();
            for (Cineplex value : cineplexList) {
                if (cineplex_name.equalsIgnoreCase(value.getCineplexName())) {
                    return value;
                }
            }
        }
        System.out.println("Cineplex not found");
        return null;
    }

    public Cinema getCinema(Cineplex cineplex){
        List<Cinema> Cinemas = CQS.GetCinemas(cineplex);
        System.out.println("Cinemas:");
        if(!Cinemas.isEmpty()) {
            for (Cinema value : Cinemas) {
                System.out.println(value.getName());
            }
            System.out.print(" Enter exact name: ");
            String cineplex_name = sc.nextLine();
            for (Cinema value : Cinemas) {
                if (cineplex_name.equalsIgnoreCase(value.getName())) {
                    return value;
                }
            }
        }
        System.out.println("No Cinema Found");
        return null;
    }

    public List<MovieShowing> getMovie(Cinema cinema){
        return CQS.GetShows(cinema);
    }
}

