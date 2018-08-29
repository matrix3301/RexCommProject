package com.commcode.rancode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;

/**
 * @author Rex Tan
 * @version 1.0
 * @since 2018/8/29
 * 批量生产不重复随机数
 */
public class RandomCodeGenerator {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 通过HashSet的不可能重复性，来校验随机码是否重复
        HashSet<String> hashSet = new HashSet<>();

        long startTime = System.currentTimeMillis();

        // 设定编码方式，MD5码长32，SHA1码长40
        String encode = "SHA1";
        // 设定随机码长度，数字越小生成时间越长
        int codeLength = 5;
        // 设定取码的起始位置，起始位置不能设定大于编码方式的最大位数减去随机码长度。例如，以5为例，MD5最大27，SHA1最大35
        int startPosition = 0;
        // 设定生成多少个，数字越大生成时间越长
        int count;
        try {
            count = Integer.parseInt(args[0]);
        } catch (Exception e) {
            count = 100;
        }

//		while (hashSet.size() < count) {
//			Random ran = new Random(System.currentTimeMillis());
//			String ranString = String.valueOf(ran.nextInt());
//			SHAEncode shaEncode = new SHAEncode(ranString, encode);
//			hashSet.add(shaEncode.enCoding().substring(startPosition, codeLength));
//
//			// 打印出当前已经生成出多少个随机码
////			System.out.println(hashSet.size());
//		}
        int i = 1;
        while (i <= count) {
            Random ran = new Random(System.currentTimeMillis());
            String ranString = String.valueOf(ran.nextInt());
            SHAEncode shaEncode = new SHAEncode(ranString, encode);
            String newCode = shaEncode.enCoding().substring(startPosition, codeLength);
            if (!hashSet.contains(newCode)) {
                hashSet.add(newCode);
                i++;
            }

            // 打印出当前已经生成出多少个随机码
            System.out.println(hashSet.size());
        }
        long endCreateTime = System.currentTimeMillis();

        // 设定所需打印的前缀字符[0-9][a-z]
        String prefix = "1";
        // 将HashSet中的随机码一次性打印出来
//		for (String a : hashSet) {
//			System.out.println(",\"" + prefix + "\",\"" + a + "\",0");
//		}
        // 将编码输出到文件
        saveToFile(prefix, hashSet);

        long endPrintTime = System.currentTimeMillis();

        System.out.println("生成用时：" + (((double) endCreateTime - (double) startTime) / 1000));
        System.out.println("打印用时：" + (((double) endPrintTime - (double) endCreateTime) / 1000));

    }

    private static void saveToFile(String prefix, HashSet<String> hs) {
        File file = new File("randomCode.csv");
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            int i = 1;
            for (String a : hs) {
                bw.write(",\"" + prefix + "\",\"" + a + "\",0");
                //用来判断最后一行不要加换行
                if (i < hs.size()) {
                    bw.newLine();
                }
                i++;
            }
            bw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
