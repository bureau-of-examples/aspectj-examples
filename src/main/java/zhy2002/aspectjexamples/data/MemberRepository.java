package zhy2002.aspectjexamples.data;


import zhy2002.aspectjexamples.domain.Member;
import zhy2002.aspectjexamples.domain.dto.MemberReport;

public interface MemberRepository {

    Member save(Member member);

    Member get(Long id);

    MemberReport newMembersThisWeekReport();
}
