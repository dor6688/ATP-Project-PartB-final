package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    byte[]compressedMaze; // the final compressed maze

    /**
     * Constructor
     * @param os
     */
    public MyCompressorOutputStream(OutputStream os){
        out = os;
    }


    @Override
    public void write(int b) throws IOException { }

    /**
     * Get array of byte
     * @param b
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException {
        String strBinary = "";
        int index ;
        int frameGrater255 = b[0];
        int startIndex = -1;
        if (frameGrater255 == 0)
            startIndex = 7;
        if (frameGrater255 == 1 || frameGrater255 ==2)
            startIndex = 10;
        if (frameGrater255 == 3)
            startIndex = 13;
        int size = (b.length - startIndex)/8;
        if ((b.length - startIndex)%8 == 0)
            compressedMaze = new byte[size + startIndex];
        else{
            compressedMaze = new byte[size + startIndex +1];
        }
        for(index= 0; index<startIndex; index++){
            compressedMaze[index] = b[index];
        }
        int counter = 0;
        for(int j=startIndex; j<b.length;j++){

            if (counter < 8){
                strBinary += (b[j]+"");
                counter++;
            }
            else {
                j--;
                counter = 0;
                byte byteBinary = fromBinaryToDec(strBinary);
                compressedMaze[index++] = byteBinary;
                strBinary = "";

            }
        }
        if (strBinary.length() <= 8){
            while (strBinary.length() != 8)
                strBinary += '0';
            byte byteBinary = fromBinaryToDec(strBinary);
            compressedMaze[index] = byteBinary;
        }
        out.write(compressedMaze);
    }


    private byte fromBinaryToDec(String strBinary) {
        int sum = 0;
        int k=0;
        for (int i=strBinary.length()-1; i>= 0 ;i--){
            int x = (int) (Math.pow(2,k++)*Character.getNumericValue(strBinary.charAt(i)));
            sum += x;
        }
        return (byte) sum;
    }

}
