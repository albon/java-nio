package albon.arith.compiler.memory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author albon
 *         Date : 16-12-8
 *         Time: 下午2:49
 */
public class JavaStringCompilerTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {
        String content =
                "public class Test {\n" +
                        "\n" +
                        "    public String say() {\n" +
                        "        return \"hello world\";\n" +
                        "    }\n" +
                        "}\n";

        JavaStringCompiler compiler = new JavaStringCompiler();
        Map<String, byte[]> classBytes = compiler.compile("Test", content);
        Class<?> clazz = compiler.loadClass("Test", classBytes);
        Object instance = clazz.newInstance();
        Method method = clazz.getMethod("say");

        Object result = method.invoke(instance);
        System.out.println("invoke result = " + result);
    }

}
