package org.example.algorithm;

import java.util.ArrayList;
import java.util.List;

public class KMPMatcher {
    private final String pattern;
    private final int[] lps;

    public KMPMatcher(String pattern) {
        this.pattern = pattern;
        this.lps = buildLps(pattern);
    }

    public List<Integer> search(String text) {
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        int n = text.length(), m = pattern.length();

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    result.add(i - j);
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) j = lps[j - 1];
                else i++;
            }
        }
        return result;
    }

    private int[] buildLps(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0, i = 1;

        while (i < m) {
            if (p.charAt(i) == p.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) len = lps[len - 1];
                else { lps[i] = 0; i++; }
            }
        }
        return lps;
    }
}
