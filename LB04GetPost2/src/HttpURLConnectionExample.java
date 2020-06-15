
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Scott Jr
 */
public class HttpURLConnectionExample {

    public static void main(String[] args) throws Exception {
        HttpURLConnectionExample http = 
            new HttpURLConnectionExample();
        http.sendPost();
        http.sendGet();
    }
    
    private void sendGet() throws Exception {
        String request = "http://127.0.0.1/index";
        URL url = new URL(request);
        HttpURLConnection connection = 
            (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", 
            "Mozilla/5.0");
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);
        if (responseCode == 200) {
            String response = getResponse(connection);
            System.out.println("response: " + 
                response.toString());
        } else {
            System.out.println("Bad Response Code: " + 
                responseCode);
        }
        connection.disconnect();
    }
    
    private void sendPost() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int responseCode;
        URL url = new URL("http://127.0.0.1/index");
        //Map<String,Object> params = new LinkedHashMap<>();
        System.out.println("Type diary entry: ");
        String input = scanner.next();
        //params.put("Entry: ", input);

        /**StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) 
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }**/
        //byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            String response = getResponse(conn);
            System.out.println("response: " + 
                response.toString());
        } else {
            System.out.println("Bad Response Code: " + 
                responseCode);
        }
        conn.setRequestMethod("POST");
        //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.setRequestProperty("User-Agent", "nbClient");
        //conn.getOutputStream().write(input);
        OutputStream toPost = conn.getOutputStream();
        PrintWriter out = new PrintWriter(toPost, true);
        out.println(input);
        conn.disconnect();
    }
    
    private String getResponse(HttpURLConnection connection) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    connection.getInputStream()));) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (IOException ex) {
            // Handle exceptions
        }
        return "";
    }

}
