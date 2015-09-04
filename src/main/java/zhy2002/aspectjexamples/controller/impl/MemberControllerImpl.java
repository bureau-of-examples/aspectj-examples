package zhy2002.aspectjexamples.controller.impl;


import zhy2002.aspectjexamples.controller.MemberController;
import zhy2002.aspectjexamples.data.MemberRepository;
import zhy2002.aspectjexamples.domain.Member;

import java.util.logging.Logger;

public class MemberControllerImpl implements MemberController {

    private static Logger logger = Logger.getLogger(MemberControllerImpl.class.getName());

    private MemberRepository memberRepository;

    @Override
    public void save(Member member) {
         memberRepository.save(member);
    }

    @Override
    public void saveAll(Iterable<Member> members) {
        members.forEach(memberRepository::save);
    }

    @Override
    public void showMember(Long id) {
        Member member = memberRepository.get(id);
        if(member != null){
           logger.info("showMember " + id);
        }
    }
}
