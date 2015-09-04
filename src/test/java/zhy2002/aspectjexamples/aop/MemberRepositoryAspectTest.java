package zhy2002.aspectjexamples.aop;

import org.mockito.InOrder;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import zhy2002.aspectjexamples.data.MemberRepository;
import zhy2002.aspectjexamples.data.impl.MemberRepositoryImpl;
import zhy2002.aspectjexamples.domain.dto.MemberReport;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

@PrepareForTest({Logger.class})
@SuppressStaticInitializationFor({"zhy2002.aspectjexamples.data.impl.MemberRepositoryImpl"})
public class MemberRepositoryAspectTest extends PowerMockTestCase {

    private Logger mockLogger;

    @BeforeClass
    public void prepareClass(){
    }

    @BeforeMethod
    public void prepareTest(){
        mockStatic(Logger.class);
        mockLogger = mock(Logger.class);
        Whitebox.setInternalState(MemberRepositoryImpl.class, mockLogger);
        Whitebox.setInternalState(ReplaceMethodAspect.class, mockLogger);
    }

    @Test
    public void mockStaticTest(){
        //arrange
        when(Logger.getLogger("test1")).thenReturn(mockLogger);

        //action
        Logger logger = Logger.getLogger("test1");

        //assertion
        assertThat(logger, sameInstance(mockLogger));
    }

    @Test
    public void beforeSaveAdviceTest(){

        //arrange
         MemberRepositoryImpl memberRepository = new MemberRepositoryImpl();

        //action
        memberRepository.save(null);
//
        //assertion
        InOrder inOrder = Mockito.inOrder(mockLogger);
        inOrder.verify(mockLogger).info("before.saveMethod");
        inOrder.verify(mockLogger).log(Level.INFO, "save({0})", new Object[]{null});
    }


    @Test
    public void repositoryReportMethodIsNotReplacedByAroundAspect(){

        //arrange
        MemberRepository memberRepository = new MemberRepositoryImpl();

        //action
        MemberReport report = memberRepository.newMembersThisWeekReport();

        //assertion
        assertThat(report, not(nullValue()));
        InOrder inOrder = Mockito.inOrder(mockLogger);
        inOrder.verify(mockLogger, Mockito.times(0)).info("Reports are not implemented.");
        inOrder.verify(mockLogger).info("Retrieving new members added this week.");

    }

}
