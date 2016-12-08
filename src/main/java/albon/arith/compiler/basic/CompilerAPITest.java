package albon.arith.compiler.basic;

import javax.tools.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * Created by albon on 16/12/7.
 */
public class CompilerAPITest {

    public static void main(String[] args) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);

        JavaFileObject fileObject = buildTestFile();
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(fileObject);

        JavaCompiler.CompilationTask compilerTask = compiler.getTask(null, standardFileManager, null, null, null,
                compilationUnits);
        Boolean result = compilerTask.call();
        System.out.println("compilerTask.call = " + result);
        standardFileManager.close();

        reflectTest();
    }

    private static void reflectTest() {
        try {
            String classPath = "file:" + System.getProperty("user.dir") + "/";
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(classPath)},
                    Thread.currentThread().getContextClassLoader());
            Class<?> aClass = urlClassLoader.loadClass("Test");
            Object test = aClass.newInstance();
            Method method = aClass.getMethod("hello");

            Object result = method.invoke(test);
            System.out.println("invoke result = " + result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static SimpleJavaFileObject buildTestFile() {
        String content =
                "public class Test {\n" +
                        "\n" +
                        "    public String hello() {\n" +
                        "        return \"baby\";\n" +
                        "    }\n" +
                        "}\n";
        try {
            return new StringObject("Test", content);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
