package com.example.wct.util;

public class StringUtil {

    public String getString(String name) {
        if (null != name) {
            name = Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
            if (name.contains("-")) {
                name = name.replace("-", " ");
            }
            if (name.split(" ").length > 1) {
                String[] split = name.split(" ");
                name = "";
                for (String word : split) {
                    if (word.length() > 0) {
                        word = Character.toString(word.charAt(0)).toUpperCase() + word.substring(1).toLowerCase();
                        name = name + " " + word;
                    }
                }
            }
            return name.trim();
        }

        return name;
    }

    public String justCapitalize(String name) {
        if (null != name) {
            name = Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
            if (name.split(" ").length > 1) {
                String[] split = name.split(" ");
                name = "";
                for (String word : split) {
                    if (word.length() > 0) {
                        word = Character.toString(word.charAt(0)).toUpperCase() + word.substring(1).toLowerCase();
                        name = name + " " + word;
                    }
                }
            }
            return name.trim();
        }

        return name;
    }
}