import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {

    static Flow[] readFile() {
        Flow[] flows = null;
        try {
            Scanner myReader = new Scanner(new File("project5input.txt"));
            int NumberOfFlows = Integer.parseInt(myReader.nextLine());
            flows = new Flow[NumberOfFlows];
            int i = 0;
            System.out.println(NumberOfFlows);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("\t+");
                // System.out.println(data[0]);
                Flow flow = new Flow(data[0], Integer.parseInt(data[1]));
                flows[i] = flow;
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return flows;
    }

    public static void main(String[] args) throws Exception {
        Flow[] flows;
        flows = readFile();

        int m = 500000;
        int l = 500;

        VirtualBitMap virtualBitMap = new VirtualBitMap(m, l);
        virtualBitMap.RecordFlows(flows);
        virtualBitMap.Query(flows);
        virtualBitMap.Output(flows);

        m = 4000;
        int k = 3;
        l = 128;
        int bits = 5;

        BsktHLL bsktHLL = new BsktHLL(m, l, bits, k);
        bsktHLL.RecordFlows(flows);
    }
}
