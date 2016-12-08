package albon.arith;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author albon
 *         Date : 16-11-9
 *         Time: 下午3:40
 */
public class Test {

    private static String tables = "BAK_book_data\n" +
            "BAK_ip_map\n" +
            "BAK_log_key_value\n" +
            "BAK_raw_log\n" +
            "BAK_travelco_data\n" +
            "BAK_TWELL_MAPPED_COUNTER\n" +
            "BAK_TWELL_SIMPLE_COUNTER\n" +
            "BAK_TWELL_TIME_COUNTER\n" +
            "BAK_wrappers\n" +
            "book_data\n" +
            "bookingerrorstatbyairline\n" +
            "bookingerrorstat\n" +
            "booking_product\n" +
            "booking_product_test\n" +
            "bookingstatinfo\n" +
            "citycode\n" +
            "custominfo\n" +
            "functioninfo\n" +
            "geographylog\n" +
            "groupinfo\n" +
            "i_bookingerrorstatbyairline\n" +
            "i_bookingerrorstat\n" +
            "invalidRoute\n" +
            "ip_map\n" +
            "log_key_value\n" +
            "maintain\n" +
            "moneyeye\n" +
            "monitor_log\n" +
            "n3wrapper\n" +
            "periodFlighttmp\n" +
            "push_log\n" +
            "qboss_log\n" +
            "raw_log\n" +
            "raw_log_test\n" +
            "relationbookinginfobak111\n" +
            "relationbookinginfobak\n" +
            "rolesinfo\n" +
            "travalco_no_result\n" +
            "travelco_daily_data\n" +
            "travelco_data\n" +
            "travelcoerrorairline\n" +
            "travelcoerrorinfo\n" +
            "travelco_summary\n" +
            "TWELL_MAPPED_COUNTER\n" +
            "TWELL_SIMPLE_COUNTER\n" +
            "twell_summary\n" +
            "TWELL_TIME_COUNTER\n" +
            "userinfo\n" +
            "workLog\n" +
            "WRAPPER_COUNTER\n" +
            "wrappercustom\n" +
            "wrapper_faq_answer\n" +
            "wrapper_faq_question\n" +
            "wrapper_n3_constraints\n" +
            "wrapper_n3_constraints_online\n" +
            "wrapper_n3\n" +
            "wrapper_search\n" +
            "wrappers\n" +
            "wrapperswitchlog\n" +
            "wrapperupdatelog";

    private static String table2 = "-rw-rw---- 1 mysql mysql  2482549012 Oct 26 01:55 travelco_daily_data.MYD\n" +
            "-rw-rw---- 1 mysql mysql  1040807932 Oct 26 22:05 bookingstatinfo.MYD\n" +
            "-rw-rw---- 1 mysql mysql   136770032 Oct 26 22:05 bookingerrorstat.MYD\n" +
            "-rw-rw---- 1 mysql mysql  1595606496 Oct 26 22:05 bookingerrorstatbyairline.MYD\n" +
            "-rw-rw---- 1 mysql mysql   140986372 Oct 26 22:05 i_bookingerrorstat.MYD\n" +
            "-rw-rw---- 1 mysql mysql   911830660 Oct 26 22:05 i_bookingerrorstatbyairline.MYD\n" +
            "-rw-rw---- 1 mysql mysql    35921970 Nov  1 01:30 wrappers.MYD\n" +
            "-rw-rw---- 1 mysql mysql  3391353972 Nov  9 10:29 maintain.MYD\n" +
            "-rw-rw---- 1 mysql mysql 24550950264 Nov  9 14:40 raw_log.MYD\n" +
            "-rw-rw---- 1 mysql mysql  7093436556 Nov  9 14:41 booking_product.MYD";

    public static void main(String[] args) throws IOException {
        List<String> tableList = Lists.newArrayList(Splitter.on("\n").trimResults().omitEmptyStrings().splitToList(tables));

        List<String> itemList = Splitter.on("\n").trimResults().omitEmptyStrings().splitToList(table2);
        for (String item : itemList) {
            String[] split = item.split(" ");
            System.out.println(split[split.length-1]);
            tableList.add(split[split.length-1]);
            tableList.add(split[split.length-1].substring(0, split[split.length-1].length()-4));
        }

        System.out.println(tableList);

        List<String> ctTableList = Lists.newArrayList();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/root/1.log")));
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.contains("insert") == false || line.contains("into") == false) {
//                System.out.println("is_not_sql: " + line);
                continue;
            }

            String insert = line.substring(line.indexOf("insert"));
            String into = insert.substring(insert.indexOf("into")).trim().substring(4).trim();

            if (into.indexOf(" ") < 0) {
                System.out.println("error: " + into);
            } else {
                String trim = into.substring(into.indexOf(" ")).trim();
                boolean b = trim.startsWith("(");
                if (!b) {
                    System.out.println(into);
                }
            }

            int pPos = into.indexOf("(");
            if (pPos >= 0) {
                String tableName = into.substring(0, pPos).trim();
                if (tableList.contains(tableName) && (ctTableList.contains(tableName) == false)) {
                    ctTableList.add(tableName);
                }
            }


        }

        System.out.println("=====================");
        System.out.println(ctTableList);
    }
}
