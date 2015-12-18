package ss.week5;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * A simple class that experiments with the Hex encoding
 * of the Apache Commons Codec library.
 *
 */
public class EncodingTest {
    public static void main(String[] args) throws DecoderException {
       String input = "Hello World";
       System.out.println(Hex.encodeHexString(input.getBytes()));

       String s = new String(Hex.decodeHex(Hex.encodeHexString(input.getBytes()).toCharArray()));
       System.out.println(s);
       
       System.out.println(Base64.encode(input.getBytes()));
       String hex = new String("010203040506");
       
       System.out.println(Base64.encode(Hex.decodeHex(hex.toCharArray())));
       
       System.out.println(new String(Base64.decode("U29mdHdhcmUgU31zdGVtcw==")));
       System.out.println(Base64.decode(Base64.encode(input.getBytes())));

       
      System.out.println(Base64.encode("a".getBytes()));
       System.out.println(Base64.encode("aa".getBytes()));
       System.out.println(Base64.encode("aaa".getBytes()));
       System.out.println(Base64.encode("aaaa".getBytes()));
       System.out.println(Base64.encode("aaaaa".getBytes()));
       System.out.println(Base64.encode("aaaaaa".getBytes()));
	
    }
}
