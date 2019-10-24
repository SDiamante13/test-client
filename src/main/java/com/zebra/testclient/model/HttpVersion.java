package com.zebra.testclient.model;

public enum HttpVersion {
    ZERO_DOT_NINE("HTTP/0.9"),
    ONE_DOT_ZERO("HTTP/1.0"),
    ONE_DOT_ONE("HTTP/1.1"),
    TWO_DOT_ZERO("HTTP/2.0");

    private String versionNumber;

    HttpVersion(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }
}
