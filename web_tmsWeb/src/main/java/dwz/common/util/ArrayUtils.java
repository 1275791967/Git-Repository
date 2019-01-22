package dwz.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <strong>ArrayUtils</strong><br>
 * <br> 
 * <strong>Create on : 2011-12-27<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */
public class ArrayUtils {
    
    private ArrayUtils(){}
    
    /**
     * 将一个array转为根据keys转为map
     * @param array
     * @param keys
     * @return
     */
    public static Map<String, Object> toMap(Object[] array,String...keys) {
        if(array == null) return new HashMap<String, Object>();
        
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        for(int i = 0; i < keys.length; i++) {
            if(array.length == i ) {
                break;
            }
            m.put(keys[i], array[i]);
        }
        return m;
    }
    
    /**
     * 将两个数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static <T> T[] concat(T[] first, T[] second) {  
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个字节数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static byte[] concat(byte[] first, byte[] second) {  
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个short数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static short[] concat(short[] first, short[] second) {  
        short[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个int数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static int[] concat(int[] first, int[] second) {  
        int[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个long数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static long[] concat(long[] first, long[] second) {  
        long[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个float数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static float[] concat(float[] first, float[] second) {  
        float[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
    /**
     * 将两个double数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static double[] concat(double[] first, double[] second) {  
        double[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个char数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static char[] concat(char[] first, char[] second) {  
        char[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 将两个double数组合并成一个
     * @param first
     * @param second
     * @return
     */
    public static boolean[] concat(boolean[] first, boolean[] second) {  
        boolean[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    /**
     * 多个byte数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static byte[] concatAll(byte[] first, byte[]... rest) {  
        int totalLength = first.length;  
        for (byte[] array : rest) {  
            totalLength += array.length;  
        }  
        byte[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (byte[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个byte数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static short[] concatAll(short[] first, short[]... rest) {  
        int totalLength = first.length;  
        for (short[] array : rest) {  
            totalLength += array.length;  
        } 
        
        short[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (short[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个int数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static int[] concatAll(int[] first, int[]... rest) {  
        int totalLength = first.length;  
        for (int[] array : rest) {  
            totalLength += array.length;  
        } 
        
        int[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (int[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个int数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static long[] concatAll(long[] first, long[]... rest) {  
        int totalLength = first.length;  
        for (long[] array : rest) {  
            totalLength += array.length;  
        } 
        
        long[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (long[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个float数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static float[] concatAll(float[] first, float[]... rest) {  
        int totalLength = first.length;  
        for (float[] array : rest) {  
            totalLength += array.length;  
        } 
        
        float[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (float[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个double数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static double[] concatAll(double[] first, double[]... rest) {  
        int totalLength = first.length;  
        for (double[] array : rest) {  
            totalLength += array.length;  
        } 
        
        double[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (double[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个char数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static char[] concatAll(char[] first, char[]... rest) {  
        int totalLength = first.length;  
        for (char[] array : rest) {  
            totalLength += array.length;  
        } 
        
        char[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (char[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个boolean数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    public static boolean[] concatAll(boolean[] first, boolean[]... rest) {  
        int totalLength = first.length;  
        for (boolean[] array : rest) {  
            totalLength += array.length;  
        } 
        
        boolean[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (boolean[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }
    /**
     * 多个数组合并成一个
     * 
     * @param first
     *     第一个需要合并的数组
     * @param rest 
     *     其他需要合并的数组
     * @return
     *     合并后的数组
     */
    @SafeVarargs
    public static <T> T[] concatAll(T[] first, T[]... rest) {  
        int totalLength = first.length;  
        for (T[] array : rest) {  
            totalLength += array.length;  
        }  
        T[] result = Arrays.copyOf(first, totalLength);  
        int offset = first.length;  
        for (T[] array : rest) {  
            System.arraycopy(array, 0, result, offset, array.length);  
            offset += array.length;  
        }  
        return result;  
    }  
}
