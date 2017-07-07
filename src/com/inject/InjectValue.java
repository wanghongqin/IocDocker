package com.inject;

/**
 * 注入数据实体
 * 
 * @author wanghongqin
 *
 */
public class InjectValue {
	/**
	 * 值类型
	 */
	private Class<?> valueType;
	/**
	 * 值
	 */
	private Object value;

	public Class<?> getValueType() {
		return valueType;
	}

	public void setValueType(Class<?> valueType) {
		this.valueType = valueType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
