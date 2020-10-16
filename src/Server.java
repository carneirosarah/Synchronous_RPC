/*
RPC Síncrono
O servidor recebe um número e faz uma busca exaustiva para verificar se este número é primo.
Retorna ao cliente verdadeiro se o número é primo, caso contrário retorna falso.
Autor: Sarah R L Carneiro
*/

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(int port) throws IOException {

        this.serverSocket = new ServerSocket(port);
    }

    public Socket acceptConnection () throws IOException {

        return this.serverSocket.accept();
    }

    public static void closeSocket (Socket socket) throws IOException {

        socket.close();
    }

    public static void handleRequest (Socket socket) throws IOException {

        try {

            int count = 0;
            Boolean flag = true;
            ObjectOutput output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Integer number = (Integer) input.readObject();
            System.out.println("Server - Mensagem Recebida: " + number );

            for (int i = 1; i < number; i ++) {

                if (count > 2) {

                    flag = false;
                    break;

                } else if (number % i == 0) {

                    count++;
                }
            }

            output.writeObject(flag);
            output.flush();

            input.close();
            output.close();

        } catch (IOException | ClassNotFoundException e) {

            e.printStackTrace();
        } finally {
            closeSocket(socket);
        }
    }

    public static void main(String[] args) {

        try {

            Server server = new Server(5000);
            System.out.println("Server - Aguardando Conexão na porta 5000 ...");

            while (true) {

                Socket socket = server.acceptConnection();
                System.out.println("Server - Cliente Conectado");

                Server.handleRequest(socket);
                System.out.println("Server - Cliente Finalizado");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
