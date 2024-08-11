package me.gb2022.commons.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HTTPUtil {
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
    String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7";

    static HttpURLConnection getHttpURLConnection(String url, boolean extraHeads) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", ACCEPT);
        if (extraHeads) {
            con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, br, zst");

            con.setRequestProperty("x-requested-with", "xmlhttprequest");
            con.setRequestProperty("Content-Type", "application/json");
        }
        return con;
    }
}
