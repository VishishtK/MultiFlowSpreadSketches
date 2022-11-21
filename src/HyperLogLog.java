import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.BitSet;

public class HyperLogLog {
    BitSet[] registers;
    int noOfRegisters;
    int bitsPerRegister;
    HashFunctions hashFunction;
    HashFunctions geometricHashFunction;

    public HyperLogLog(int noOfRegisters, int bitsPerRegister) {
        this.noOfRegisters = noOfRegisters;
        this.bitsPerRegister = bitsPerRegister;
        this.hashFunction = new HashFunctions(1, noOfRegisters);
        this.geometricHashFunction = new HashFunctions(1, Integer.MAX_VALUE);
        registers = new BitSet[noOfRegisters];
        for (int i = 0; i < noOfRegisters; i++) {
            registers[i] = new BitSet(bitsPerRegister);
        }
    }

    public void RecordPackets(int[] packets) {
        for (int packet : packets) {
            RecordPacket(packet);
        }
    }

    public void RecordPacket(int packet) {
        int He = hashFunction.Hash(packet);
        int Ge = Integer.numberOfLeadingZeros(geometricHashFunction.Hash(packet));

        int r = registers[He].cardinality() > 0 ? (int) registers[He].toLongArray()[0] : 0;
        r = Math.max(r, Ge);

        registers[He] = BitSet.valueOf(new long[] { r });
    }

    public double Query() {
        double alpha = 0.7213/(1 + (1.079 / noOfRegisters));
        double[] data = new double[noOfRegisters];

        for (int i = 0; i < noOfRegisters; i++) {
            int estimate = registers[i].cardinality() > 0 ? (int) registers[i].toLongArray()[0] : 0;
            data[i] = Math.pow(2, estimate);
        }

        return alpha * noOfRegisters * harmonicMean(data);
    }

    public void Clear() {
        for (BitSet bitSet : registers) {
            bitSet.clear();
        }
    }

    private double harmonicMean(double[] data) {
        double sum = 0.0;

        for (int i = 0; i < data.length; i++) {
            sum += 1.0 / data[i];
        }
        return data.length / sum;
    }

    public void Output(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("HyperLogLog.txt"));
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void PrintRegisters(){
        for(int i=0;i<noOfRegisters;i++){
            System.out.print((registers[i].cardinality() > 0 ? (int) registers[i].toLongArray()[0] : 0) + " ,");
        }
    }
}
