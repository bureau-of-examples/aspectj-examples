package zhy2002.aspectjexamples.data.impl;

import zhy2002.aspectjexamples.data.MemberRepository;
import zhy2002.aspectjexamples.domain.Member;
import zhy2002.aspectjexamples.domain.dto.MemberReport;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberRepositoryImpl implements MemberRepository {

    public static final Logger logger = Logger.getLogger(MemberRepositoryImpl.class.getName());

    public Member save(Member member){

        logger.log(Level.INFO, "save({0})", new Object[]{member});
        return member;
    }

    @Override
    public Member get(Long id) {
        if(id % 2 == 1){
            logger.warning("Cannot find member " + id);
            return null;
        }

        Member member = new Member();
        member.setId(id);
        member.setName("Member" + id);
        logger.log(Level.INFO, "get({0})", new Object[]{member.getName()});
        return member;
    }

    @Override
    public MemberReport newMembersThisWeekReport(){
        logger.info("Retrieving new members added this week.");
        return new MemberReport();
    }

}
