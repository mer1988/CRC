/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crc;

import javax.swing.JTextArea;


public class operacionesBinarias {
    static String strOriginal, strGenerator, strPadded, strRemainder;



    private static int zeroCounter(){
        int ret = 0;
        boolean x = true;
        for (int i=0; i<strRemainder.length(); i++)
        {
            if ((strRemainder.charAt(i) == '0') && (x == true))
            {
               ret +=1;
            }
            else
            {
                x = false;
            }
        }
        return ret;
}


    private static String divide(){
         String strTmp;
         boolean loop;
         do
         {
             strTmp = "";
             strRemainder = "";
             for (int jmpCounter=0; jmpCounter<strGenerator.length(); jmpCounter++)
             {
                 if (strGenerator.charAt(jmpCounter) == strPadded.charAt(jmpCounter))
                 {
                    strRemainder += '0';
                 }
                 else
                 {
                     strRemainder += '1';
                 }
             }

             for (int i=zeroCounter(); i<strGenerator.length(); i++)
             {
                 strTmp += strRemainder.charAt(i);
             }

             for (int i=strGenerator.length(); i<strPadded.length(); i++)
             {
                 strTmp += strPadded.charAt(i);
             }
             strPadded = strTmp;

         }while (strPadded.length() >= strGenerator.length());

         if (strPadded.length() != strGenerator.length()-1)
         {
            strTmp = "";
            for (int i=0; i<(strGenerator.length()-strPadded.length()-1); i++)
            {
                strTmp += '0';
            }
            strTmp += strPadded;
            strPadded = strTmp;
         }

         return strPadded;

    }

    public static String divisionBinariaConResto(String dividendo, String generador, JTextArea proceso){

            int i;
            strOriginal = strPadded = dividendo;
            strGenerator = generador;
            for (i=0; i<(strGenerator.length() - 1); i++){
                strPadded += '0';
            }
            if ((strPadded.length() >= strGenerator.length())  && (strGenerator.charAt(0)=='1')){
               return divide();
            }
            else if (strGenerator.charAt(0) == '0'){
                 return "Error, the Generator need to start with an 1\n";
            }
            else if (strPadded.length() < strGenerator.length()){
                return "Error, the String is shorter than the Generator...\n";
            }
            return null;
     }
    
}
