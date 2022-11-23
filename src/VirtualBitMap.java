import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

public class VirtualBitMap {
    int virtualBits;
    int physicalBits;
    BitSet bitMap;
    HashFunctions hashFunction;
    HashFunctions virtHashFunctions;

    public VirtualBitMap(int physicalBits, int virtualBits) {
        bitMap = new BitSet(physicalBits);
        this.virtualBits = virtualBits;
        this.physicalBits = physicalBits;
        this.hashFunction = new HashFunctions(virtualBits, physicalBits);
        this.virtHashFunctions = new HashFunctions(1, virtualBits);
    }

    public void RecordFlows(Flow[] flows) {
        for (Flow flow : flows) {
            RecordFlow(flow);
        }
    }

    private void RecordFlow(Flow flow) {
        RecordPackets(flow.flowId, flow.packets);
    }

    public void RecordPackets(String flowId, int[] packets) {
        for (int packet : packets) {
            RecordPacket(flowId, packet);
        }
    }

    public void RecordPacket(String flowId, int packet) {
        bitMap.set(hashFunction.Hash(flowId, virtHashFunctions.Hash(packet)));
    }

    public void Query(Flow[] flows) {
        for (int i = 0; i < flows.length; i++) {
            Query(flows[i]);
        }
    }

    private void Query(Flow flow) {
        double Vb = 1 - ((double) bitMap.cardinality() / (double) physicalBits);

        int zeroBitsVBf = 0;
        for (int i = 0; i < virtualBits; i++) {
            if (!bitMap.get(hashFunction.Hash(flow.flowId, i)))
                zeroBitsVBf++;
        }

        double Vf = (double) zeroBitsVBf / (double) virtualBits;

        flow.estimatedNumberOfPackets = virtualBits * Math.log(Vb) - virtualBits * Math.log(Vf);
    }

    public void Output(Flow[] flows) {
        String output = "TrueSpread,EstimatedSpread\n";
        for (int i = 0; i < flows.length; i++) {
            if (flows[i].numberOfPackets > 500)
                continue;
            output += flows[i].numberOfPackets + "," + flows[i].estimatedNumberOfPackets + "\n";
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("virtualBitMap.csv"));
            writer.write(output);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            Runtime.getRuntime().exec("python3.9 plotGraph.py ");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
