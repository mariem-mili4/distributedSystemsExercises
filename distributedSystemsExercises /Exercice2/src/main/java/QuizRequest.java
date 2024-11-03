import java.io.Serializable;

public class QuizRequest implements Serializable {
    private String requestType;
    private String[] answers;

    public QuizRequest(String requestType) {
        this.requestType = requestType;
    }

    public QuizRequest(String requestType, String[] answers){
        this.requestType = requestType;
        this.answers = answers;
    }

    public String getRequestType(){
        return requestType;
    }
    public String[] getAnswers(){
        return answers;
    }
}
