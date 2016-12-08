package albon.arith.compiler;

import javax.tools.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by albon on 16/12/7.
 */
public class CompilerAPITest {

    public static void main(String[] args) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        MemoryJavaFileManager manager = new MemoryJavaFileManager(standardFileManager);

        JavaFileObject fileObject = buildTestFile();
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(fileObject);

        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<JavaFileObject>();

        JavaCompiler.CompilationTask compilerTask = compiler.getTask(null, manager, collector, null, null,
                compilationUnits);
        Boolean result = compilerTask.call();
        System.out.println("compilerTask.call = " + result);

        if (result.equals(Boolean.FALSE)) {
            List<Diagnostic<? extends JavaFileObject>> diagnostics = collector.getDiagnostics();
            System.out.println("diagnostics.size -> " + diagnostics.size());
            for (Diagnostic<? extends JavaFileObject> d : diagnostics) {
                System.out.println("Line Number->" + d.getLineNumber());
                System.out.println("Message->" + d.getMessage(Locale.ENGLISH));
                System.out.println("Source" + d.getCode());
                System.out.println("\n");
            }
            return;
        }

        Map<String, byte[]> classBytes = manager.getClassBytes();

        MemoryClassLoader classLoader = new MemoryClassLoader(classBytes);

        reflectTest(classLoader);

        standardFileManager.close();
    }

    private static void reflectTest(MemoryClassLoader classLoader) {
        try {
            String classPath = "file:" + System.getProperty("user.dir") + "/";
//            URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL(classPath)},
//                    Thread.currentThread().getContextClassLoader());
            Class<?> aClass = classLoader.loadClass("Test");
            Object test = aClass.newInstance();
            Method method = aClass.getMethod("hello");

            Object result = method.invoke(test);
            System.out.println("invoke result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SimpleJavaFileObject buildTestFile() {
        String content =
                "public class Test {\n" +
                "\n" +
                "    public String hello() {\n" +
                "        return \"hello world\";\n" +
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
