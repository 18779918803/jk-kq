package com.jk.common.util;

import com.jk.common.bean.ReturnBean;
import com.jk.common.exception.KrtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 缪隽峰
 * @version 1.0
 * @Description: HTTP 访问工具类
 * @date 2016年4月19日
 *
 * @version 1.1
*  @author 温龙飞
*  @date 2020年7月1日
 */
@Slf4j
public class HttpUtils {

    /**
     * 编码
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 错误编码
     */
    private static final int HTTP_CODE = 300;

    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";

    private static final String HTTP_PUT = "PUT";

    /**
     * Send GET request
     */
    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), HTTP_GET, headers);
            conn.connect();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String jsonGet(String url, Map<String, String> params) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return get(url, params, headers);
    }


    /**
     * Send POST request
     */
    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), HTTP_POST, headers);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes(CHARSET));
            out.flush();
            out.close();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, null);
    }

    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    public static String post(String url, String data) {
        return post(url, null, data, null);
    }

    public static String jsonPost(String url, String data) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return post(url, null, data, headers);
    }

    public static String jsonPost(String url, Map<String, String> headers, String data) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Content-Type", "application/json");
        return post(url, null, data, headers);
    }

    /**
     * Send POST request
     */
    public static String put(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), HTTP_PUT, headers);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes(CHARSET));
            out.flush();
            out.close();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String jsonPut(String url, String data) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return put(url, null, data, headers);
    }


    /**
     * https 域名校验
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = {new TrustAnyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new TrustAnyHostnameVerifier();

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws Exception {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection) conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        conn.setUseCaches(false); // Post 请求不能使用缓存
        if (headers != null) {
            String contentType = headers.get("Content-Type");
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(contentType)) {
                conn.setRequestProperty("Content-Type", contentType);
            } else {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            }
        }
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 传入URL和一个Map集合，并拼装成为一个GET请求
     *
     * @param url
     * @param queryParas
     * @return
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }
        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            if (!StringUtils.isEmpty(value)) {
                try {
                    value = URLEncoder.encode(value, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                sb.append(key).append("=").append(value);
            }
        }
        return sb.toString();
    }

    public static String postDirect(String url, String params) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {

        StringBuffer bufferRes = null;
        URL urlPost = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlPost.openConnection();
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        OutputStream out = http.getOutputStream();
        out.write(params.getBytes(CHARSET));
        out.flush();
        out.close();
        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, CHARSET));
        String valueString = null;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        in.close();
        if (http != null) {
            // 关闭连接
            http.disconnect();
        }
        return bufferRes.toString();
    }


    /**
     * GET请求
     *
     * @param url       请求地址
     * @param headerMap header请求参数
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map headerMap) throws Exception {
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        setHeader(connection, headerMap);
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine;
        if (httpURLConnection.getResponseCode() >= HTTP_CODE) {
            throw new KrtException(ReturnBean.error("Get请求失败,服务器响应码是{}" + httpURLConnection.getResponseCode()));
        }
        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }


    /**
     * POST请求
     *
     * @param url          请求参数
     * @param parameterMap 参数Map
     * @param headerMap    header参数
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map parameterMap, Map headerMap) throws Exception {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator<String> iterator = parameterMap.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        log.debug("POST请求参数 : " + parameterBuffer.toString());
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameterBuffer.length()));
        setHeader(httpURLConnection, headerMap);
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine;
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(parameterBuffer.toString());
            outputStreamWriter.flush();
            if (httpURLConnection.getResponseCode() >= HTTP_CODE) {
                throw new KrtException(ReturnBean.error("Post请求失败, 服务器响应码是{}" + httpURLConnection.getResponseCode()));
            }
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }

    /**
     * json请求
     *
     * @param url           请求地址
     * @param jsonParameter json字符串参数
     * @param headerMap       header参数
     * @return
     * @throws Exception
     */
    public static String doPostJsonStr(String url, String jsonParameter, Map headerMap) throws Exception {
        log.debug("POST请求参数 : " + jsonParameter.toString());
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        httpURLConnection.setRequestProperty("Content-Type", "text/html");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(jsonParameter.length()));
        setHeader(httpURLConnection, headerMap);
        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine;
        try {
            outputStream = httpURLConnection.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.write(jsonParameter.getBytes("UTF-8"));
            dataOutputStream.flush();
            log.debug(httpURLConnection.getResponseMessage());
            if (httpURLConnection.getResponseCode() >= HTTP_CODE) {
                throw new KrtException(ReturnBean.error("Post请求失败, 服务器响应码是"+httpURLConnection.getResponseCode()));
            }
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        } finally {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return resultBuffer.toString();
    }


    /**
     * 上传文件
     *
     * @param url          请求地址
     * @param parameterMap 参数
     * @param fileMap      文件参数
     * @param headerMap    header参数
     * @return
     * @throws Exception
     */
    public static String doUpload(String url, Map<String, String> parameterMap, Map<String, String> fileMap, Map headerMap) throws Exception {
        // boundary就是request头和上传文件内容的分隔符
        String boundary = "---------------------------123821742118716";
        URL localURL = new URL(url);
        HttpURLConnection httpURLConnection;
        httpURLConnection = (HttpURLConnection) localURL.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(30000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        setHeader(httpURLConnection, headerMap);
        OutputStream out = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        try {
            out = new DataOutputStream(httpURLConnection.getOutputStream());
            // text
            if (parameterMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = parameterMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet()
                        .iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = "application/octet-stream";
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            out = null;
            if (httpURLConnection.getResponseCode() >= HTTP_CODE) {
                throw new KrtException(ReturnBean.error("上传文件失败, 服务器响应码是{}" + httpURLConnection.getResponseCode()));
            }
            // 读取返回数据
            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                resultBuffer.append(line).append("\n");
            }
            reader.close();
            reader = null;
        } finally {
            if (out != null) {
                out.close();
            }
            if (reader != null) {
                reader.close();
            }
            httpURLConnection.disconnect();
        }
        return resultBuffer.toString();
    }

    /**
     * 设置头属性
     *
     * @param connection
     * @param headerMap
     */

    private static void setHeader(URLConnection connection, Map headerMap) {
        if (headerMap != null) {
            Iterator<String> iterator = headerMap.keySet().iterator();
            String key;
            String value;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = headerMap.get(key).toString();
                connection.setRequestProperty(key, value);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(doPost("http://localhost:8080/oauth/authorize?response_type=code&scope=read&client_id=mobile-client&redirect_uri=http://www.baidu.com",null,null));

        //System.out.printf(doPost("http://localhost:8080/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=password&scope=read&username=admin&password=123456",null,null));

        //System.out.println(doPost("http://localhost:8080/oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=3870d0dca232c821ae87084789ef16fe",null,null));

    }

}
