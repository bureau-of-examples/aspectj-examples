package zhy2002.aspectjexamples.test;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ResourceLoadingTest {

    @BeforeClass
    public void prepareClass(){
        System.out.println("Testing in " + System.getProperty("user.dir"));
    }

    @Test
    public void canLoadClassResourceFromRelativePath(){

        //action
        URL url = ResourceLoadingTest.class.getResource("../datafile1.txt");

        //assertion
        assertThat(url, not(nullValue()));
        assertThat(new File(url.getFile()).exists(), equalTo(true));

        //action
        url = ResourceLoadingTest.class.getResource("../datafile1xxx.txt");

        //assertion
        assertThat(url, nullValue());
    }

    @Test
    public void canLoadClassResourceFromAbsolutePath(){

        //action
        URL url = ResourceLoadingTest.class.getResource("/zhy2002/aspectjexamples/datafile1.txt");

        //assertion
        assertThat(url, not(nullValue()));
        assertThat(new File(url.getFile()).exists(), equalTo(true));

        //action
        url = ResourceLoadingTest.class.getResource("/zhy2002/aspectjexamples/datafile1xxx.txt");

        //assertion
        assertThat(url, nullValue());
    }

    @Test
    public void canLoadClassLoaderResourceFromAbsolutePath(){

        //action
        URL url = ResourceLoadingTest.class.getClassLoader().getResource("zhy2002/aspectjexamples/datafile1.txt");

        //assertion
        assertThat(url, not(nullValue()));
        assert url != null;
        assertThat(new File(url.getFile()).exists(), equalTo(true));
    }

    @Test
    public void cannotLoadClassLoaderResourceWithLeadingSlash(){
        //action
        URL url = ResourceLoadingTest.class.getClassLoader().getResource("/zhy2002/aspectjexamples/datafile1.txt");

        //assertion
        assertThat(url, nullValue());
    }

    @Test
    public void canLoadSystemResourceFromAbsolutePath(){

        //action
        URL url =  ClassLoader.getSystemResource("zhy2002/aspectjexamples/datafile1.txt");

        //assertion
        assertThat(url, not(nullValue()));
        assertThat(new File(url.getFile()).exists(), equalTo(true));
    }

    @Test
    public void canLoadSystemResourceFromTestTargetAbsolutePath(){

        //action
        URL url =  ClassLoader.getSystemResource("logging.properties");

        //assertion
        assertThat(url, not(nullValue()));
        assertThat(new File(url.getFile()).exists(), equalTo(true));

    }

}
