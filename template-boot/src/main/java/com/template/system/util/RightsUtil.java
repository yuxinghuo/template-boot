package com.template.system.util;

import cn.hutool.core.util.StrUtil;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

public class RightsUtil {
    public RightsUtil() {
    }

    public static boolean checkBigInteger(String rights) {
        return !StrUtil.isBlank(rights) && !"0".equals(rights);
    }

    public static BigInteger sumRights(int[] rights) {
        BigInteger num = new BigInteger("0");

        for(int i = 0; i < rights.length; ++i) {
            num = num.setBit(rights[i]);
        }

        return num;
    }

    public static BigInteger sumRights(List<Integer> rights) {
        BigInteger num = new BigInteger("0");

        Integer r;
        for(Iterator var2 = rights.iterator(); var2.hasNext(); num = num.setBit(r)) {
            r = (Integer)var2.next();
        }

        return num;
    }

    public static BigInteger sumRights(String[] rights) {
        BigInteger num = new BigInteger("0");

        for(int i = 0; i < rights.length; ++i) {
            num = num.setBit(Integer.parseInt(rights[i]));
        }

        return num;
    }

    public static boolean testRights(String sum, int targetRights) {
        return !StrUtil.isBlank(sum) && !"0".equals(sum) ? testRights(new BigInteger(sum), targetRights) : false;
    }

    public static boolean testRights(String sum, String targetRights) {
        return !StrUtil.isBlank(sum) && !"0".equals(sum) ? testRights(new BigInteger(sum), Integer.parseInt(targetRights)) : false;
    }

    public static boolean testRights(BigInteger sum, int targetRights) {
        return sum != null && !"0".equals(sum.toString()) ? sum.testBit(targetRights) : false;
    }
}
