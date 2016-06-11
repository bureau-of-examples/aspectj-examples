package zhy2002.aspectjexamples.domain.dto;


import java.time.LocalDateTime;

public class MemberReport {

    private String title;
    private String description;
    private LocalDateTime dateTimeCreated;

    public MemberReport() {
    }

    public MemberReport(String title) {
        this(title, null);
    }

    public MemberReport(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
}
