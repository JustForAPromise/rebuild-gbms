package login.com.studentScoreAllInfo.api;

import lombok.Data;

@Data
public class SearchDetailApiGet {
    private String no;
    private Integer studentId;
    private Integer type;

    private String name;

    private Integer majorId;
}
