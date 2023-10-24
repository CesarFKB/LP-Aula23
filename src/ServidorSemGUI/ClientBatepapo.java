package ServidorSemGUI;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientBatepapo implements Runnable {
    private SocketClient clientSocket;
    private Scanner scanner;

    public ClientBatepapo() {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        try {
            clientSocket = new SocketClient(new Socket(ServidorBatepapo.ADDRESS, ServidorBatepapo.PORT));
            new Thread(this).start();
            messageLoop();
        } finally {
            clientSocket.close();
        }
    }

    private void messageLoop() throws IOException {
        String msg;
        System.out.println("Digite uma mensagem (ou <sair> para finalizar): ");
        do {
            System.out.print("<- ");
            msg = scanner.nextLine();
            clientSocket.sendMessage(msg);
        } while (!msg.equalsIgnoreCase("sair"));
    }

    @Override
    public void run() {
        String msg;
        while ((msg = clientSocket.getMessage()) != null) {
            System.out.println("\n-> " + msg + "\n");
            System.out.println("Digite uma mensagem (ou <sair> para finalizar): ");
        }
    }

    public static void main(String[] args) {
        System.out.println("CONSOLE DO CLIENTE");
        try {
            ClientBatepapo client = new ClientBatepapo();
            client.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar cliente: " + e.getMessage());
        }
        System.out.println("Cliente finalizado!");
    }
}
