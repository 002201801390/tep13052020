package br.edu.usf;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Randomizes {
    private static URL randomURL;

    static {
        try {
            randomURL = new URL("https://randomuser.me/api/");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Randomizes() {
        throw new AssertionError("No " + Randomizes.class + " instances for you!");
    }

    public static String name() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            final JsonObject name = randomPerson.get("name").getAsJsonObject();
            return name.get("first").getAsString() + " " + name.get("last").getAsString();
        }
        return null;
    }

    public static String address() {
        final JsonObject randomPerson = randomPerson();
        if (randomPerson != null) {
            final JsonObject location = randomPerson.get("location").getAsJsonObject();
            return "ZIP: " + location.get("postcode").getAsString() + " "
                    + location.get("city").getAsString() + " - "
                    + location.get("state").getAsString() + ", "
                    + location.get("country").getAsString();
        }
        return null;
    }

    private static JsonObject randomPerson() {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(randomURL.toString());
            request.addHeader("content-type", "application/json");

            final CloseableHttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity(), "UTF-8");

            return new Gson().fromJson(json, JsonObject.class).get("results").getAsJsonArray().get(0).getAsJsonObject();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
