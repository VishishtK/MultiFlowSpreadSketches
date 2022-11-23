import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BsktHLL {

    int numberOfHLL;
    int numberOfBitsPerHLLRegister;
    int k;
    int numberOfRegisterPerHLL;

    HyperLogLog[] bskt;

    HashFunctions hashFunctions;

    public BsktHLL(int numberOfHLL, int numberOfRegisterPerHLL, int numberOfBitsPerHLLRegister, int k) {
        this.numberOfHLL = numberOfHLL;
        this.numberOfBitsPerHLLRegister = numberOfBitsPerHLLRegister;
        this.k = k;
        this.hashFunctions = new HashFunctions(k, numberOfHLL);
        this.numberOfRegisterPerHLL = numberOfRegisterPerHLL;
        bskt = new HyperLogLog[numberOfHLL];
        for (int i = 0; i < numberOfHLL; i++) {
            bskt[i] = new HyperLogLog(numberOfRegisterPerHLL, numberOfBitsPerHLLRegister);
        }
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
        for (int i = 0; i < k; i++) {
            bskt[hashFunctions.Hash(flowId, i)].RecordPackets(packets);
        }
    }

    public void Query(Flow[] flows) {
        for (int i = 0; i < flows.length; i++) {
            Query(flows[i]);
        }
    }

    private void Query(Flow flow) {
        double estimate = Integer.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            estimate = Math.min(estimate, bskt[hashFunctions.Hash(flow.flowId, i)].Query());
        }
        flow.estimatedNumberOfPackets = estimate;
    }

    public void Output(Flow[] flows) {
        Arrays.sort(flows, (a, b) -> (int) b.estimatedNumberOfPackets - (int) a.estimatedNumberOfPackets);
        String output = "";
        for (int i = 0; i < 25; i++) {
            output += "FlowID " + flows[i].flowId + ", TrueSpread " + flows[i].numberOfPackets + ", EstimatedSpread "
                    + flows[i].estimatedNumberOfPackets + "\n";
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("bsktHLL.txt"));
            writer.write(output);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
