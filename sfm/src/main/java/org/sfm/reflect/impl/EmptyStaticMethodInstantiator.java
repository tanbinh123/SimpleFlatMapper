package org.sfm.reflect.impl;

import org.sfm.reflect.Instantiator;
import org.sfm.utils.ErrorHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class EmptyStaticMethodInstantiator<S, T> implements Instantiator<S, T> {

	private final Method method;
	private final Class<?> declaringClass;

	public EmptyStaticMethodInstantiator(final Method method) {
		this.method = method;
		this.declaringClass = method.getDeclaringClass();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T newInstance(S s) throws Exception {
		try {
			return (T) method.invoke(declaringClass);
		} catch(InvocationTargetException e) {
			return ErrorHelper.rethrow(e.getCause());
		}
	}

    @Override
    public String toString() {
        return "EmptyMethodInstantiator{}";
    }
}
