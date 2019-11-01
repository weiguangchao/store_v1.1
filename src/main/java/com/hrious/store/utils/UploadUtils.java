package com.hrious.store.utils;

public class UploadUtils {

	public static String getDir(String name) {
		String hex = Integer.toHexString(name.hashCode());
		for (int i = 0, h = hex.length(); i < 8 - h; i++) {
			hex = "0" + hex;
		}
		// 生成八级目录
		return "/" + hex.charAt(0) + "/" + hex.charAt(1) + "/" + hex.charAt(2) 
			+ "/" + hex.charAt(3) + "/" + hex.charAt(4) + "/" + hex.charAt(5) 
			+ "/" + hex.charAt(6);
	}
	
	public static String getUUIDName(String name) {
		int i = name.lastIndexOf(name);
		if (i == -1) { // 没有.(拓展名)
			return UUIDUtils.getCode();
		} else {
			return UUIDUtils.getCode() + name.substring(i);
		}
	}
}
