package com.github.texxel.fontgenerator;

import java.io.File;

public class Main {

    public static void main( String[] args ) throws Exception {
        File file = new File( "data/font2.png" );
        Font font = FontGenerator.makeFont( file, '!' );
        FontGenerator.writeBmpFile( new File( "data/test.txt" ), font );
    }

}
