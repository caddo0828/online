package com.online.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParentModule {
    private String id;
    private String name;
    private List<SonModules> son;

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

    public List<SonModules> getSon() {
        return son;
    }

    public void setSon(List<SonModules> son) {
        this.son = son;
    }

    @Override
    public String toString() {
        return "ParentModule{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", son=" + son +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentModule that = (ParentModule) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, son);
    }

    public ParentModule(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
