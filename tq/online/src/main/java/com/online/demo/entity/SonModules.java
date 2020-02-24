package com.online.demo.entity;

public class SonModules {
    private String id;
    private String name;
    private String url;
    private String parent_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "SonModules{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }

    public SonModules(String id, String name, String url, String parent_id) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.parent_id = parent_id;
    }
}
