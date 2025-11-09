import java.net.*;
import java.util.Scanner;

public class RARPClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket();
        InetAddress srv = InetAddress.getByName("localhost");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter MAC Address: ");
        String mac = sc.nextLine();

        byte[] sendBuf = mac.getBytes();
        s.send(new DatagramPacket(sendBuf, sendBuf.length, srv, 8888));

        byte[] recvBuf = new byte[1024];
        DatagramPacket recv = new DatagramPacket(recvBuf, recvBuf.length);
        s.receive(recv);

        String ip = new String(recv.getData(), 0, recv.getLength());
        System.out.println("IP Address for " + mac + " : " + ip);

        s.close();
        sc.close();
    }
}
