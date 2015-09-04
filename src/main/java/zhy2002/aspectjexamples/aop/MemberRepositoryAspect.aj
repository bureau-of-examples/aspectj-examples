package zhy2002.aspectjexamples.aop;

import zhy2002.aspectjexamples.controller.OrderController;
import zhy2002.aspectjexamples.controller.impl.OrderControllerImpl;
import zhy2002.aspectjexamples.data.MemberRepository;
import zhy2002.aspectjexamples.data.impl.MemberRepositoryImpl;

public privileged /*accessing private field*/ aspect MemberRepositoryAspect {

    pointcut saveMethod(MemberRepository memberRepository) : execution(public * *.save(..)) && target(memberRepository);

    pointcut callGetMethodInOrderController(OrderController orderController, MemberRepository memberRepository) : call(public * *.get(..)) && within(OrderControllerImpl) && this(orderController) && target(memberRepository);

    before(MemberRepository memberRepository): saveMethod(memberRepository) {
        //Logger logger = Logger.getLogger(MemberRepositoryAspect.class.getName()); //this will not return a mock object for some reason.
        MemberRepositoryImpl.logger.info("before.saveMethod");
    }

    before(OrderController orderController, MemberRepository memberRepository)
            : callGetMethodInOrderController(orderController, memberRepository) {
        MemberRepositoryImpl.logger.info("before.callGetMethodInOrderController");
    }
}
