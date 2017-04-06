package com.elane.common.utils.response;

import java.util.HashMap;
import java.util.Map;

/***
 * 对象返回
 * @author 
 *
 */
public class ElaneResponse {

	public static final Integer SUCCESS = 1;
	public static final Integer ERROR = -1;
	public static final Integer UNEXPECT = -2;
	
	private static final Map<Integer, String> map = new HashMap<Integer, String>(){
	    {
	        put(SUCCESS,"success");
	        put(ERROR, "error");
	        put(UNEXPECT, "unexpect");
	    }
	};
	
	private Meta meta;
	private Object data;

	public ElaneResponse success() {
	    meta = new Meta(SUCCESS, map.get(SUCCESS));
        this.data = null;
        return this;
	}

	public ElaneResponse success(String message) {
        meta = new Meta(SUCCESS, message);
        this.data = null;
        return this;
    }

	public ElaneResponse success(Object data) {
		meta = new Meta(SUCCESS, map.get(SUCCESS));
		this.data = data;
		return this;
	}

	public ElaneResponse failure() {
		return failure(map.get(ERROR));
	}

	public ElaneResponse failure(String message) {
		meta = new Meta(ERROR, message);
		this.data = null;
		return this;
	}

	public ElaneResponse unexpect() {
        return unexpect(map.get(UNEXPECT));
    }

	public ElaneResponse unexpect(String message) {
        meta = new Meta(UNEXPECT, message);
        this.data = null;
        return this;
    }
   
	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}
}
