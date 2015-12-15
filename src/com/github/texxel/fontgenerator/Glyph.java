package com.github.texxel.fontgenerator;

public class Glyph {

    public int x, y, width, height;
    public char character;

    @Override
    public String toString() {
        return "(" + character + ": " + x + "," + y + " - " + width + "," + height + ")";
    }
}
