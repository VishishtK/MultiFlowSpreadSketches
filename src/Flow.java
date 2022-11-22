import java.util.Random;
import java.util.stream.IntStream;

public class Flow {
    String flowId;
    int numberOfPackets;
    double estimatedNumberOfPackets;
    int[] packets;

    public Flow(String flowID, int numberOfPackets) {
        this.flowId = flowID;
        this.numberOfPackets = numberOfPackets;
        this.packets = IntStream.generate(() -> new Random().nextInt()).limit(numberOfPackets)
                .map(flowId -> Math.abs(flowId)).toArray();
    }
}