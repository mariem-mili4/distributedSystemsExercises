import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
    private static String SERVER_ADDRESS = "localhost";
    private static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Connected to the server !");
        String userGuess;
        while (true){
            System.out.println("Enter your guess(0-100) : ");
            userGuess= userInput.readLine();
            out.println(userGuess);
            String response = in.readLine();
            System.out.println(response);
            if (response.startsWith("Congratulations")){
                break;
            }
        }
        socket.close();
    }
}
