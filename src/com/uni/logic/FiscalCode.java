package com.uni.logic;

import java.util.HashMap;

public class FiscalCode {

	private String name;
	private String surname;
	private String data_n;
	private String comune;
	private String gender;
	private HashMap<String,  String> mapMonth;
	private HashMap<String,  Integer> tableFiscalCodeDispari;
	private HashMap<String,  Integer> tableFiscalCodePari;
	private HashMap<Integer, String> tableFiscalCode_Code;
	
	/**
	 * 
	 * @param name
	 * @param surname
	 * @param data_n
	 * @param comune
	 */
	public FiscalCode(String name, String surname, String data_n, String comune, String gender) {
		
		this.name 		= name;
		this.surname 	= surname;
		this.data_n 	= data_n;
		this.comune 	= comune;
		this.gender     = gender;
		
		mapMonth = new HashMap<>();
		mapMonth.put("01", "A");
		mapMonth.put("02", "B");
		mapMonth.put("03", "C");
		mapMonth.put("04", "D");
		mapMonth.put("05", "E");
		mapMonth.put("06", "H");
		mapMonth.put("07", "L");
		mapMonth.put("08", "M");
		mapMonth.put("09", "P");
		mapMonth.put("10", "R");
		mapMonth.put("11", "S");
		mapMonth.put("12", "T");
		
		tableFiscalCodeDispari = new HashMap<>();
		tableFiscalCodeDispari.put("0", 1);
		tableFiscalCodeDispari.put("1", 0);
		tableFiscalCodeDispari.put("2", 5);
		tableFiscalCodeDispari.put("3", 7);
		tableFiscalCodeDispari.put("4", 9);
		tableFiscalCodeDispari.put("5", 13);
		tableFiscalCodeDispari.put("6", 15);
		tableFiscalCodeDispari.put("7", 17);
		tableFiscalCodeDispari.put("8", 19);
		tableFiscalCodeDispari.put("9", 21);
		tableFiscalCodeDispari.put("A", 1);
		tableFiscalCodeDispari.put("B", 0);
		tableFiscalCodeDispari.put("C", 5);
		tableFiscalCodeDispari.put("D", 7);
		tableFiscalCodeDispari.put("E", 9);
		tableFiscalCodeDispari.put("F", 13);
		tableFiscalCodeDispari.put("G", 15);
		tableFiscalCodeDispari.put("H", 17);
		tableFiscalCodeDispari.put("I", 19);
		tableFiscalCodeDispari.put("J", 21);
		tableFiscalCodeDispari.put("K", 2);
		tableFiscalCodeDispari.put("L", 4);
		tableFiscalCodeDispari.put("M", 18);
		tableFiscalCodeDispari.put("N", 20);
		tableFiscalCodeDispari.put("O", 11);
		tableFiscalCodeDispari.put("P", 3);
		tableFiscalCodeDispari.put("Q", 6);
		tableFiscalCodeDispari.put("R", 8);
		tableFiscalCodeDispari.put("S", 12);
		tableFiscalCodeDispari.put("T", 14);
		tableFiscalCodeDispari.put("U", 16);
		tableFiscalCodeDispari.put("V", 10);
		tableFiscalCodeDispari.put("W", 22);
		tableFiscalCodeDispari.put("X", 25);
		tableFiscalCodeDispari.put("Y", 24);
		tableFiscalCodeDispari.put("Z", 23);
		
		tableFiscalCodePari = new HashMap<>();
		tableFiscalCodePari.put("0", 0);
		tableFiscalCodePari.put("1", 1);
		tableFiscalCodePari.put("2", 2);
		tableFiscalCodePari.put("3", 3);
		tableFiscalCodePari.put("4", 4);
		tableFiscalCodePari.put("5", 5);
		tableFiscalCodePari.put("6", 6);
		tableFiscalCodePari.put("7", 7);
		tableFiscalCodePari.put("8", 8);
		tableFiscalCodePari.put("9", 9);
		tableFiscalCodePari.put("A", 0);
		tableFiscalCodePari.put("B", 1);
		tableFiscalCodePari.put("C", 2);
		tableFiscalCodePari.put("D", 3);
		tableFiscalCodePari.put("E", 4);
		tableFiscalCodePari.put("F", 5);
		tableFiscalCodePari.put("G", 6);
		tableFiscalCodePari.put("H", 7);
		tableFiscalCodePari.put("I", 8);
		tableFiscalCodePari.put("J", 9);
		tableFiscalCodePari.put("K", 10);
		tableFiscalCodePari.put("L", 11);
		tableFiscalCodePari.put("M", 12);
		tableFiscalCodePari.put("N", 13);
		tableFiscalCodePari.put("O", 14);
		tableFiscalCodePari.put("P", 15);
		tableFiscalCodePari.put("Q", 16);
		tableFiscalCodePari.put("R", 17);
		tableFiscalCodePari.put("S", 18);
		tableFiscalCodePari.put("T", 19);
		tableFiscalCodePari.put("U", 20);
		tableFiscalCodePari.put("V", 21);
		tableFiscalCodePari.put("W", 22);
		tableFiscalCodePari.put("X", 23);
		tableFiscalCodePari.put("Y", 24);
		tableFiscalCodePari.put("Z", 25);
		
		tableFiscalCode_Code = new HashMap<>();
		tableFiscalCode_Code.put(0, "A");
		tableFiscalCode_Code.put(1, "B");
		tableFiscalCode_Code.put(2, "C");
		tableFiscalCode_Code.put(3, "D");
		tableFiscalCode_Code.put(4, "E");
		tableFiscalCode_Code.put(5, "F");
		tableFiscalCode_Code.put(6, "G");
		tableFiscalCode_Code.put(7, "H");
		tableFiscalCode_Code.put(8, "I");
		tableFiscalCode_Code.put(9, "J");
		tableFiscalCode_Code.put(10, "K");
		tableFiscalCode_Code.put(11, "L");
		tableFiscalCode_Code.put(12, "M");
		tableFiscalCode_Code.put(13, "N");
		tableFiscalCode_Code.put(14, "O");
		tableFiscalCode_Code.put(15, "P");
		tableFiscalCode_Code.put(16, "Q");
		tableFiscalCode_Code.put(17, "R");
		tableFiscalCode_Code.put(18, "S");
		tableFiscalCode_Code.put(19, "T");
		tableFiscalCode_Code.put(20, "U");
		tableFiscalCode_Code.put(21, "V");
		tableFiscalCode_Code.put(22, "W");
		tableFiscalCode_Code.put(23, "X");
		tableFiscalCode_Code.put(24, "Y");
		tableFiscalCode_Code.put(25, "Z");
		
	}
	
