package com.cream.warriorLegends.common.exception;

/**
 * 抛出后需要处理的通用异常
 * 之所以要定义这个类，是想缩短一下异常名字的长度
 */
public class Err extends Exception{

    public Err(String message) {
        super(message);
    }
}
