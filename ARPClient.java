import java.net.*; import java.util.*;
public class ARPClient {
  public static void main(String[] a) throws Exception {
    DatagramSocket s = new DatagramSocket();
    InetAddress server = InetAddress.getByName("localhost");
    Scanner sc = new Scanner(System.in);
    byte[] buf = new byte[1024];
    while (true) {
      System.out.print("Enter IP (or 'exit'): ");
      String ip = sc.nextLine();
      if (ip.equalsIgnoreCase("exit")) break;
      s.send(new DatagramPacket(ip.getBytes(), ip.length(), server, 9999));
      DatagramPacket p = new DatagramPacket(buf, buf.length);
      s.receive(p);
      System.out.println("MAC for "+ip+": "+new String(p.getData(),0,p.getLength())+"\n");
    }
    s.close(); sc.close();
  }
}
