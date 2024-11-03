import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizServer {
    private  static List<QuizQuestion> quizQuestions;

    public static void main(String[] args) throws IOException {
        quizQuestions = generateQuizQuestions();
        ServerSocket serverSocket = new ServerSocket(1234) ;
        System.out.println("Quiz server is running ...");
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");
                new Thread(new ClientHandler(clientSocket, quizQuestions)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static List<QuizQuestion> generateQuizQuestions(){
        List<QuizQuestion> questions = new ArrayList<>();
        questions.add(new QuizQuestion("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, "Paris"));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, "Mars"));
        return questions;
    }

}
