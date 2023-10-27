/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices,edges) = 包含vertices中的点，包含edges中的带权有向边的图
    // Representation invariant:
    //   vertices是点的集合
    //   edges是带权有向边的列表，其中包含的点必在vertices中，且每条边的权值为正整数
    // Safety from rep exposure:
    //   所有属性都是private final
    //   vertices是set，是可变类型，但是只能通过add方法和构造函数添加点，获取vertices也通过vertices方法利用防御性复制给用户新的set
    //   edges是list可变类型，但是没有方法直接获取，可以通过sources和targets获取边的信息

    public ConcreteEdgesGraph() {
    }
    // TODO checkRep
    public void checkRep(){
        Iterator<L> vIterator = vertices.iterator();
        while (vIterator.hasNext()){
            L vertex = vIterator.next();
            assert !vertex.equals(""):"包含空字符串为标签的点";
        }
        Iterator<Edge<L>>iterator = edges.iterator();
        while (iterator.hasNext()){
            Edge<L> edge = iterator.next();
            assert vertices.contains(edge.getSource()):"起点包含不存在的点";
            assert vertices.contains(edge.getTarget()):"终点包含不存在的点";
            assert edge.getWeight() > 0:"边权不为正整数";
        }
    }
    @Override public boolean add(L vertex) {
        if(!vertices.contains(vertex)){
            vertices.add(vertex);
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }
    
    @Override public int set(L source, L target, int weight) {
        vertices.add(source);
        vertices.add(target);
        //update remove
        for(Edge<L> edge:edges){
            if(edge.getSource().equals(source) && edge.getTarget().equals(target)){
                int lastWeight = edge.getWeight();
                edges.remove(edge);
                if(weight != 0) edges.add(new Edge<L>(source,target,weight));
                return lastWeight;
            }
        }
        //add
        int lastWeight = 0;
        if(weight != 0) edges.add(new Edge<L>(source,target,weight));
        checkRep();
        return lastWeight;
    }
    
    @Override public boolean remove(L vertex) {
        boolean hasVertex = vertices.remove(vertex);
        if(hasVertex){
            for(Edge<L> edge:edges){
                if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) edges.remove(edge);
            }
        }
        checkRep();
        return hasVertex;
    }
    
    @Override public Set<L> vertices() {
        Set<L> vers = new HashSet<>();
        vers.addAll(vertices);
        checkRep();
        return vers;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer>sources = new HashMap<>();
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()){
            Edge<L> edge = iterator.next();
            if(edge.getTarget().equals(target)){
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        checkRep();
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L,Integer>targets = new HashMap<>();
        Iterator<Edge<L>> iterator = edges.iterator();
        while(iterator.hasNext()){
            Edge<L> edge = iterator.next();
            if(edge.getSource().equals(source)){
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        checkRep();
        return targets;
    }

    @Override public String toString() {
        String res = "";
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()){
            Edge<L> edge = iterator.next();
            res += edge.toString();
            res += ",";
        }
        return res;
    }
}

/**
 * 表示一个带权有向边，边的两点分别为String为标签的点，并且边权为int类型，权值为正整数.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 *
 */
class Edge<L> {
    private final L source;
    private final L target;
    private final int weight;
    // Abstraction function:
    //   AF(source,target,weight) = 一条从source到target的权值为weight的有向边
    // Representation invariant:
    //   source，target都应为非空字符串
    //   weight为非负整数
    // Safety from rep exposure:
    //   source,target为String类型，weight为int类型，都为不可变类型
    //   所有属性都为private final

    /**
     * 创建一个source为起点，target为终点，weight为权值的边
     * @param source 起点
     * @param target 终点
     * @param weight 权值，必须为正整数
     */
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    /**
     * 检验是否符合rep
     */
    public void checkRep(){
        assert !target.equals("") :"边的终点为空";
        assert !source.equals("") :"边的起始点为空";
        assert weight > 0 :"权值为非正整数";
    }
    // TODO methods

    /**
     * 获取起点
     * @return 起点
     */
    public L getSource() {
        return source;
    }

    /**
     * 获取终点
     * @return 终点
     */
    public L getTarget() {
        return target;
    }

    /**
     * 获取权值
     * @return 权值，正整数
     */
    public int getWeight() {
        return weight;
    }
    // TODO toString()

    /**
     * 将ADT转换为可读的字符串
     * @return source-weight-target格式的字符串
     */
    @Override
    public String toString() {
        String res = source + "-" + weight + "-" + target;
        return res;
    }
}
