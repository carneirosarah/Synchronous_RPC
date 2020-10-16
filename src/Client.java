/*
RPC Síncrono
O cliente envia um número ao servidor e aguarda a resposta que indicará se o número é primo ou não.
Autor: Sarah R L Carneiro
*/

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class Client {

    public static void main(String[] args) {

        try {

            while (true) {

                Socket socket = new Socket("localhost", 5000);

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                Random random = new Random();
                Integer number = random.nextInt(1000000);

                output.writeObject(number);
                output.flush();

                Boolean isPrimeNumber = (Boolean) input.readObject();

                if (isPrimeNumber) {
                    System.out.println("Client - O número " + number + " é primo!!");
                } else {
                    System.out.println("Client - O número " + number + " não é primo!!");
                }

                input.close();
                output.close();

                Thread.sleep(1000);

            }
        } catch (IOException | ClassNotFoundException |InterruptedException e) {

            e.printStackTrace();
        }

    }
}
