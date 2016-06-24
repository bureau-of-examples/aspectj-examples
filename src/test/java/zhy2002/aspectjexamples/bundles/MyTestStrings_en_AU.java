package zhy2002.aspectjexamples.bundles;

import java.util.ListResourceBundle;

/**
 * Created by ZHY on 24/06/2016.
 */
public class MyTestStrings_en_AU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"test1", "auauau"}
        };
    }
}
