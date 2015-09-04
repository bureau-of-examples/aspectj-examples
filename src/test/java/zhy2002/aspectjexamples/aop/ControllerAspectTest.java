package zhy2002.aspectjexamples.aop;

import org.mockito.InOrder;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import zhy2002.aspectjexamples.controller.MemberController;
import zhy2002.aspectjexamples.controller.OrderController;
import zhy2002.aspectjexamples.controller.impl.MemberControllerImpl;
import zhy2002.aspectjexamples.controller.impl.OrderControllerImpl;
import zhy2002.aspectjexamples.data.MemberRepository;
import zhy2002.aspectjexamples.data.impl.MemberRepositoryImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.*;

@PrepareForTest({Logger.class})
@SuppressStaticInitializationFor({"zhy2002.aspectjexamples.data.impl.MemberRepositoryImpl", "zhy2002.aspectjexamples.controller.impl.OrderControllerImpl", "zhy2002.aspectjexamples.controller.impl.MemberControllerImpl"})
public class ControllerAspectTest extends PowerMockTestCase {

    private Logger mockLogger;

    @BeforeClass
    public void prepareClass() {
    }

    @BeforeMethod
    public void prepareTest() {
        mockStatic(Logger.class);
        mockLogger = mock(Logger.class);
        Whitebox.setInternalState(MemberRepositoryImpl.class, mockLogger);
        Whitebox.setInternalState(OrderControllerImpl.class, mockLogger);
        Whitebox.setInternalState(MemberControllerImpl.class, mockLogger);
        Whitebox.setInternalState(ReplaceMethodAspect.class, mockLogger);  //cannot add aspect class to @SuppressStaticInitializationFor.
    }

    private OrderController createOrderController() throws IllegalAccessException {
        MemberRepository memberRepository = new MemberRepositoryImpl();
        OrderController orderController = new OrderControllerImpl();
        MemberModifier.field(OrderControllerImpl.class, "memberRepository").set(orderController, memberRepository);
        return orderController;
    }

    private MemberController createMemberController() throws IllegalAccessException {
        MemberRepository memberRepository = new MemberRepositoryImpl();
        MemberController memberController = new MemberControllerImpl();
        MemberModifier.field(MemberControllerImpl.class, "memberRepository").set(memberController, memberRepository);
        return memberController;
    }

    @Test
    public void beforeAspectShouldBeApplied() throws IllegalAccessException {

        //arrange
        OrderController orderController = createOrderController();

        //action
        orderController.showOrders(4L);

        //assertion
        InOrder inOrder = Mockito.inOrder(mockLogger);
        inOrder.verify(mockLogger).info("before.callGetMethodInOrderController");
        inOrder.verify(mockLogger).log(Level.INFO, "get({0})", new Object[]{"Member4"});
        inOrder.verify(mockLogger).info("showOrders for member 4");
    }

    @Test
    public void shouldNotShowMemberWhenGivenOddMemberId() throws IllegalAccessException {

        //arrange
        OrderController orderController = createOrderController();

        //action
        orderController.showOrders(3L);

        //assertion
        InOrder inOrder = Mockito.inOrder(mockLogger);
        inOrder.verify(mockLogger).info("before.callGetMethodInOrderController");
        inOrder.verify(mockLogger).warning("Cannot find member 3");
        inOrder.verify(mockLogger).warning("Cannot show member 3");

    }

    @Test
    public void beforeAspectShouldNotApply() throws IllegalAccessException{
        //arrange
        MemberController memberController = createMemberController();

        //action
        memberController.showMember(4L);

        //assertion
        InOrder inOrder = Mockito.inOrder(mockLogger);
        inOrder.verify(mockLogger, times(0)).info("before.callGetMethodInOrderController");
        inOrder.verify(mockLogger).log(Level.INFO, "get({0})", new Object[]{"Member4"});
        inOrder.verify(mockLogger).info("showMember 4");

    }

    @Test
    public void controllerReportMethodIsReplacedByAroundAspect() throws IllegalAccessException {

        //arrange
        OrderController orderController = createOrderController();

        //action
        orderController.orderSummaryReport();

        //assertion
        InOrder inOrder = Mockito.inOrder(mockLogger);
        inOrder.verify(mockLogger).info("Reports are not implemented.");
        inOrder.verify(mockLogger, times(0)).info("orderSummaryReport");
    }


}
