import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class live_study_Dashboard {
    private static final String token = "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";
    static int total_issue = 18;
    public static void main(String[] args) throws IOException {
        GitHub github = new GitHubBuilder().withJwtToken(token).build();
        GHRepository repository = github.getRepository("whiteship/live-study");
        // github (이름/프로젝트명)

        List<IssueWithCreationDate> closeIssues = new ArrayList<>();
        // 프로젝트에서 있어지는 이슈들

        for (GHIssue issue : repository.getIssues(GHIssueState.CLOSED)) {
            String createdAt = issue.getCreatedAt().toString();
            closeIssues.add(new IssueWithCreationDate(issue, createdAt));
        }

        closeIssues.sort(Comparator.comparing(IssueWithCreationDate::getCreatedAt));

        for (IssueWithCreationDate issues : closeIssues) {
            GHIssue issue = issues.getIssue();
            System.out.println(issue.getTitle());
            System.out.println("-------------------------------------------------------------------------------------");
            List<GHIssueComment> comments = issue.getComments();
            int cnt = 0;
            for (GHIssueComment comment : comments) {
                String username = comment.getUser().getName();
                if (username == null)
                    continue;
                System.out.println("숙제 한 사람 : " + username);
                cnt++;
                if (cnt == 10)
                    break;
            }
            System.out.println();
        }
    }

    // 이슈 날짜순 저장 위한 데이터 형식
    static class IssueWithCreationDate {
        private GHIssue issue;
        private String createdAt;

        public IssueWithCreationDate(GHIssue issue, String createdAt) {
            this.issue = issue;
            this.createdAt = createdAt;
        }

        public GHIssue getIssue() {
            return issue;
        }

        public String getCreatedAt() {
            return createdAt;
        }
    }
}
