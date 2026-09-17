import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Machine implements normalizedFeatures{

    static ArrayList<Machine> hardwareStorage = new ArrayList<>();

    public int MYCT; public static int MYCTMax; public static int MYCTMin;
    public int MMIN; public static int MMINMax; public static int MMINMin;
    public int MMAX; public static int MMAXMax; public static int MMAXMin;
    public int CACH; public static int CACHMax; public static int CACHMin;
    public int CHMIN; public static int CHMINMax; public static int CHMINMin;
    public int CHMAX; public static int CHMAXMax; public static int CHMAXMin;
    public int PRP;
    public int ERP; public static int ERPMax; public static int ERPMin;

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

                // Normalization for machine
                if (temp.MYCT > MYCTMax) { MYCTMax = temp.MYCT; } if (temp.MYCT < MYCTMin) { MYCTMin = temp.MYCT; }
                if (temp.MMIN > MMINMax) { MMINMax = temp.MMIN; } if (temp.MMIN < MMINMin) { MMINMin = temp.MMIN; }
                if (temp.MMAX > MMAXMax) { MMAXMax = temp.MMAX; } if (temp.MMAX < MMAXMin) { MMAXMin = temp.MMAX; }
                if (temp.CACH > CACHMax) { CACHMax = temp.CACH; } if (temp.CACH < CACHMin) { CACHMin = temp.CACH; }
                if (temp.CHMIN > CHMINMax) { CHMINMax = temp.CHMIN; } if (temp.CHMIN < CHMINMin) { CHMINMin = temp.CHMIN; }
                if (temp.CHMAX > CHMAXMax) { CHMAXMax = temp.CHMAX; } if (temp.CHMAX < CHMAXMin) { MYCTMin = temp.CHMAXMin; }
                if (temp.ERP > ERPMax) { ERPMax = temp.ERP; } if (temp.ERP < ERPMin) { ERPMin = temp.ERP; }

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
                encodeERP()
        };
    }

    @Override
    public double getLable() { return PRP; }

    private double encodeMYCT() { return (double) (MYCT - MYCTMin) / (MYCTMax - MYCTMin); }
    private double encodeMMIN() { return (double) (MMIN - MMINMin) / (MMINMax - MMINMin); }
    private double encodeMMAX() { return (double) (MMAX - MMAXMin) / (MMAXMax - MMAXMin); }
    private double encodeCACH() { return (double) (CACH - CACHMin) / (CACHMax - CACHMin); }
    private double encodeCHMIN() { return (double) (CHMIN - CHMINMin) / (CHMINMax - CHMINMin); }
    private double encodeCHMAX() { return (double) (CHMAX - CHMAXMax) / (CHMAXMax - CHMAXMin); }
    private double encodeERP() { return (double) (ERP - ERPMin) / (ERPMax - ERPMin); }

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