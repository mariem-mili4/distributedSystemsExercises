import java.io.*;
import java.net.*;
import java.util.Random;

public class SecretNbrServer {
     private static final int PORT = 9999;
     private static  int secretNumber;

    public static void main(String[] args) throws IOException {
        generateSecretNumber();
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);
        while(true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected" + socket.getInetAddress());
            handleClient(socket);
            }
    }
    private static void generateSecretNumber(){
        Random random = new Random();
        secretNumber = random.nextInt(101);
    }
    private static void handleClient(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        String inputLine;
        while ((inputLine = in.readLine()) !=null){
            System.out.println("Received guess :"+ inputLine);
            handleGuess(inputLine,out);
        }
    }

    private static  void handleGuess(String inputLine, PrintWriter out){
        int guess = Integer.parseInt(inputLine);
        if(guess < secretNumber){
            out.println("Your guess is too low !");
        } else if (guess >secretNumber ) {
            out.println("Your guess is too high  !");
        }else {
            out.println("Congratulations! You guessed the number!");

        }

    }

}

