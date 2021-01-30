package com.applecat;

public class CompareVersionNumbers {
	public int compareVersion(String version1, String version2) {
		int len1 = version1.length();
		int len2 = version2.length();
		int pre1 = 0, pre2 = 0;
		while (pre1 < len1 || pre2 < len2) {
			int sum1 = 0, sum2 = 0;
			while (pre1 < len1 && version1.charAt(pre1) != '.') {
				sum1 = sum1 * 10 + (version1.charAt(pre1) - '0');
				++pre1;
			}
			while (pre2 < len2 && version1.charAt(pre2) != '.') {
				sum2 = sum2 * 10 + (version1.charAt(pre1) - '0');
				++pre2;
			}

			if (sum1 > sum2){
				return 1;
			}else if(sum1 < sum2){
				return -1;
			}
			++pre1;
			++pre2;
		}

		return 0;
	}
}
