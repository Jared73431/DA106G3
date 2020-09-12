package com.chatroom.model;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ChatroomredisDAO {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)
    //KEY(社團:會員)
	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String member_no1,String member_no2) {
		String key = new StringBuilder(member_no1).append(":").append(member_no2).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, jedis.llen(key) - 1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String member_no1,String member_no2,String chat_content) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder(member_no1).append(":").append(member_no2).toString();
		String receiverKey = new StringBuilder(member_no2).append(":").append(member_no1).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(senderKey, chat_content);
		jedis.rpush(receiverKey, chat_content);
		jedis.close();
	}
	public static void savewhileunonload(String member_no2,String chat_content) {
		String notreadKey = new StringBuilder(member_no2).append(":").append("dontread").toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(notreadKey, chat_content);
		jedis.close();
	}
	public static Long rownum(String memberno){
		String notreadKey = new StringBuilder(memberno).append(":").append("dontread").toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		Long rownumber = jedis.llen(notreadKey);
		return rownumber;
	}
	public static void readed(String member_no) {
		String notreadKey = new StringBuilder(member_no).append(":").append("dontread").toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.del(notreadKey);
		jedis.close();
	}
}
