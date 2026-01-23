package com.project_vn.sql_masterclass;

import java.io.Serializable;

public class TopicItem implements Serializable {
    public String title;
    public String description;
    public String code;
    public boolean isHeader;

    // Constructor for Section Headers (e.g., "1. Core Concepts")
    public TopicItem(String title) {
        this.title = title;
        this.isHeader = true;
    }

    // Constructor for Topics (e.g., "SELECT Statement")
    public TopicItem(String title, String description, String code) {
        this.title = title;
        this.description = description;
        this.code = code;
        this.isHeader = false;
    }
}