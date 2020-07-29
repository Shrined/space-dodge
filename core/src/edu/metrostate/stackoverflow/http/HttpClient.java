package edu.metrostate.stackoverflow.http;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpClient {

    public static final String HIGH_SCORES_URL = "https://babf1979b5.execute-api.us-east-1.amazonaws.com/Live/score";

    private static OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String API_KEY = "eVLtEGqisu7nQ6kidX5nx9YF4QxlmYds2PBS8eAT";
    private static final String GET_REQUEST = "{\"httpMethod\" : \"GET\"}";

    public String addScoreJson(String username, String score) {
        return "{" +
                "\"httpMethod\":\"POST\"," +
                "\"queryStringParameters\": {" +
                "\"username\":\""+username+"\"," +
                "\"score\":"+score+
                "}" +
                "}";
    }

    public String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-api-key", API_KEY)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public List<Player> getTopScores(String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, GET_REQUEST);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-api-key", API_KEY)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return convertScoresToList(response.body().string());
    }

    private List<Player> convertScoresToList(String json) {
        List<Player> players = new ArrayList<>();
        String[] parts = json.split(",");
        String[] usernames = new String[parts.length * 2];
        String[] scores = new String[parts.length * 2];
        for(String part: parts) {
            String[] vals = part.split(":");
            if(vals.length < 2) {
                return null;
            }
            vals[0] = vals[0].substring(vals[0].indexOf("\"") + 1);
            vals[0] = vals[0].substring(0, vals[0].indexOf("\""));
            vals[1] = vals[1].substring(vals[1].indexOf("\"") + 1);
            vals[1] = vals[1].substring(0, vals[1].indexOf("\""));
            players.add(new Player(vals[0], Integer.parseInt(vals[1])));
        }
        Collections.sort(players);
        return players;
    }

}
