package com.zebra.testclient.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Objects;

@Data
@Builder
public class Message {
    private HttpMethod httpMethod;
    private String path;
    private HttpVersion httpVersion;
    private Map<String, String> headers;
    private String messageBody;

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
}
