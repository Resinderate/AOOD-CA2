package ca2;

import java.io.*;

public class IOStringUtils {
    public static void writeFixedString(DataOutput out, int length, String s) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            if(i < s.length())
                sb.append(s.charAt(i));     //append char
            else
                sb.append(0);           //write unicode zero
        }
        out.writeUTF(sb.toString());
    }
    
    public static String readFixedString(DataInput in, int length) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        String result = in.readUTF();
        for(int i = 0; i < result.length(); i++)
        {
            char c = result.charAt(i);
            //if char is not Unicode zero add to string
            if(c != '0')
                sb.append(c);
        }
        return sb.toString();
    }
}
