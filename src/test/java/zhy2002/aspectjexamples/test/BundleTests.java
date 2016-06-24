package zhy2002.aspectjexamples.test;

import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class BundleTests {

    @Test
    public void testListBundle() {

        Locale au = new Locale("en", "AU");
        ResourceBundle bundle = ResourceBundle.getBundle("zhy2002.aspectjexamples.bundles.MyTestStrings", au);
        assertThat(bundle.getObject("test1"), equalTo("auauau"));
    }

    @Test
    public void testPropertyBundlee() {
        Locale zh = new Locale("zh", "CN");
        ResourceBundle bundle = ResourceBundle.getBundle("res.translation", zh);
        assertThat(bundle.getObject("value1"), equalTo("zhong wen"));
    }
}
