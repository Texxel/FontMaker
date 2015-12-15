package com.github.texxel.fontgenerator;

import java.io.File;

public class Main {

    public static void main( String[] args ) throws Exception {
        for ( int i = 1; i <= 5; i++ ) {
            File file = new File( "data/font" + i + ".png" );
            Font font = FontGenerator.makeFont( file, '!' );
            FontGenerator.writeBmpFile( new File( "data/font" + i + ".fnt" ), font );
        }
    }

}
