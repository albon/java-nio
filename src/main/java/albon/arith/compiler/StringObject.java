package albon.arith.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by albon on 16/12/7.
 */
public class StringObject extends SimpleJavaFileObject {
    private String contents = null;

    protected StringObject(String className, String contents) throws URISyntaxException {
        super(URI.create("string:///" + className.replace('.', '/')+ Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        return contents;
    }
}
