package com.BeanFactory;

import java.util.Dictionary;
import java.util.List; 
import com.inject.InjectValue;

public class AnnotationBenaFactory extends BeanFactory {

	@Override
	public Object getBean(Class<?> className, Dictionary<Class<?>,List<InjectValue>> dictionary) {
		Object object = newInstace(className,dictionary);
		return object;
	}
	@Override
	public Object getBean(Class<?> className) { 
		return getBean(className, null);
	}


}
