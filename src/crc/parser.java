/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crc;

/**
 *
 * @author Migue
 */

public class parser{

	public static String convertirPol(String s){
		String[] p = splitMas(s);
		String binario="";
		int cont = 0,i = 0,grad = Integer.valueOf(p[p.length-1].substring(1));
		if(p[0].equals("1")){
			cont=1;
			binario = "1";
			i = 1;
		}
		while (cont <= grad){
			if (cont == Integer.valueOf(p[i].substring(1)) ){
				binario += "1";
				i++;
				cont++;
			}else{
				binario += "0";
				cont++;
			}
		}

		return binario;
	}

	private static String[] splitMas(String s){
		String p = s.replace('+', ',');
		return p.split(",");
	}

	/*public static void main(String[] args) {
		String b = "1+x2+x3+x10";
		String bin = convertirPol(b);
		System.out.println(bin);

	}*/

}
