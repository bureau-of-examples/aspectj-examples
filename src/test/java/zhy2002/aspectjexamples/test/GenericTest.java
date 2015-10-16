package zhy2002.aspectjexamples.test;

import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Test java generic behaviour.
 */
public class GenericTest {

    @Test
    public void retainGenericTypeInfoTest(){

        List<Integer> list = new ArrayList<Integer>(){}; //subclassing is required to retain generic info in super class

        ParameterizedType genericSuperClass = ((ParameterizedType)list.getClass().getGenericSuperclass());

        assertThat(genericSuperClass.getActualTypeArguments().length, equalTo(1));

        Type typeParamClass = genericSuperClass.getActualTypeArguments()[0];

        assertThat(typeParamClass, sameInstance(Integer.class));

    }

}
