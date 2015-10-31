package zhy2002.aspectjexamples.test;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test Java language features.
 */
public class JavaTest {

    @Test
    public void canDefineAnotherPackageClassInSameFile() {
        TopLevelPackageLevelClass topLevelPackageLevelClass = new TopLevelPackageLevelClass();//defined in another java file
        assertThat(topLevelPackageLevelClass, instanceOf(Object.class));
    }

    @Test
    public void weakReferenceIsClearedAfterGC(){
        Object obj  = new Object();
        WeakReference<Object> objectWeakReference = new WeakReference<>(obj);
        assertThat(obj, sameInstance(objectWeakReference.get()));

        System.gc();
        assertThat(obj, sameInstance(objectWeakReference.get())); //still has the strong reference

        obj = null;
        System.gc();
        assertThat(objectWeakReference.get(), nullValue());

    }

    @Test
    public void canGetJvmMxBeans(){
        long totalCompilationTime = ManagementFactory.getCompilationMXBean().getTotalCompilationTime();
        assertThat(totalCompilationTime, greaterThan(0L));
    }

    @Test
    public void weakHashMapEntryIsRemovedWhenKeyObjectIsCollected(){
        WeakHashMap<Object, String> weakHashMap = new WeakHashMap<>();
        Object obj = new Object();
        weakHashMap.put(obj, "test1");
        assertThat(weakHashMap.size(), equalTo(1));
        obj = null;
        System.gc();
        assertThat(weakHashMap.size(), equalTo(0));
    }

}
