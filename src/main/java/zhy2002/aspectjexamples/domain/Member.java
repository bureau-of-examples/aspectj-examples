package zhy2002.aspectjexamples.domain;


public class Member {

    private Long id;
    private String name;
    private Integer age;

    public Member(){}

    public Member(String name, Integer age){
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    protected void showFamilySecret() {}
}
