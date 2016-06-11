package zhy2002.aspectjexamples.test.anotherpackage;

import zhy2002.aspectjexamples.domain.Member;
import zhy2002.aspectjexamples.test.yetanother.HyperMember;

/**
 * test protected modifier.
 */
public class SuperMember extends Member {

    static void test() {
        SuperMember superMember = new SuperMember();
        superMember.showFamilySecret();

        Member member = superMember;
        //member.showFamilySecret(); //not ok

        HyperMember hyperMember = new HyperMember();
        hyperMember.showFamilySecret();
        //hyperMember.showNewSecret(); //not ok

        //in summary, a class can only access the protected methods that exist in itself.
    }
}
