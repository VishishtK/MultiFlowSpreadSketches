import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.BitSet;

public class BitMap {
    BitSet bitMap;
    int size;
    HashFunctions hashFunction;

    public BitMap(int size) {
        bitMap = new BitSet(size);
        this.size = size;
        this.hashFunction = new HashFunctions(1, size);
    }

    public void RecordPackets(int[] packets) {
        for (int packet : packets) {
            RecordPacket(packet);
        }
    }

    public void RecordPacket(int packet) {
        bitMap.set(hashFunction.Hash(packet));
    }

    public double Query() {
        return (-1) * size * Math.log(1.0 - ((double) bitMap.cardinality() / (double) size));
    }

    public void Clear() {
        bitMap.clear();
    }

    public void Output(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("BitMap.txt"));
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
