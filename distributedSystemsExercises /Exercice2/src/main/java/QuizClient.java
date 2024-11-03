import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class QuizClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sending GET_QUIZ request...");
        out.writeObject(new QuizRequest("GET_QUIZ"));
        out.flush();
        QuizResponse response = (QuizResponse) in.readObject();
        List<QuizQuestion> questions = response.getQuestions();

        String[] answers = new String[questions.size()];

        for (int i = 0; i < questions.size(); i++){
            QuizQuestion question = questions.get(i);
            System.out.println("Question " +(i+1) + ":" + question.getQuestionText());
            String[] options = question.getOptions();
            for (int j = 0; j < options.length; j++){
                System.out.println((j+1)+". " +options[j]);
            }
            System.out.print("Your answer: ");
            int answerIndex = scanner.nextInt() - 1;
            answers[i] = options[answerIndex];
        }
        out.writeObject(new QuizRequest("SUBMIT_ANSWERS", answers));
        System.out.println("Waiting for response...");
        QuizResponse result = (QuizResponse) in.readObject();
        System.out.println("Received quiz questions.");
        System.out.println("Score: " + result.getScore());
        System.out.println("Feedback: " + result.getFeedback());
    }
}
