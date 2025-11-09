import java.net.*; import java.util.*;
public class ARPServer {
  public static void main(String[] a) throws Exception {
    DatagramSocket s = new DatagramSocket(9999);
    Map<String,String> arp = new HashMap<>();
    arp.put("192.168.1.1","AA:BB:CC:DD:EE:01");
    arp.put("192.168.1.2","AA:BB:CC:DD:EE:02");
    arp.put("192.168.1.3","AA:BB:CC:DD:EE:03");
    System.out.println("ARP Server running...");
    byte[] buf = new byte[1024];
    while (true) {
      DatagramPacket p = new DatagramPacket(buf, buf.length); s.receive(p);
      String ip = new String(p.getData(),0,p.getLength());
      String mac = arp.computeIfAbsent(ip, k -> {
        StringBuilder m=new StringBuilder(); Random r=new Random();
        for(int i=0;i<6;i++) m.append(String.format("%02X",r.nextInt(256))).append(i<5?":":"");
        return m.toString();
      });
      System.out.println(ip+" -> "+mac);
      s.send(new DatagramPacket(mac.getBytes(), mac.length(), p.getAddress(), p.getPort()));
    }
  }
}
