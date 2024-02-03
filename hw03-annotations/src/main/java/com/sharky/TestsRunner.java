package com.sharky;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestsRunner {
    private static final Logger logger = LoggerFactory.getLogger(TestsRunner.class);

    private TestsRunner() {
        throw new UnsupportedOperationException();
    }

    public static <T> void run(Class<T> testClass) {
        var annotationsToMethodsMap = getAnnotations(testClass);
        var testMethods = annotationsToMethodsMap.get(Test.class);
        if (testMethods == null) {
            logger.warn("No tests were found");
            return;
        }
        var failedTests = runTests(testClass, annotationsToMethodsMap);
        printResult(failedTests, testMethods.size());
    }

    private static <T> Map<Class<?>, List<Method>> getAnnotations(Class<T> testClass) {
        var methods = testClass.getDeclaredMethods();
        var validMethods = Arrays.stream(methods).filter(it -> !Modifier.isPrivate(it.getModifiers()));
        var annotationsToMethodsMap = new HashMap<Class<?>, List<Method>>();
        validMethods.forEach(
            method -> {
                var annotations = method.getDeclaredAnnotations();
                Arrays.stream(annotations).forEach(
                    annotation -> {
                        var annotatedMethods = annotationsToMethodsMap.getOrDefault(
                            annotation.annotationType(),
                            new ArrayList<>()
                        );
                        annotatedMethods.add(method);
                        annotationsToMethodsMap.put(annotation.annotationType(), annotatedMethods);
                    }
                );
            }
        );
        return annotationsToMethodsMap;
    }

    private static <T> int runTests(Class<T> testClass, Map<Class<?>, List<Method>> annotatedMethods) {
        var beforeMethods = annotatedMethods.getOrDefault(Before.class, Collections.emptyList());
        var afterMethods = annotatedMethods.getOrDefault(After.class, Collections.emptyList());
        var testMethods = annotatedMethods.getOrDefault(Test.class, Collections.emptyList());
        var failedTests = 0;
        for (var testMethod : testMethods) {
            var classInstance = ReflectionHelper.instantiate(testClass);
            try {
                invokeSupportMethods(beforeMethods, testMethod.getName(), classInstance);
                testMethod.invoke(classInstance);
                invokeSupportMethods(afterMethods, testMethod.getName(), classInstance);
            } catch (Exception ex) {
                logger.error(ex.getCause() == null ? ex.toString() : ex.getCause().toString());
                failedTests++;
            }
        }
        return failedTests;
    }

    private static <T> void invokeSupportMethods(
        List<Method> supportMethods,
        String testMethodName,
        T classInstance
    ) throws InvocationTargetException, IllegalAccessException {
        for (var supportMethod : supportMethods) {
            if (supportMethod.getParameterCount() == 1) {
                supportMethod.invoke(classInstance, testMethodName);
            } else {
                supportMethod.invoke(classInstance);
            }
        }
    }

    private static void printResult(int failedTests, int totalTests) {
        logger.info("Tests passed: {}, failed: {}, total: {}", totalTests - failedTests, failedTests, totalTests);
    }
}
