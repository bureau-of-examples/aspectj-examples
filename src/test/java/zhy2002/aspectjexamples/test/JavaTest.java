package zhy2002.aspectjexamples.test;

import org.junit.Test;
import zhy2002.aspectjexamples.serviceloader.MyTestService;

import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.util.*;

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
    public void canGetServiceViaServiceLoader(){

        ServiceLoader<MyTestService> myTestServiceLoader = ServiceLoader.load(MyTestService.class);
        List<MyTestService> myTestServices = new ArrayList<>();
        for(MyTestService myTestService : myTestServiceLoader) {
            myTestServices.add(myTestService);
        }

        assertThat(myTestServices, hasSize(2));
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

    @Test
    public void hashCodeTest(){

        HashCodeTest hashCodeTest = new HashCodeTest("test1");
        HashSet<HashCodeTest> hashSet = new HashSet<>();
        hashSet.add(hashCodeTest);
        HashCodeTest hashCodeTest2 = new HashCodeTest("test1");

        assertThat(hashSet, contains(equalTo(hashCodeTest2)));

        hashCodeTest.value = "test2";
        assertThat(hashSet.contains(hashCodeTest2), equalTo(false)); //does not equal as value is changed

        hashCodeTest2.value = hashCodeTest.value;
        assertThat(hashSet.contains(hashCodeTest2), equalTo(false)); //cannot find hashCodeTest because it is in the wrong bucket
    }

    @Test
    public void boxingTest() {
        Integer a = 3;
        Integer b = 3;

        assertThat(a == b, equalTo(true)); //small number is interned.

        Integer c = 6;

        Integer d = 1000;
        Integer e = 2000;
        Integer f = 3000;

        assertThat(f == d + e, equalTo(true)); //c is auto un-boxed

        assertThat(f == (Integer) (d + e), equalTo(false)); //d + e is boxed into a difference instance

        assertThat(c == (Integer)(a + b), equalTo(true)); //small integers less than 128 is interned.
        assertThat(c == (Integer)(return2() + return4()), equalTo(true)); //same as above

        Long g = 6L;

        assertThat(g == (a + b), equalTo(true)); //g is auto un-boxed and then the result of a+b is promoted to Long
        assertThat(g.equals(a + b), equalTo(false)); //a + b auto boxed to an Integer

        Integer h = 1000;

        assertThat(h == d, equalTo(false)); //large number not interned

    }

    private int return2() {
        return 2;
    }

    private int return4() {
        return 4;
    }

    @Test
    public void removeFromMapValuesViewWillRemoveTheFirstEntryWithThisValue() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 1);
        map.values().remove(1);
        assertThat(map.size(), equalTo(1));
        assertThat(map.get(2), equalTo(1));
    }


    @Test
    public void reflectionPolymorphism(){

        Child child = new Child();
        try {
            Parent.class.getMethod("getValue").invoke(child);
            assertThat(true, equalTo(false));
        }catch (Throwable ex) {
            assertThat(ex, instanceOf(IllegalArgumentException.class));
        }
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

class Parent {
    public String getValue(){
        return "parent";
    }
}

class Child {
    public String getValue(){
        return "child";
    }
}

class HashCodeTest{
    public String value;

    public HashCodeTest(){}

    public HashCodeTest(String value){
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof HashCodeTest))
            return false;

        HashCodeTest hashCodeTest = (HashCodeTest) obj;
        return Objects.equals(hashCodeTest.value, this.value);
    }
}

