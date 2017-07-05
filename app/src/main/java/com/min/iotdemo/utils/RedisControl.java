package com.min.iotdemo.utils;

import android.util.Log;

import com.min.iotdemo.bean.Equiment;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * Created by Min on 2017/7/4.
 */

public class RedisControl {
    private static RedisControl instance=null;
    private final Jedis jedis;
    public RedisControl() {
        //通过域名和端口号连接 Redis 服务
       jedis = new Jedis("dounine.net",6379);
        jedis.auth("root");
        Log.e("aaa","已连接上Redis");
    }
public static RedisControl getInstance(){
    if(instance==null){
        synchronized(RedisControl.class){
            if(instance==null){
                instance=new RedisControl();
            }
        }
    }
    return instance;
}

    /**
     * 通过键值获取值
     * @param key
     * @return
     */
    public String getValue(final String key) {
        String value=jedis.get(key);
        return value;
    }

    /**
     * 通过键值集合获取所有设备值
     * @param keyList
     * @return
     */
    public List<String> getAllstatus(List<Equiment> keyList) throws Exception {
        List<String> equimentStaus=new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
              equimentStaus.add(jedis.get(keyList.get(i).getId()));
        }
        return equimentStaus;
    }



    /**
     * 通过键值设置值
     * @param key
     * @param value
     * @return
     */
    public String setValue(String key,String value){
        return   jedis.set(key,value);
    }

    /**
     * 获取(组合设备)json格式
     * @return
     */
    public String getJsonValue(){
        return jedis.get("sort");
    }

}
