package ServidorComGUI;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class ServidorBatepapo {
    public static final String ADDRESS = "localhost";
    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<SocketClient> clients = new LinkedList<>();

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta: " + PORT);
        clientConnectionLoop();
    }

    private void clientConnectionLoop() throws IOException {
        System.out.println("Aguardando conexao de um cliente!");
        while (true) {
            SocketClient clientSocket = new SocketClient(serverSocket.accept());
            clients.add(clientSocket);
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    private void clientMessageLoop(SocketClient clientSocket) {
        String msg;
        try {
            while ((msg = clientSocket.getMessage()) != null) {
                if ("sair".equalsIgnoreCase(msg)) {
                    return;
                }
                System.out.printf("<- Cliente %s: %s\n", clientSocket.getRemoteSocketAddress(), msg);
                sendMsgToAll(clientSocket, msg);
            }
        } finally {
            clientSocket.close();
        }
    }

    private void sendMsgToAll(SocketClient sender, String msg) {
        Iterator<SocketClient> iterator = clients.iterator();
        while (iterator.hasNext()) {
            SocketClient clientSocket = iterator.next();
            if (!sender.equals(clientSocket)) {
                if (!clientSocket.sendMessage("Cliente " + sender.getRemoteSocketAddress() + ": " + msg)) {
                    iterator.remove();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("CONSOLE DO SERVIDOR");
        try {
            ServidorBatepapo server = new ServidorBatepapo();
            server.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar servidor: " + e.getMessage());
        }
        System.out.println("Servidor finalizado!");
    }

}
