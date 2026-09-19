import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Machine implements normalizedFeatures{

    static ArrayList<Machine> data = new ArrayList<>();

    public int MYCT; public static int MYCTMax = Integer.MIN_VALUE; public static int MYCTMin = Integer.MAX_VALUE;
    public int MMIN; public static int MMINMax = Integer.MIN_VALUE; public static int MMINMin = Integer.MAX_VALUE;
    public int MMAX; public static int MMAXMax = Integer.MIN_VALUE; public static int MMAXMin = Integer.MAX_VALUE;
    public int CACH; public static int CACHMax = Integer.MIN_VALUE; public static int CACHMin = Integer.MAX_VALUE;
    public int CHMIN; public static int CHMINMax = Integer.MIN_VALUE; public static int CHMINMin = Integer.MAX_VALUE;
    public int CHMAX; public static int CHMAXMax = Integer.MIN_VALUE; public static int CHMAXMin = Integer.MAX_VALUE;
    public int PRP;
    public int ERP; public static int ERPMax = Integer.MIN_VALUE; public static int ERPMin = Integer.MAX_VALUE;

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
                if (temp.CHMAX > CHMAXMax) { CHMAXMax = temp.CHMAX; } if (temp.CHMAX < CHMAXMin) { CHMAXMin = temp.CHMAX; }
                if (temp.ERP > ERPMax) { ERPMax = temp.ERP; } if (temp.ERP < ERPMin) { ERPMin = temp.ERP; }

                data.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
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
    private double encodeCHMAX() { return (double) (CHMAX - CHMAXMin) / (CHMAXMax - CHMAXMin); }
    private double encodeERP() { return (double) (ERP - ERPMin) / (ERPMax - ERPMin); }

    public static void classify() {
        if (data.isEmpty()) {
            System.out.println("No data collected.");
            return;
        }

        float averagePRP = 0;
        int numUnits = 0;
        for (Machine entry : data) {
            averagePRP += entry.PRP;
            numUnits++;
        }

        float sumSquaredErrors = 0;
        for (Machine entry : data) {
            float diff = entry.PRP - averagePRP;
            sumSquaredErrors += diff * diff;
        }

        averagePRP = averagePRP / numUnits;
        float mse = sumSquaredErrors / numUnits;

        System.out.println("Average PRP: " + averagePRP + "\nNumber of Units: " + numUnits);
        System.out.println("MSE: " + mse);
    }
}