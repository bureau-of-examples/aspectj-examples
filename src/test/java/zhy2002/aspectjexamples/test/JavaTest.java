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
    public void weakReferenceIsClearedAfterGC() {
        Object obj = new Object();
        WeakReference<Object> objectWeakReference = new WeakReference<>(obj);
        assertThat(obj, sameInstance(objectWeakReference.get()));

        System.gc();
        assertThat(obj, sameInstance(objectWeakReference.get())); //still has the strong reference

        obj = null;
        System.gc();
        assertThat(objectWeakReference.get(), nullValue());

    }

    @Test
    public void canGetJvmMxBeans() {
        long totalCompilationTime = ManagementFactory.getCompilationMXBean().getTotalCompilationTime();
        assertThat(totalCompilationTime, greaterThan(0L));
    }

    @Test
    public void weakHashMapEntryIsRemovedWhenKeyObjectIsCollected() {
        WeakHashMap<Object, String> weakHashMap = new WeakHashMap<>();
        Object obj = new Object();
        weakHashMap.put(obj, "test1");
        assertThat(weakHashMap.size(), equalTo(1));
        obj = null;
        System.gc();
        assertThat(weakHashMap.size(), equalTo(0));
    }


    @Test
    public void multipleBooleanTrueOrFalseInstancesPossible() {

        boolean result = new Boolean(null) == Boolean.FALSE;
        assertThat(result, equalTo(false));
    }

    @Test
    public void fieldWithSameNameIsHidden() {

        C2 c2 = new C2();

        assertThat(c2.val, equalTo(20));

        C1 c1 = c2;
        assertThat(c1.val, equalTo(10));

    }

    @Test
    public void printFiboTest() {
        printFibo2(0);
        printFibo2(1);
        printFibo2(2);
        printFibo2(3);
        printFibo2(4);
        printFibo2(5);
    }


    public void printFibo(int count) {
        if (count <= 0) {
            System.out.println();
            return;
        }

        if (count == 1) {
            System.out.println("1");
            return;
        }

        System.out.print("1 1");
        count -= 2;
        int first = 1, second = 1;
        for (int i = 0; i < count; i++) {
            int third = first + second;
            System.out.print(" ");
            System.out.print(third);
            first = second;
            second = third;
        }
        System.out.println();
    }

    public void printFibo2(int count){
        int previous = 0;
        int current = 1;

        for(int i=0; i<count; i++){
            System.out.println(current);
            int next = previous + current;
            previous = current;
            current = next;
        }
        System.out.println();
    }


}

class C1 {
    int val = 10;

    private int f1() {
        return 0;
    }
}

class C2 extends C1 {
    int val = 20;

    protected String f1() {
        return "a";
    }
}

