package albon.arith.local.var.table;

/**
 * Created by albon on 16/11/20.
 */
public class LocalVarTableTest {

    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }
}
