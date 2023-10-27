package P2;

import P1.graph.Graph;

import java.util.*;

/**
 * 朋友关系图是有向图，点代表一个人，存在边代表a认识b
 * 不可变类型
 */
public class FriendshipGraph {
    private final Graph<Person> graph = Graph.empty();

    // Abstraction function:
    //   AF(graph) = 以graph为存储形式的人际关系图
    // Representation invariant:
    //   graph节点为Person，不能有重复的点和边
    // Safety from rep exposure:
    //   所有属性均为private final
    //   graph为Graph类型，不可变

    /**
     * 构建一个空图
     */
    public FriendshipGraph() {
    }

    /**
     * 检查rep
     */
    public void checkRep(){
        Iterator<Person>iterator = graph.vertices().iterator();
        Set<Person>vers = new HashSet<>();
        while (iterator.hasNext()){
            Person vertex = iterator.next();
            assert !vertex.getName().equals(""):"点的标签值为空";
            assert vers.add(vertex):"有重复的点";
            Iterator<Map.Entry<Person,Integer>>tarIterator = graph.targets(vertex).entrySet().iterator();
            Set<Person>tars = new HashSet<>();
            while (tarIterator.hasNext()){
                Map.Entry<Person,Integer>target = tarIterator.next();
                assert tars.add(target.getKey()):"有重复的点";
            }
        }
    }
    /**
     * 获取人际关系图
     * @return 有向图
     */
    public Graph<Person> getGraph() {
        return graph;
    }

    /**
     * 加点
     * @param person 作为点的人，名字不为空
     */
    public void addVertex(Person person){
        if(!person.getName().equals("")) graph.add(person);
        checkRep();
    }

    /**
     * 加边
     * @param source 起始点
     * @param target 终点
     */
    public void addEdge(Person source,Person target){
        if(!source.getName().equals("") && !target.getName().equals("")) graph.set(source,target,1);
        checkRep();
    }

    /**
     * 获取从source到target的距离
     * @param source 起点
     * @param target 终点
     * @return 两点距离（边数），如果是自己则为0，如果不通则为-1
     */
    public int getDistance(Person source,Person target){
        if(source.equals(target)) return 0;
        if(!graph.vertices().contains(source) || !graph.vertices().contains(target)) return -1;
        int distance = -1;
        List<Person>queue = new ArrayList<>();
        int p = 0;
        int end = p;
        int level = 0;
        queue.add(source);
        while (p < queue.size()){
            Map<Person,Integer>targets = graph.targets(queue.get(p));
            for(Map.Entry<Person,Integer>tar:targets.entrySet()){
                if (!queue.contains(tar.getKey())){
                    queue.add(tar.getKey());
                }
                if(tar.getKey().equals(target)) {
                    distance = level + 1;
                    break;
                }
            }
            if(p == end){
                end = queue.size() - 1;
                level++;
            }
            p++;
            if(distance > 0) break;
        }
        return distance;
    }

    public static void main(String[] args) {
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel,ross);
        graph.addEdge(ross,rachel);
        graph.addEdge(ross,ben);
        graph.addEdge(ben,ross);
        graph.addEdge(ben,kramer);
        System.out.println("rachel to ben:"+graph.getDistance(rachel,ben));
        System.out.println("rachel to rachel:"+graph.getDistance(rachel,rachel));
        System.out.println("ben to kramer:"+graph.getDistance(ben,kramer));
        System.out.println("kramer to ben:"+graph.getDistance(kramer,ben));
    }
}
