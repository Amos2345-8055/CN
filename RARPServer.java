import java.net.*;
import java.util.*;

public class RARPServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket s = new DatagramSocket(8888);
        Map<String,String> table = new HashMap<>();
        table.put("AA:BB:CC:DD:EE:01","192.168.1.1");
        table.put("AA:BB:CC:DD:EE:02","192.168.1.2");
        table.put("AA:BB:CC:DD:EE:03","192.168.1.3");

        System.out.println("RARP Server running...");
        while (true) {
            byte[] buf = new byte[1024];
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            s.receive(p);
            String mac = new String(p.getData(), 0, p.getLength());
            System.out.println("Request for MAC: " + mac);

            String ip = table.get(mac);
            if (ip == null) {
                ip = "192.168.1." + (table.size() + 1);
                table.put(mac, ip);
                System.out.println("New mapping: " + mac + " -> " + ip);
            } else {
                System.out.println("Found: " + mac + " -> " + ip);
            }

            byte[] outBuf = ip.getBytes();
            s.send(new DatagramPacket(outBuf, outBuf.length, p.getAddress(), p.getPort()));
        }
    }
}
