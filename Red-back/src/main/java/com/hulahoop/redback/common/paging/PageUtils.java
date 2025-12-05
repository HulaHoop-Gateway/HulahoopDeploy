package com.hulahoop.redback.common.paging;

public class PageUtils {

    public static int calcOffset(int page, int size) {
        return (page - 1) * size;
    }

}
