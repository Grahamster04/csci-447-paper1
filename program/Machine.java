import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Machine {

    static ArrayList<Machine> hardwareStorage = new ArrayList<>();

    public int MYCT; public static int MYCTMax;
    public int MMIN; public static int MMINMax;
    public int MMAX; public static int MMAXMax;
    public int CACH; public static int CACHMax;
    public int CHMIN; public static int CHMINMax;
    public int CHMAX; public static int CHMAXMax;
    public int PRP; public static int PRPMax;
    public int ERP; public static int ERPMax;

    public Machine(int mcyt, int mmin, int mmax, int cash, int chmin, int chmax, int prp, int erp) {
        this.MYCT = mcyt;
        this.MMIN = mmin;
        this.MMAX = mmax;
        this.CACH = cash;
        this.CHMIN = chmin;
        this.CHMAX = chmax;
        this.PRP = prp;
        this.ERP = erp;
    }

    public static ArrayList<Machine> collectData() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/machine.data"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                Machine temp = new Machine(
                        Integer.parseInt(split[2].trim()),
                        Integer.parseInt(split[3].trim()),
                        Integer.parseInt(split[4].trim()),
                        Integer.parseInt(split[5].trim()),
                        Integer.parseInt(split[6].trim()),
                        Integer.parseInt(split[7].trim()),
                        Integer.parseInt(split[8].trim()),
                        Integer.parseInt(split[9].trim())
                );

                // Normalization tracking
                if (temp.MYCT > MYCTMax) { MYCTMax = temp.MYCT; }
                if (temp.MMIN > MMINMax) { MMINMax = temp.MMIN; }
                if (temp.MMAX > MMAXMax) { MMAXMax = temp.MMAX; }
                if (temp.CACH > CACHMax) { CACHMax = temp.CACH; }
                if (temp.CHMIN > CHMINMax) { CHMINMax = temp.CHMIN; }
                if (temp.CHMAX > CHMAXMax) { CHMAXMax = temp.CHMAX; }
                if (temp.PRP > PRPMax) { PRPMax = temp.PRP; }
                if (temp.ERP > ERPMax) { ERPMax = temp.ERP; }

                hardwareStorage.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hardwareStorage;
    }

    public double[] getFeatures() {
        return new double[] {
                encodeMYCT(),
                encodeMMIN(),
                encodeMMAX(),
                encodeCACH(),
                encodeCHMIN(),
                encodeCHMAX(),
                encodePRP(),
                encodeERP()
        };
    }

    private double encodeMYCT() { return (double) MYCT / MYCTMax; }
    private double encodeMMIN() { return (double) MMIN / MMINMax; }
    private double encodeMMAX() { return (double) MMAX / MMAXMax; }
    private double encodeCACH() { return (double) CACH / CACHMax; }
    private double encodeCHMIN() { return (double) CHMIN / CHMINMax; }
    private double encodeCHMAX() { return (double) CHMAX / CHMAXMax; }
    private double encodePRP() { return (double) PRP / PRPMax; }
    private double encodeERP() { return (double) ERP / ERPMax; }

    public static void classify() {
        if (hardwareStorage.isEmpty()) {
            System.out.println("No data collected.");
            return;
        }

        float averagePRP = 0;
        int numUnits = 0;
        for (Machine entry : hardwareStorage) {
            averagePRP += entry.PRP;
            numUnits++;
        }

        averagePRP = averagePRP / numUnits;
        System.out.println("Average PRP: " + averagePRP + "\nNumber of Units: " + numUnits);
    }
}