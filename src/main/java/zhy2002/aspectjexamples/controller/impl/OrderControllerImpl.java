package zhy2002.aspectjexamples.controller.impl;

import zhy2002.aspectjexamples.controller.OrderController;
import zhy2002.aspectjexamples.data.MemberRepository;
import zhy2002.aspectjexamples.domain.Member;


import java.util.logging.Logger;

public class OrderControllerImpl implements OrderController {

    private static Logger logger = Logger.getLogger(OrderControllerImpl.class.getName());

    private MemberRepository memberRepository;

    @Override
    public void showOrders(Long id) {

        Member member = memberRepository.get(id);
        if(member != null){
            logger.info("showOrders for member " + id);
        } else {
            logger.warning("Cannot show member " + id);
        }
    }

    @Override
    public void orderSummaryReport(){
        logger.info("orderSummaryReport");
    }
}
