package com.example.revern.task5;

/**
 * Created by Алмаз on 29.09.2015.
 */
public class District {
    String name;
    Size size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public enum Size {
        SMALL, MEDIUM, LARGE
    }
}
