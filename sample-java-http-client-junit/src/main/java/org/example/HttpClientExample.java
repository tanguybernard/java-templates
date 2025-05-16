package org.example;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.security.cert.X509Certificate;

public class HttpClientExample {

    public static String fetchUrl(String url) throws Exception {
        // Création du client HTTP
        HttpClient client = HttpClientExample.createUnsafeClient();

        // Construction de la requête HTTP GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Envoi de la requête et récupération de la réponse en string
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Vérifier que le statut est 200 OK
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Erreur HTTP : " + response.statusCode());
        }
    }

    public static HttpClient createUnsafeClient() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .build();
    }

    public static void main(String[] args) {
        try {
            String json = fetchUrl("https://swapi.dev/api/people/");
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

