package com.example.wct.util;

import java.util.logging.Logger;

public class Print {

    private static final Logger LOGGER = Logger.getLogger( Print.class.getName() );

    public void printUrl(String url){
        LOGGER.info(url);
    }
}
