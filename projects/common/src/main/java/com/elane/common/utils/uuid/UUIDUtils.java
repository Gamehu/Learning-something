package com.elane.common.utils.uuid;

import java.util.UUID;

/***
 * UUID 工具类
 * @author hankin
 *
 */
public class UUIDUtils {

	/***
	 * 获取UUID 32位
	 * @return
	 */
	public static String getUUID32() {
		return getUUID36().replaceAll("-", "");
	}

	/***
	 * 获取UUID 34位
	 * @return
	 */
	public static String getUUID36() {
		return UUID.randomUUID().toString();
	}
}
