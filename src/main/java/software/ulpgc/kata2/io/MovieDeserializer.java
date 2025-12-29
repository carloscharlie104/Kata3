package software.ulpgc.kata2.io;

import software.ulpgc.kata2.model.Movie;

public class MovieDeserializer {
    public static Movie fromTsv(String str) {
        return fromTsv(str.split("\t"));
    }

    public static Movie fromTsv(String[] split) {
        if (split.length < 8) return null;

        return new Movie(split[2], toInt(split[5]), toInt(split[7]));
    }

    private static int toInt(String str) {
        if (str.equals("\\N")) return -1;
        return Integer.parseInt(str);
    }
}
