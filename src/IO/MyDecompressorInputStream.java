package IO;

import java.io.IOException;
import java.io.InputStream;


public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream is){
        in = is;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    public int read(byte [] b) throws IOException {
        int stop = 0;
        int index;
        b[0] = (byte)in.read();

        if (b[0] == 0)
            stop = 7;
        if(b[0]==1 || b[0]==2)
            stop = 10;
        if(b[0] == 3)
            stop = 13;
        for(index =1; index<stop ;index++){
            b[index] = (byte)in.read();
        }
        int tmp = (0xFF & in.read());
        while(index != b.length) {
            String binaryString = convertIntToBinary(tmp);
            for (int i = 0; i <binaryString.length(); i++) {
                int t = Character.getNumericValue(binaryString.charAt(i));
                b[index++] = (byte) t;
                if (index == b.length)
                    break;
            }
            tmp = (0xFF & in.read());

        }
        return 0;
    }

    private String convertIntToBinary(int num) {
        String str="";
        if(num==0)
            str = "00000000";
        while(num !=0){
            str = (num%2)+""+str;
            num=num/2;
        }
        if (str.length() != 8){
            while (str.length()<8)
                str = '0'+str;
        }

        return str;
    }


}
