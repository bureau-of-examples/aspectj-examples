package zhy2002.aspectjexamples.test;

import org.junit.Test;

import java.lang.reflect.Field;
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
    public void retainGenericTypeInfoTest() {

        List<Integer> list = new ArrayList<Integer>() {
        }; //subclassing is required to retain generic info in super class

        ParameterizedType genericSuperClass = ((ParameterizedType) list.getClass().getGenericSuperclass());

        assertThat(genericSuperClass.getActualTypeArguments().length, equalTo(1));

        Type typeParamClass = genericSuperClass.getActualTypeArguments()[0];

        assertThat(typeParamClass, sameInstance(Integer.class));

    }

    private static class MyClass {

        private List<String> myListField;

        public List<String> getMyListField() {
            return myListField;
        }

        public void setMyListField(List<String> myListField) {
            this.myListField = myListField;
        }
    }

    /**
     * This is how hibernate finds the generic type of a field.
     */
    @Test
    public void recoverFieldGenericTypeTest() {

        MyClass myObject = new MyClass();

        Field[] fields = myObject.getClass().getDeclaredFields();

        assertThat(fields.length, equalTo(1));

        Type fieldType = fields[0].getGenericType();

        assertThat(fieldType, instanceOf(ParameterizedType.class));

        ParameterizedType parameterizedFieldType = (ParameterizedType) fieldType;

        assertThat(parameterizedFieldType.getActualTypeArguments().length, equalTo(1));

        assertThat(parameterizedFieldType.getActualTypeArguments()[0], sameInstance(String.class));

        //examine the generic signature is also possible: myObject.getClass().getMethod("getMyListField").getGenericSignature()
        //I think the rule here is type information which is available at compile time could be preserved by the compiler as metadata.
    }


}

class TopLevelPackageLevelClass {


}