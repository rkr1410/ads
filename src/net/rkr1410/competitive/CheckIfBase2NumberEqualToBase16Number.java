package net.rkr1410.competitive;

import java.util.Objects;

public class CheckIfBase2NumberEqualToBase16Number {


    public static void main(String[] args) {
        System.err.println(new CheckIfBase2NumberEqualToBase16Number().convertToBase10Int("1010111", 2));
        System.err.println(new CheckIfBase2NumberEqualToBase16Number().convertToBase10Int("57", 16));
        System.err.println(new CheckIfBase2NumberEqualToBase16Number().convertToBase10Int("5g", 16));
    }


	public int convertToBase10Int(String number, int base) {
		Objects.requireNonNull(number, "number cannot be null");
		if ("".equals(number)) {
			throw new IllegalArgumentException("number cannot be empty");
		}
		if (base < 2 || base > 16) {
			throw new IllegalArgumentException("Bases <2, 16> allowed (provided " + base + ")");
		}
		
		int returnValue = 0;
		int maxIndex = number.length() - 1;
		for (int pos = maxIndex; pos >= 0; pos--) {
			char currentChar = number.charAt(pos);
			int currentCharIntValue = getCharacterIntValue(currentChar);
			if (currentCharIntValue < 0 || currentCharIntValue >= base) {
				throw new IllegalArgumentException("Illegal character found at position " + pos);
			}
			
			int currentBasePower = maxIndex - pos;
			returnValue = returnValue + ((int) Math.pow(base, currentBasePower)) * currentCharIntValue;
		}
		
		return returnValue;
	}
	
	private int getCharacterIntValue(char ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		}
		if (ch >= 'A' && ch <= 'Z') {
			return 10 + ch - 'A';
		}
		if (ch >= 'a' && ch <= 'z') {
			return 10 + ch - 'a';
		}
		return -1;
	}

}
