package com.cy.store.commom.copyUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 要在一台服务器中的Java程序调用另一台服务器上的项目的API
 * 只需要改URL 和 HTTP请求即可
 */
public class APICaller {
    public static void main(String[] args) {
        try {
            // 目标服务器的API端点URL
            String apiEndpoint = "http://192.168.10.225:9528/lckj/index/getNumByModule";

            // 创建URL对象
            URL url = new URL(apiEndpoint);

            // 建立连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 配置HTTP请求
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-access-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJERVZJQ0UiOiJBUFAiLCJ1c2VyTmFtZSI6IjYzNTUxMzIyIiwiZXhwIjoxNzQ3MTkyOTQ5LCJpYXQiOjE2ODQxMjA5NDl9.8Eq0gS2Yz9zHFEVogq703Qw1LN9Lmi_dBKBzuR5lqOU");

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 处理响应结果
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("API call failed. Response Code: " + responseCode);
            }

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
