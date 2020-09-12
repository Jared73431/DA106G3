package com.groupchat.model;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class GroupChatredisDAO {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)
    //KEY(社團:會員)
	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String groupgo_id) {
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(groupgo_id, 0, jedis.llen(groupgo_id) - 1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String member_no, String groupgo_id,String chat_con) {
		String message = new StringBuilder(member_no).append(":").append(chat_con).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(groupgo_id, chat_con);

		jedis.close();
	}

}
