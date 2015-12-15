package com.github.texxel.fontgenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FontGenerator {

    private static List<Glyph> decode( BufferedImage image, char start ) {
        ArrayList<Glyph> glyphs = new ArrayList<>();

        char currentChar = start;

        int startX = -1;
        int miny = image.getHeight();
        int maxy = 0;

        for ( int x = 0; x < image.getWidth(); x++ ) {
            boolean gap = true;

            for ( int y = 0; y < image.getHeight(); y++ ) {
                int color = image.getRGB( x, y );
                int alpha = (color >> 24) & 0xff;
                if ( alpha > 50 ) {
                    gap = false;
                    miny = Math.min( miny, y );
                    maxy = Math.max( maxy, y );
                    if ( startX == -1 )
                        startX = x;
                }
            }

            if ( gap && startX != -1 ) {
                Glyph glyph = new Glyph();
                glyph.character = currentChar;
                glyph.x = startX;
                glyph.y = miny;
                glyph.width = x - startX;
                glyph.height = maxy - miny + 1;
                glyphs.add( glyph );

                startX = -1;
                miny = image.getHeight();
                maxy = 0;
                currentChar++;
            }
        }

        return glyphs;
    }

    public static Font makeFont( File image, char start ) throws IOException {
        Font font = new Font();

        font.name = image.getName().split( "\\." )[0];
        font.image = ImageIO.read( image );
        font.glyphs = decode( font.image, start );
        font.imageFile = image;
        font.size = getTextHeight( font.glyphs );

        return font;
    }

    private static int getTextHeight( List<Glyph> glyphs ) {
        int height = 0;
        for ( Glyph glyph : glyphs ) {
            height = Math.max( height, glyph.y +  glyph.height );
        }
        return height;
    }

    public static void writeBmpFile( File out, Font font ) throws IOException {

        String info = String.format( "info face=\"%s\" size=%d bold=0 italic=1 charset=\"\" unicode=0 stretchH=100 smooth=1 aa=1 padding=0,0,0,0 spacing=0,0",
                font.name, font.size );
        String common = String.format( "common lineHeight=%d base=%d scaleW=%d scaleH=%d pages=1 packed=0",
                font.size, font.size, font.image.getWidth(), font.image.getHeight() );
        String page = String.format( "page id=0 file=\"%s\"", font.imageFile.getName() );
        String chars = String.format( "chars count=%d", font.glyphs.size() );

        StringBuilder output = new StringBuilder( 200 );
        output.append( info ).append( '\n' );
        output.append( common ).append( '\n' );
        output.append( page ).append( '\n' );
        output.append( chars ).append( '\n' );

        for ( Glyph glyph : font.glyphs ) {
            String text = String.format( "char id=%d   x=%d    y=%d     width=%d     height=%d     xoffset=%d     yoffset=%d    xadvance=%d     page=0  chnl=0 ",
                    (int)glyph.character, glyph.x, glyph.y, glyph.width, glyph.height, 0, glyph.y, glyph.width );
            output.append( text ).append( '\n' );
        }

        List<String> lines = Arrays.asList( output.toString().split( "\n" ) );
        Path file = out.toPath();
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

}
