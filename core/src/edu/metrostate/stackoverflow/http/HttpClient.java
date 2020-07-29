package edu.metrostate.stackoverflow.http;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

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

    public Player[] getTopScores(String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, GET_REQUEST);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-api-key", API_KEY)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String resStr = response.body().string();
        Gson gson = new Gson();
        Player[] players = gson.fromJson(resStr, Player[].class);
        return players;
    }

}
