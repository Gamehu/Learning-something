package com.elane.core.bean;

/**
 * 下拉框
 * 
 * @author 
 *
 */
public class ComboboxNode {

	private String id;
	private String text;
	private Object data;

	public ComboboxNode() {
	}

	public ComboboxNode(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
