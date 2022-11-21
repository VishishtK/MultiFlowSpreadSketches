import java.util.Random;
import java.util.stream.IntStream;

public class HashFunctions {

    int[] hashFunctionInts;
    int hashSize;
    int bound;

    public HashFunctions(int k,int bound){
        this.hashFunctionInts = IntStream.generate(() -> new Random().nextInt(Integer.MAX_VALUE)).limit(k).toArray();
        this.hashSize = k;
        this.bound = bound;
    }

    public int FNVHash(final String k) {
        int FNV_32_INIT = 0x811c9dc5;
        int FNV_32_PRIME = 0x01000193;

        int rv = FNV_32_INIT;
        final int len = k.length();
        for(int i = 0; i < len; i++) {
            rv ^= k.charAt(i);
            rv *= FNV_32_PRIME;
        }
        return Math.abs(rv%this.bound);
    }

    public int Hash(int k){
        return FNVHash(String.valueOf(k ^ hashFunctionInts[0]));
    }

    public int Hash(int k, int functionNumber){
        return FNVHash(String.valueOf(k ^ hashFunctionInts[functionNumber]));
    }

    public int Hash(String k, int functionNumber){
        return Hash(k.hashCode(),functionNumber);
    }

    public int[] HashWithMSB(String k, int functionNumber){
        return new int[]{k.hashCode()&0b10000000000000000000000000000000,Hash(k.hashCode(),functionNumber)};
    }

    

}
