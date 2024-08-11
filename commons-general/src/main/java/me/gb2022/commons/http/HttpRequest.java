package me.gb2022.commons.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public class HttpRequest {
    private final Map<String, String> headers;
    private final String url;
    private final HttpMethod method;

    public HttpRequest(Map<String, String> headers, String url, HttpMethod method) {
        this.headers = headers;
        this.url = url;
        this.method = method;
    }

    public static Builder https(HttpMethod method, String url) {
        return new Builder(true, method, url);
    }

    public static Builder http(HttpMethod method, String url) {
        return new Builder(false, method, url);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public HttpURLConnection createConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod(this.method.toString());
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");

        for (String k : this.headers.keySet()) {
            con.setRequestProperty(k, this.headers.get(k));
        }

        return con;
    }

    public String request() {
        try {
            String str;
            HttpURLConnection con = createConnection();

            var code = con.getResponseCode();
            if (code != 200) {
                InputStream error = con.getErrorStream();
                str = new String(error.readAllBytes(), StandardCharsets.UTF_8);
                error.close();
                con.disconnect();
                return str;
            }

            InputStream in = con.getInputStream();
            str = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            in.close();
            con.disconnect();
            return str;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class Builder {
        private final Map<String, String> headers = new HashMap<>();
        private final StringBuilder args = new StringBuilder();

        private final StringBuilder url = new StringBuilder();

        private final HttpMethod method;

        public Builder(boolean https, HttpMethod method, String url) {
            this.url.append(https ? "https://" : "http://");
            this.method = method;
            this.url.append(url);
        }

        public Builder path(String path) {
            url.append(path);
            return this;
        }

        public Builder param(String key, String value) {
            this.args.append(key).append("=").append(value).append("&");
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder browserBehavior(boolean extra) {
            header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");

            if (extra) {
                header("Accept-Encoding", "gzip, deflate, br, zst").header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
                header("x-requested-with", "xmlhttprequest");
                header("Content-Type", "application/json");
            }

            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this.headers, this.url + "?" + this.args, this.method);
        }
    }
}
