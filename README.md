# FontMaker

FontMaker is a very quickly hacked together program that takes an image containing a sequence of characters and generates the bitmap font file the LibGdx reqires to make BitmapFonts (see [BitmapFont(FileHandle)](https://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/g2d/BitmapFont.html#BitmapFont-com.badlogic.gdx.files.FileHandle-)). The image needs to have each character seperated by a full vertical line of white space. Also, the program requires that all characters are on a single line. See the "data/fontN.png" images for examples of input images. The output bitmap file will reference the input image file.

For example use, see Main.java. You'll probably also need to manually edit the settings in
the [FontGenerator.writeBmpFile()](https://github.com/Texxel/FontMaker/blob/master/src/com/github/texxel/fontgenerator/FontGenerator.java#L79) method to get the correct configuration for you. Examples of both input images and output files are in the "data" folder.

This project is public domain except for the assets which are under the same licence as Texxel. 
You can steal whatever code you like; although I can't understand why you would want to :P
