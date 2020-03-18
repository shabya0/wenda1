package com.nowcoder.wenda.util;

public class RedisKeyUtil {
    private static String split = ":";
    private static String biz_like = "LIKE";
    private static String biz_dislike = "DISLIKE";

    public static String getLikeKey(int entityType, int entityId){
        return biz_like + split + String.valueOf(entityType) + split +String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId){
        return biz_dislike + split + String.valueOf(entityType) + split +String.valueOf(entityId);
    }
}