	public String getFiscalCode() {
		
		String fiscal_code 		= "";
		String vocals 			= "aeiou";

		name 	= name.toLowerCase();
		surname = surname.toLowerCase();		
		
		String subSurnameVocal 	= "";
		String subSurnameConsonant = "";
		
		for(char nc : surname.toCharArray()) {
			if(!vocals.contains(String.valueOf(nc))) {
				subSurnameConsonant += String.valueOf(nc);
			}
		}
		if(subSurnameConsonant.length() >= 3) {
			if(subSurnameConsonant.length() == 3) {
				fiscal_code += subSurnameConsonant;
			}else {
				fiscal_code += String.valueOf(subSurnameConsonant.charAt(0)) + 
							   String.valueOf(subSurnameConsonant.charAt(1)) + 
							   String.valueOf(subSurnameConsonant.charAt(2)); 
			}
		}else {
			fiscal_code += subSurnameConsonant;
			int difference = 3 - fiscal_code.length();
			for(char nv : surname.toCharArray()) {
				if(vocals.contains(String.valueOf(nv))) {
					subSurnameVocal += String.valueOf(nv);
				}
			}
			for(int d = 0; d < difference; d++) {
				if(d < subSurnameVocal.length()) {
					fiscal_code += String.valueOf(subSurnameVocal.charAt(d));
				}
			}
		}
		
		String subNameVocal 	= "";
		String subNameConsonant = "";
		
		for(char nc : name.toCharArray()) {
			if(!vocals.contains(String.valueOf(nc))) {
				subNameConsonant += String.valueOf(nc);
			}
		}
		if(subNameConsonant.length() >= 3) {
			if(subNameConsonant.length() == 3) {
				fiscal_code += subNameConsonant;
			}else {
				fiscal_code += String.valueOf(subNameConsonant.charAt(0)) + 
							   String.valueOf(subNameConsonant.charAt(2)) + 
							   String.valueOf(subNameConsonant.charAt(3)); 
			}
		}else {
			fiscal_code += subNameConsonant;
			int difference = 6 - fiscal_code.length();
			for(char nv : name.toCharArray()) {
				if(vocals.contains(String.valueOf(nv))) {
					subNameVocal += String.valueOf(nv);
				}
			}
			for(int d = 0; d < difference; d++) {
				if(d < subNameVocal.length()) {
					fiscal_code += String.valueOf(subNameVocal.charAt(d));
				}
			}
		}
		
		fiscal_code += data_n.split("-")[2].substring(2, 4);
		fiscal_code += mapMonth.get(data_n.split("-")[1]);
		
		if(gender.toLowerCase().equals("m")) {
			fiscal_code += data_n.split("-")[0];
		}else {
			fiscal_code += String.valueOf(Integer.parseInt(data_n.split("-")[0]) + 40);
		}
		
		fiscal_code += SingletonComuni.getInstance().getComuneCodiceResidenziale().get(comune);
		
		boolean isDispari = true;
		int tot           = 0;
		for(char f : fiscal_code.toCharArray()) {
			
			if(isDispari)
				tot += tableFiscalCodeDispari.get(String.valueOf(f).toUpperCase());
			else
				tot += tableFiscalCodePari.get(String.valueOf(f).toUpperCase());
			
			isDispari = !isDispari;
		}
		
		int resto = tot % 26;
		fiscal_code += tableFiscalCode_Code.get(resto);
		
		return fiscal_code.toUpperCase();
	}
	
}
