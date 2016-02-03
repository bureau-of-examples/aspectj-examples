package zhy2002.aspectjexamples.test;

import org.junit.Test;

import java.lang.annotation.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Test Java Annotation behaviour.
 */
public class AnnotationTest {

    @Test
    public void annotationIsInheritedViaSuperClass(){

        assertThat(SuperClass.class.getAnnotations().length, equalTo(1));
        assertThat(ChildClass.class.getAnnotations().length, equalTo(1));
        assertThat(ChildClass.class.getAnnotation(MyAnnotation.class), notNullValue());
    }

    @Test
    public void methodAnnotationCannotBeInherited() throws NoSuchMethodException {

        assertThat(SuperClass.class.getMethod("f1").getAnnotations().length, equalTo(1));
        assertThat(ChildClass.class.getMethod("f1").getAnnotations().length, equalTo(0));
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@interface MyAnnotation {
    String value();
}

@MyAnnotation("super")
class SuperClass {

    @MyAnnotation("super method")
    public void f1(){}

}


class ChildClass extends SuperClass{

    @Override
    public void f1(){

    }

}
