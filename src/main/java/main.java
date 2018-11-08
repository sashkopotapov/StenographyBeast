import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
//        String word = "dog";
//        encode(word);
        decode("/Users/oleksandrpotapov/PycharmProjects/Stenography/src/main/resources/new_cube.jpg");


    }
    public static void encode(String word)throws IOException {
        String wordBinary = stringToBinary(word);
        //read an image
        BufferedImage img = null;
        File f = null;
        try{
            f = new File("/Users/oleksandrpotapov/PycharmProjects/Stenography/src/main/resources/cube.jpg");
            img = ImageIO.read(f);
        }catch(IOException e){
            System.out.println(e);
        }
        //end reading
        //get image width and height
        int width = img.getWidth();
        int height = img.getHeight();
        int size = width * height;

        //start converting to binary
        String[][] binaryPicture = new String[size][3];
        int counter = 0;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int p = img.getRGB(x, y);
//                int a = (p >> 24) & 0xff;
                String r = Integer.toBinaryString((p >> 16) & 0xff);
                String g = Integer.toBinaryString((p >> 8) & 0xff);
                String b = Integer.toBinaryString(p & 0xff);


                String[] pixel = {r,g,b};
                //adding zeros to numbers so all will be 8 lenghts
                for(int i = 0; i < 3; i++){
                    int num = 8-pixel[i].length();
                    String zero = "0";
                    String zeros = "";
                    for(int j = 1; j<=num;j++){
                        zeros += zero;
                    }
                    pixel[i] = zeros + pixel[i];
                    if(wordBinary.length() > 2){
                    pixel[i] = pixel[i].substring(0, 6) + wordBinary.substring(0, 2);
                    wordBinary = wordBinary.substring(2, wordBinary.length());
                    }

                }

                binaryPicture[counter] = pixel;
                counter++;
            }

        }

//        for(String[] i:binaryPicture){
//            for(String j: i){
//                System.out.println(j);
//            }
//
//        }
        counter = 0;

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {


                int a = 255;
                int r = Integer.parseInt(binaryPicture[counter][0], 2);
                int g = Integer.parseInt(binaryPicture[counter][1], 2);
                int b = Integer.parseInt(binaryPicture[counter][2], 2);

                counter++;
                //set the pixel value
                int p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(x, y, p);

                //write image
                try{
                    f = new File("/Users/oleksandrpotapov/PycharmProjects/Stenography/src/main/resources/new_cube.jpg");
                    ImageIO.write(img, "jpg", f);
                }catch(IOException e){
                    System.out.println(e);
                }
    }
        }
    }
    private static String stringToBinary(String word){
        String len = Integer.toBinaryString(word.length());
        word = word;
        String ascii = "";
        for(int i = 0; i < word.length(); i++){
            int asciiLetter = (int) word.charAt(i);

            String binaryWord = Integer.toBinaryString(asciiLetter);
            int num = 8-binaryWord.length();
            String zero = "0";
            String zeros = "";
            for(int j = 1; j<=num;j++){
                zeros += zero;
            }
            binaryWord = zeros + binaryWord;
            ascii += binaryWord;
            }

        return len + ascii;
    }
    public static void decode(String path){
        String word = "";
        BufferedImage img = null;
        File f = null;
        try{
            f = new File(path);
            img = ImageIO.read(f);
        }catch(IOException e){
            System.out.println(e);
        }
        int width = img.getWidth();
        int height = img.getHeight();
        int size = width * height;

        String[][] binaryPicture = new String[size][3];
        int counter = 0;
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int p = img.getRGB(x, y);
//                int a = (p >> 24) & 0xff;
                String r = Integer.toBinaryString((p >> 16) & 0xff);
                String g = Integer.toBinaryString((p >> 8) & 0xff);
                String b = Integer.toBinaryString(p & 0xff);



                String[] pixel = {r,g,b};
                //adding zeros to numbers so all will be 8 lenghts
                for(int i = 0; i < 3; i++){
                    int num = 8-pixel[i].length();
                    String zero = "0";
                    String zeros = "";
                    for(int j = 1; j<=num;j++){
                        zeros += zero;
                    }
                    pixel[i] = zeros + pixel[i];
                }
                binaryPicture[counter] = pixel;
                counter++;
            }

        }
                for(String[] i:binaryPicture){
            for(String j: i){
                System.out.println(j);
            }

        }
        int len = Integer.parseInt(binaryPicture[0][0], 2);
//        System.out.println(len);
        String binaryWordRepr = "";
        counter = 0;
        while(counter <= len) {

                binaryWordRepr += binaryPicture[counter][0].substring(6, 8);
                binaryWordRepr += binaryPicture[counter][1].substring(6, 8);
                binaryWordRepr += binaryPicture[counter][2].substring(6, 8);
                counter++;

        }
//        System.out.println(binaryWordRepr);


    }
}
