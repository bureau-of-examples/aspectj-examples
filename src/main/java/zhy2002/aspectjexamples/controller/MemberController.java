package zhy2002.aspectjexamples.controller;


import zhy2002.aspectjexamples.domain.Member;

public interface MemberController {


    void save(Member member);

    void saveAll(Iterable<Member> members);

    void showMember(Long id);

}
