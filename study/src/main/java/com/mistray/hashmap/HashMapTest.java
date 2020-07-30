package com.mistray.hashmap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author MistLight
 * @create 2018-10-25
 * @desc
 */
public class HashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
        hashMap.put("1111","22222");

        ConcurrentLinkedQueue queue1 = new ConcurrentLinkedQueue();

        queue1.add("aa");

        int t =1;
        int tail =2;
        int head = 3;
        int p = (t != (t = tail)) ? t : head;
        System.out.println(p);
    }

}
