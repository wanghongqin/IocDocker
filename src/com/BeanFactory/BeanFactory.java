package com.BeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import com.annotation.RefClass;
import com.inject.InjectValue;

/**
 * 装配工厂
 * 
 * @author wanghongqin
 *
 */
public abstract class BeanFactory {

	protected Object newInstace(Class<?> cls, Dictionary<Class<?>, List<InjectValue>> dictionary) {
		Object object = null;
		try { 
			if (dictionary != null && dictionary.size() > 0) {
				List<InjectValue> models = dictionary.get(cls);
				if (models != null && models.size() > 0) {

					// 如果类型包含值，则加载					
					List<Class<?>> classList = models.stream().collect(() -> new ArrayList<Class<?>>(),
							(list, item) -> list.add(item.getValueType()), (list1, list2) -> list1.addAll(list2));
					
					Class<?>[] classes = classList.toArray(new Class<?>[classList.size()]);
					
					List<Object> valuesList = models.stream().collect(() -> new ArrayList<Object>(),
							(list, item) -> list.add(item.getValue()), (list1, list2) -> list1.addAll(list2));
					Object[] values = valuesList.toArray();
					
					Constructor<?> constructor = cls.getConstructor(classes);
					object = constructor.newInstance(values);
					refClass(object, dictionary);

				} else {
					object = cls.newInstance();
					refClass(object, dictionary);
				}
			} else {
				object = cls.newInstance();
				refClass(object, dictionary);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	
	/**
	 * 注入到对应的字段中
	 * 
	 * @param object
	 */
	private void refClass(Object object, Dictionary<Class<?>, List<InjectValue>> dictionary) {
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 设置暴力访问
			field.setAccessible(true);
			RefClass refClass = field.getAnnotation(RefClass.class);
			if (refClass == null) {
				continue;
			}
			try {
				Object obj = newInstace(field.getType(), dictionary);
				field.set(object, obj);
				refClass(obj, dictionary);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 注入无参数对象
	 * 
	 * @param className
	 * @return
	 */
	public abstract Object getBean(Class<?> className);

	/**
	 * 注入带有参数对象
	 * 
	 * @param className
	 * @param models
	 * @return
	 */
	public abstract Object getBean(Class<?> className, Dictionary<Class<?>, List<InjectValue>> dictionary);

}
