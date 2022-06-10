package com.b635.notebook.utils;

public class RUtils {
    private static final Object EMPTY_DATA = null;

    public static R commonFailOrNot(int i, String message) {
        if (i < 1) {
            return result(message + "失败");
        }
        return result(message + "成功");
    }

    public static R result(String message, Object ob) {
        return new R(message, ob);
    }


    public static R result(String message) {
        return result(message, EMPTY_DATA);
    }

}
