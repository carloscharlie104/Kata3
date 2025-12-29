package software.ulpgc.kata2.io;

import software.ulpgc.kata2.model.Movie;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.GZIPInputStream;

public class RemoteMovieLoader implements MovieLoader {
    private final String url;
    private final Function<String, Movie> deserialize;

    public RemoteMovieLoader(String url, Function<String, Movie> deserialize) {
        this.url = url;
        this.deserialize = deserialize;
    }

    @Override
    public List<Movie> loadAll() {
        try{
            return loadFrom(new URL (url));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(URL url) throws IOException {
        return loadFrom(url.openConnection());
    }

    private List<Movie> loadFrom(URLConnection connection) throws IOException {
        try (BufferedReader inputStream = unzip(connection.getInputStream())) {
            return loadFrom(inputStream);
        }
    }

    private List<Movie> loadFrom(InputStream inputStream) throws IOException {
        return loadFrom(toReader(inputStream));
    }

    private List<Movie> loadFrom(BufferedReader reader) throws IOException {
        List<Movie> movies = new ArrayList<>();
        reader.readLine();
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            Movie movie = deserialize.apply(line);

            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }

    private BufferedReader toReader(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private BufferedReader unzip(InputStream inputStream) throws IOException {
        return new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream)));
    }
}
