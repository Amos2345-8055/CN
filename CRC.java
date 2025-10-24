import java.io.*;

class CRC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter Generator: ");
        String gen = br.readLine();

        System.out.print("Enter Data: ");
        String data = br.readLine();

        // Append zeros to data
        String code = data;
        for (int i = 1; i < gen.length(); i++)
            code += "0";

        // Compute remainder
        String remainder = divide(code, gen);

        // Transmitted code word
        String transmitted = data + remainder;
        System.out.println("\nTransmitted Code Word: " + transmitted);

        // Receiver side
        System.out.print("Enter the received Code Word: ");
        String received = br.readLine();

        String check = divide(received, gen);

        // Check for all zeros in remainder
        boolean error = false;
        for (int i = 0; i < check.length(); i++) {
            if (check.charAt(i) != '0') {
                error = true;
                break;
            }
        }

        if (error)
            System.out.println(" The received code word contains ERRORS.");
        else
            System.out.println(" The received code word contains NO errors.");
    }

    // Mod-2 division (XOR-based)
    static String divide(String dividend, String divisor) {
        char[] remainder = dividend.toCharArray();
        int divisorLen = divisor.length();

        for (int i = 0; i <= remainder.length - divisorLen; i++) {
            if (remainder[i] == '1') {
                for (int j = 0; j < divisorLen; j++) {
                    remainder[i + j] = xor(remainder[i + j], divisor.charAt(j));
                }
            }
        }

        // Extract remainder (last divisorLen-1 bits)
        StringBuilder rem = new StringBuilder();
        for (int i = remainder.length - divisorLen + 1; i < remainder.length; i++) {
            rem.append(remainder[i]);
        }

        return rem.toString();
    }

    static char xor(char a, char b) {
        return (a == b) ? '0' : '1';
    }
}
