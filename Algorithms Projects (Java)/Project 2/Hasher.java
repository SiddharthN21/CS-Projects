import com.google.common.hash.Hashing;

// Hasher class -- implements different hashing algorithms
public class Hasher {
    public static int hash1(String str, int size) {
        return Integer.remainderUnsigned(Hashing.crc32().hashBytes(str.getBytes()).asInt(), size);
    }
    public static int hash2(String str, int size) {
        return Integer.remainderUnsigned(Hashing.adler32().hashBytes(str.getBytes()).asInt(), size);
    }
}
