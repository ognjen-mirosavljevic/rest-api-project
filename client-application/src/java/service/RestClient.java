/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author ognje
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class RestClient {
    private static final String BASE_URL = "http://localhost:8080/CenterServer/api";

    private static String sendGet(String path) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/" + path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");

            return readResponse(conn);
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private static String sendPost(String path, Map<String, String> params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "/" + path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "text/plain");

            String formData = buildFormData(params);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(formData.getBytes(StandardCharsets.UTF_8));
            }

            return readResponse(conn);
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    private static String buildFormData(Map<String, String> params) throws Exception {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0) sb.append("&");

            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return sb.toString();
    }

    private static String readResponse(HttpURLConnection conn) throws Exception {
        int status = conn.getResponseCode();

        InputStream is = (status >= 200 && status < 400)
                ? conn.getInputStream()
                : conn.getErrorStream();

        if (is == null) {
            return "ERROR;HTTP " + status;
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    public static String getCart() {
        return sendGet("cart/" + Session.IDKor);
    }

    public static String checkUser(String username, String password) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        return sendPost("korisnici/login", params);
    }

    public static String createUser(String username, String password,
                                    String ime, String prezime,
                                    String adresa, int IDGra,
                                    int IDUlo) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("ime", ime);
        params.put("prezime", prezime);
        params.put("adresa", adresa);
        params.put("IDGra", String.valueOf(IDGra));
        params.put("IDUlo", String.valueOf(IDUlo));
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("korisnici/register", params);
    }

    public static String addMoney(int amount, int IDKor) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("amount", String.valueOf(amount));
        params.put("IDKor", String.valueOf(IDKor));
        params.put("IDKorAdmin", String.valueOf(Session.IDKor));
        return sendPost("korisnici/addMoney", params);
    }

    public static String changeAddress(int IDKor, String newAddress, int IDGra) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDKorAdmin", String.valueOf(Session.IDKor));
        params.put("IDKor", String.valueOf(IDKor));
        params.put("newAddress", newAddress);
        params.put("IDGra", String.valueOf(IDGra));
        return sendPost("korisnici/address", params);
    }

    public static String createGrad(String naziv) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("naziv", naziv);
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("gradovi", params);
    }

    public static String getGradovi() {
        return sendGet("gradovi/" + Session.IDKor);
    }

    public static String getUsers() {
        return sendGet("korisnici/" + Session.IDKor);
    }

    public static String createCategory(String naziv, int IDNat) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("naziv", naziv);
        params.put("IDNat", String.valueOf(IDNat));
        return sendPost("artikli/category", params);
    }

    public static String createArticle(String naziv, String opis, double cena, int IDKat) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("naziv", naziv);
        params.put("opis", opis);
        params.put("cena", String.valueOf(cena));
        params.put("IDKat", String.valueOf(IDKat));
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("artikli/create_artikli", params);
    }

    public static String changePrice(int IDArt, int cena) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDArt", String.valueOf(IDArt));
        params.put("cena", String.valueOf(cena));
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("artikli/price", params);
    }

    public static String changeDiscount(int IDArt, int popust) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDArt", String.valueOf(IDArt));
        params.put("popust", String.valueOf(popust));
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("artikli/discount", params);
    }

    public static String getCategories() {
        return sendGet("artikli/categories");
    }

    public static String getArticles() {
        return sendGet("artikli/" + Session.IDKor);
    }

    public static String addToCart(int IDArt, int kolicina) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDArt", String.valueOf(IDArt));
        params.put("IDKor", String.valueOf(Session.IDKor));
        params.put("kolicina", String.valueOf(kolicina));
        return sendPost("cart/cart", params);
    }

    public static String removeFromCart(int IDArt, int kolicina) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDArt", String.valueOf(IDArt));
        params.put("IDKor", String.valueOf(Session.IDKor));
        params.put("kolicina", String.valueOf(kolicina));
        return sendPost("cart/remove_cart", params);
    }

    public static String addToWishlist(int IDArt) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDArt", String.valueOf(IDArt));
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("cart/wishlist", params);
    }

    public static String removeFromWishlist(int IDArt) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDArt", String.valueOf(IDArt));
        params.put("IDKor", String.valueOf(Session.IDKor));
        return sendPost("cart/remove_wishlist", params);
    }

    public static String getWishlist() {
        return sendGet("cart/wishlist/" + Session.IDKor);
    }

    public static String getAllOrders() {
        return sendGet("orders/all");
    }

    public static String getMyOrders() {
        return sendGet("orders/" + Session.IDKor);
    }

    public static String getTransactions() {
        return sendGet("transaction/all");
    }

    public static String makePayment(int IDGra, String adresa) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("IDKor", String.valueOf(Session.IDKor));
        params.put("IDGra", String.valueOf(IDGra));
        params.put("adresa", adresa);
        return sendPost("transaction/payment", params);
    }
}
