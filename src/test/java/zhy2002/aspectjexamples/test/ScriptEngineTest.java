package zhy2002.aspectjexamples.test;

import org.junit.Test;
import zhy2002.aspectjexamples.domain.Member;

import javax.script.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Test script engine behaviour.
 */
public class ScriptEngineTest {

    /**
     * @see https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/api.html.
     * @throws ScriptException
     */
    @Test
    public void canCreateInstanceOfLoadedClass() throws ScriptException {

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        Member member = (Member) scriptEngine.eval("var Member = Packages.zhy2002.aspectjexamples.domain.Member; new Member('Jack', 32);");

        assertThat(member.getName(), equalTo("Jack"));
        assertThat(member.getAge(), equalTo(32));
    }
}
