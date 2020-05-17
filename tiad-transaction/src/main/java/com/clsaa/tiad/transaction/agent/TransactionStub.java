/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.transaction.agent;

import com.clsaa.tiad.transaction.annotation.Transaction;
import com.clsaa.tiad.transaction.listener.ListenerCache;
import com.clsaa.tiad.transaction.listener.TiadTransactionListener;
import javassist.*;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * generic the agent class and method
 */
public class TransactionStub {
    static ThreadLocal threadLocal = new ThreadLocal();

    @lombok.SneakyThrows
    public void scan(String packagePath) {
        threadLocal.get();
        Reflections f = new Reflections(packagePath);
        final Set<Method> methodsAnnotatedWith = f.getMethodsAnnotatedWith(Transaction.class);
        final ClassPool classPool = ClassPool.getDefault();
        final Set<Class> targetClasses = new HashSet<>();
        for (Method method : methodsAnnotatedWith) {
            final Class<?> declaringClass = method.getDeclaringClass();
            targetClasses.add(declaringClass);
        }
        //add static thread local
        for (Class targetClass : targetClasses) {
            final CtClass cc = classPool.get(targetClass.getName());
            final CtClass threadLocalClass = classPool.get(ThreadLocal.class.getName());
            CtField param = new CtField(threadLocalClass, "transaction_tag", cc);
            param.setModifiers(Modifier.STATIC);
            cc.addField(param, CtField.Initializer.byNew(threadLocalClass));
            Reflections ff = new Reflections(targetClass);
            final Set<Method> methods = ff.getMethodsAnnotatedWith(Transaction.class);
            //add thread local tag
            for (Method method : methods) {
                CtMethod m = cc.getDeclaredMethod(method.getName());
                final String methodId = targetClass.getName() + "#" + method;
                m.insertBefore("transaction_tag.set(\"transaction_tag-" + methodId + "\")");
                final Transaction annotation = method.getAnnotation(Transaction.class);
                final Class<? extends TiadTransactionListener> listener = annotation.listener();
                ListenerCache.put(methodId, listener);
            }
        }
    }
}
