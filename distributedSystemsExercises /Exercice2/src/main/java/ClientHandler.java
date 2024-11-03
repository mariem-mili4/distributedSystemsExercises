import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<QuizQuestion> quizQuestions;

    public ClientHandler(Socket clientSocket, List<QuizQuestion> quizQuestions) {
        this.clientSocket = clientSocket;
        this.quizQuestions = quizQuestions;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());  // Create OutputStream first
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            boolean clientConnected = true;
            while (clientConnected) {
                try {
                    QuizRequest request = (QuizRequest) in.readObject();

                    if (request.getRequestType().equals("GET_QUIZ")) {
                        out.writeObject(new QuizResponse(quizQuestions));
                        out.flush();

                    } else if (request.getRequestType().equals("SUBMIT_ANSWERS")) {
                        int score = evaluateAnswers(request.getAnswers());
                        String feedback = "Your score: " + score + " out of " + quizQuestions.size();
                        out.writeObject(new QuizResponse(score, feedback));
                        out.flush();
                        clientConnected = false;
                    }
                } catch (EOFException e) {
                    clientConnected = false;
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }



    private int evaluateAnswers(String[] clientAnswers) {
        int score = 0;
        for (int i = 0; i < quizQuestions.size(); i++) {
            if (quizQuestions.get(i).getCorrectAnswer().equals(clientAnswers[i])) {
                score++;
            }
        }
        return score;
    }
}
