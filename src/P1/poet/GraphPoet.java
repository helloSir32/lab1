/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import P1.graph.Graph;

import javax.imageio.event.IIOReadProgressListener;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   AF(graph) = 由graph实现的节点为String类型的graphPoet
    // Representation invariant:
    //   graph的节点为非空字符串类型，且graph中不存在重复节点，边权都为正整数
    // Safety from rep exposure:
    //   graph 为private final，且为不可变类型
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        FileReader fileReader = new FileReader(corpus);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String source = "";
        String oneLine = "";
        while ((oneLine = bufferedReader.readLine()) != null){
            source += oneLine;
        }
        bufferedReader.close();
        fileReader.close();
        Scanner scanner = new Scanner(source.toLowerCase());
        scanner.useDelimiter("\\s* \\s*");
        String lword = "";
        String rword = "";
        if(scanner.hasNext()){
            lword = scanner.next();
        }
        if(scanner.hasNext()){
            rword = scanner.next();
            graph.set(lword,rword,1);
        }
        while (scanner.hasNext()){
            lword = rword;
            rword = scanner.next();
            Map<String,Integer> targets = graph.targets(lword);
            boolean hasEdge = false;
            for(Map.Entry<String,Integer>tar:targets.entrySet()){
                if(tar.getKey().equals(rword)){
                    graph.set(lword,rword,tar.getValue()+1);
                    hasEdge = true;
                    break;
                }
            }
            if(!hasEdge) graph.set(lword,rword,1);
        }
        checkRep();
    }

    public void checkRep(){
        Iterator<String>iterator = graph.vertices().iterator();
        Set<String>vers = new HashSet<>();
        while (iterator.hasNext()){
            String vertex = iterator.next();
            assert !vertex.equals(""):"点的标签值为空";
            assert vers.add(vertex):"有重复的点";
            Iterator<Map.Entry<String,Integer>>tarIterator = graph.targets(vertex).entrySet().iterator();
            while (tarIterator.hasNext()){
                Map.Entry<String,Integer>target = tarIterator.next();
                assert target.getValue()>0:"边权小于等于0";
            }
        }
    }
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        String ans = "";
        Scanner scanner = new Scanner(input.toLowerCase());
        scanner.useDelimiter("\\s* \\s*");
        String lword = "",rword = "",center = "";
        if(scanner.hasNext()) rword = scanner.next();
        while (scanner.hasNext()){
            lword = rword;
            rword = scanner.next();
            center = searchCenter(lword,rword);
            ans += lword + " ";
            if(!center.equals("")){
                 ans += center + " ";
            }
        }
        ans += rword;
        return ans;
    }

    /**
     * 寻找两个点之间是否有中间点
     * @param lword 起始点，需要存在于图中且不为空
     * @param rword 终点，需要存在于图中且不为空
     * @return 中间点,如果没有就返回空
     */
    public String searchCenter(String lword,String rword){
        String center = "";
        Map<String, Integer>centers = graph.targets(lword);
        for(Map.Entry<String,Integer>cen:centers.entrySet()){
            if(hasTarget(cen.getKey(),rword)){
                center = cen.getKey();
                break;
            }
        }
        return center;
    }

    /**
     * 查询是否可以通向目标点
     * @param source 起始点，需要存在于图中且不为空
     * @param target 终点，需要存在于图中且不为空
     * @return 有边直接连接两个点返回true，否则返回false
     */
    public boolean hasTarget(String source,String target){
        Map<String,Integer>targets = graph.targets(source);
        for(Map.Entry<String,Integer>tar: targets.entrySet()){
            if(tar.getKey().equals(target)){
                return true;
            }
        }
        return false;
    }

    /**
     * 将图转换为字符串
     * @return 转换后的字符串
     */
    @Override
    public String toString(){
        return graph.toString();
    }
}
