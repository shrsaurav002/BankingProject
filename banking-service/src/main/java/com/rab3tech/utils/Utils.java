package com.rab3tech.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lombok.val;

public class Utils {

	public static String genRandomAlphaNum() {
		// pseudo code
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 5; i++)
			sb.append(chars[rnd.nextInt(chars.length)]);
		return sb.toString();
	}

	public static String generateCustomerAccount() {
		Random r = new Random(System.currentTimeMillis());
		return "00" + (1000000000 + r.nextInt(2000000000)) + "";
	}

	public static void main(String[] args) {
		System.out.println(generateCustomerAccount());
	}

	public static int genOTP() {
		int n = 0;
		while (n < Math.pow(10, 5)) {
			n = (int) (Math.random() * Math.pow(10, 6));
		}
		System.out.println("OTP=" + n);
		return n;
	}

	public static String amountToWords(String amount) {
		String output = "";
		Map<Integer, String> valMap = new HashMap<>();
		valMap.put(1, "One");
		valMap.put(2, "Two");
		valMap.put(3, "Three");
		valMap.put(4, "Four");
		valMap.put(5, "Five");
		valMap.put(6, "Six");
		valMap.put(7, "Seven");
		valMap.put(8, "Eight");
		valMap.put(9, "Nine");
		valMap.put(0, "Zero");
		Map<Integer, String> tensMap = new HashMap<>();
		tensMap.put(1, "Ten");
		tensMap.put(2, "Twenty");
		tensMap.put(3, "Thirty");
		tensMap.put(4, "Forty");
		tensMap.put(5, "Fifty");
		tensMap.put(6, "Sixty");
		tensMap.put(7, "Seventy");
		tensMap.put(8, "Eighty");
		tensMap.put(9, "Ninety");
		// "1 234 567 890"
		String[] place = { "", "Thousand", "Million", "Billion", "Trillion" };
		int n = Integer.parseInt(amount);
		int count = 0;
		while (n != 0) {
			System.out.println(n % 1000);
			int value = n % 1000;
			String value1 = "";
			String convert = "" + value;
			char[] a = convert.toCharArray();
			char[] c = new char[a.length];
			for (int i = (a.length - 1); i >= 0; i--) {
				c[a.length - 1 - i] = a[i];
			}
			for (int i = 0; i < c.length; i++) {
				int t = Integer.parseInt(c[i] + "");
				if (i == 2) {
					value1 = valMap.get(t) + "Hundred" + value1;
				}
				if (i == 1) {
					value1 = tensMap.get(t) + value1;
				}
				if (i == 0) {
					value1 = valMap.get(t) + value1;
				}
			}
			output = value1 + place[count]+output;
			
			count = count + 1;
			n = n / 1000;
		}
		return output;
	}
}
