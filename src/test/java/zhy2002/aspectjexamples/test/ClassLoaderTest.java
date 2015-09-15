package zhy2002.aspectjexamples.test;

import org.testng.annotations.Test;
import zhy2002.aspectjexamples.infrastructure.DirClassLoader;
import zhy2002.aspectjexamples.infrastructure.AllowDuplicateClassLoader;
import zhy2002.aspectjexamples.infrastructure.ResolvingClassLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;

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


    @Test(expectedExceptions = LinkageError.class)
    public void cannotLoadTheSameClassLoaderTwiceWithSameClassLoaderInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException{

        //arrange
        String dir = "." + File.separator + "etc";
        ClassLoader mockClassLoader = new AllowDuplicateClassLoader(dir);

        //action
        try {
            Class<?> clazz1 = mockClassLoader.loadClass("zhy2002.SysPropDumper");
            Object instance = clazz1.newInstance();
            Class<?> clazz2 = mockClassLoader.loadClass("zhy2002.SysPropDumper"); //cannot define the same full-qualified class name twice with the same class loader instance.
            //java.lang.LinkageError: loader (instance of  zhy2002/aspectjexamples/infrastructure/MockClassLoader): attempted  duplicate class definition for name: "zhy2002/SysPropDumper"
        }catch (LinkageError err){
            err.printStackTrace();
            throw err;
        }

    }

    @Test(expectedExceptions = NoClassDefFoundError.class)
    public void resolveClassWillNotCheckAllMissingClasses()  throws ClassNotFoundException{

        //arrange
        String dir = "." + File.separator + "etc";
        ClassLoader mockClassLoader = new ResolvingClassLoader(dir);

        //action
        Class<?> clazz1 = mockClassLoader.loadClass("zhy2002.SysPropDumper"); //will not call initializer

        //assertion
        assertThat(clazz1, notNullValue());

        //action cause exception
        clazz1.getMethods();
    }


    @Test
    public void forNameInitTrueWillCanStaticInitializer() throws ClassNotFoundException{

        //arrange
        String dir = "." + File.separator + "etc";
        ClassLoader mockClassLoader = new AllowDuplicateClassLoader(dir);

        //action
        Class.forName("zhy2002.SysPropDumper", true, mockClassLoader); //will call the static initialize

        //assertion
        System.out.println("Static field is initialised");

    }

    //see http://blog.csdn.net/xyang81/article/details/7292380#NetWorkClassLoader
}
