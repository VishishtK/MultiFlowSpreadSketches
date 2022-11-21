public class Flow {
    String flowId;
    int numberOfPackets;
    int estimatedNumberOfPackets;

    public Flow(String flowID, int numberOfPackets){
        this.flowId = flowID;
        this.numberOfPackets = numberOfPackets;
    }
}