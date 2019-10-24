package com.zebra.testclient.model;

import java.util.Map;
import java.util.Objects;

public class Message {
    private HttpMethod httpMethod;
    private String path;
    private HttpVersion httpVersion;
    private Map<String, String> headers;
    private String messageBody;

    Message(HttpMethod httpMethod, String path, HttpVersion httpVersion, Map<String, String> headers, String messageBody) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    @Override
    public String toString() {
        return "" + printHttpMethod(httpMethod) + printPath(path) + printHttpVersion(httpVersion) + printHeaders(headers) + printMessageBody(messageBody);
    }

    private String printHttpMethod(HttpMethod httpMethod) {
        return Objects.isNull(httpMethod) ? "" : httpMethod.name();
    }

    private String printPath(String path) {
        return Objects.isNull(path) ? "" : " " + path;
    }

    private String printHttpVersion(HttpVersion httpVersion) {
        return Objects.isNull(httpVersion) ? "" : " " + httpVersion.getVersionNumber();
    }

    private String printHeaders(Map<String, String> headers) {
        return Objects.isNull(headers) ? "" : formatHeaders(headers);
    }

    private String formatHeaders(Map<String, String> headers) {
        StringBuilder headersStringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersStringBuilder.append("\r\n")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue());
        }
        return headersStringBuilder.toString();
    }

    private String printMessageBody(String messageBody) {
        return Objects.isNull(messageBody) ? "" : " " + messageBody;
    }

    public static class MessageBuilder {
        private HttpMethod httpMethod;
        private String path;
        private HttpVersion httpVersion;
        private Map<String, String> headers;
        private String messageBody;

        MessageBuilder() {
        }

        public MessageBuilder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public MessageBuilder path(String path) {
            this.path = path;
            return this;
        }

        public MessageBuilder httpVersion(HttpVersion httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public MessageBuilder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public MessageBuilder messageBody(String messageBody) {
            this.messageBody = messageBody;
            return this;
        }

        public Message build() {
            return new Message(httpMethod, path, httpVersion, headers, messageBody);
        }
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMessageBody() {
        return messageBody;
    }
}
