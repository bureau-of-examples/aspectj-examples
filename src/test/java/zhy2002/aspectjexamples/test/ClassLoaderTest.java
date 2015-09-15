package zhy2002.aspectjexamples.test;

import org.testng.annotations.Test;
import zhy2002.aspectjexamples.infrastructure.DirClassLoader;

import java.io.File;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * ClassLoader tests.
 */
public class ClassLoaderTest {

    @Test
    public void shouldReturnSameInstanceLoadedByAppClassLoader() throws ClassNotFoundException{

        //arrange
        String dir = "." + File.separator + "target" + File.separator + "classes";
        DirClassLoader classLoader1 = new DirClassLoader(dir);
        DirClassLoader classLoader2 = new DirClassLoader(dir);

        //action
        Class<?> clazz1 = classLoader1.loadClass("java.lang.String");
        Class<?> clazz2 = classLoader2.loadClass("java.lang.String");

        //assertion
        assertThat(clazz1, sameInstance(clazz2));

    }

    @Test
    public void shouldReturnDifferenceClassesLoadedByDirClassLoader() throws ClassNotFoundException{

        //arrange
        String dir = "." + File.separator + "etc";
        DirClassLoader classLoader1 = new DirClassLoader(dir);
        DirClassLoader classLoader2 = new DirClassLoader(dir);

        //action
        Class<?> clazz1 = classLoader1.loadClass("zhy2002.SysPropDumper");
        Class<?> clazz2 = classLoader2.loadClass("zhy2002.SysPropDumper");

        //assertion
        assertThat(clazz1, not(sameInstance(clazz2)));
    }

    //see http://blog.csdn.net/xyang81/article/details/7292380#NetWorkClassLoader
}
