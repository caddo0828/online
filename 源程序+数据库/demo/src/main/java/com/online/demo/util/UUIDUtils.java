package com.online.demo.util;

import java.util.UUID;

public class UUIDUtils {
    public static String getId(){
        String id=UUID.randomUUID().toString();
        id=id.replaceAll("-","").toUpperCase();
        return id;
    }
}
