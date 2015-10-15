package zhy2002.aspectjexamples.test;

import org.junit.Test;
import zhy2002.aspectjexamples.domain.Member;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Test Jndi.
 */
public class JndiTest {

    @Test
    public void simpleJndiCanBindAndLookUp() throws NamingException {

        InitialContext initialContext = new InitialContext();
        Member member = new Member("Test1", 29);
        initialContext.bind("member1", member);

        Member result = (Member)initialContext.lookup("member1");

        assertThat(result, sameInstance(member));

    }

    @Test
    public void simpleJndiCanCreateSubContext() throws NamingException {
        InitialContext initialContext = new InitialContext();
        Context subContext = initialContext.createSubcontext("test");
        Member member = new Member("Test1", 29);
        subContext.bind("member1", member);

        Member result = (Member)subContext.lookup("member1");
        assertThat(result, sameInstance(member));

        result = (Member)initialContext.lookup("test.member1");
        assertThat(result, sameInstance(member));
    }


}
