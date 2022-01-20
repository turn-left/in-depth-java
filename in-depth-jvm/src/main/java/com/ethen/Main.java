package com.ethen;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.err.println((int) 'A');
//        System.err.println((int) 'Z');
//        System.err.println((int) 'a');
//        System.err.println((int) 'z');

        String plain = "wangximing";
        System.err.println(encode(plain));
    }

    public static String encode(String plain) {
        if (plain == null || "".equals(plain)) {
            return plain;
        }
        // 取ascii值
        final char[] chars = plain.toCharArray();
        // 取6位分组
        List<int[]> groupList = new ArrayList<>();
        int size = (chars.length - 1) / 6 + 1;
        for (int i = 0; i < size; i++) {
            int[] row = new int[6];
            for (int j = 0; j < 6; j++) {
                int index = 6 * i + j;
                if (index < chars.length) {
                    row[j] = chars[index];
                }
            }
            groupList.add(row);
        }
        // 分组计算密码
        int[] countGroup = new int[6];
        for (int k = 0; k < countGroup.length; k++) {
            for (int m = 0; m < size; m++) {
                countGroup[k] += groupList.get(m)[k];
            }
            while (countGroup[k] >= 10) {
                countGroup[k] = countGroup[k] / 100 + countGroup[k] % 100 / 10 + countGroup[k] % 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countGroup.length; i++) {
            sb.append(countGroup[i]);
        }
        return sb.toString();
    }
}
