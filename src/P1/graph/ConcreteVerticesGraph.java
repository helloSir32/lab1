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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices) = 包含vertices内的所有点的带权有向图。
    // Representation invariant:
    //   vertices内的点的tag都不为空，边的两边点都包含在vertices内，不包含重复的点。
    // Safety from rep exposure:
    //   vertices为list可变类型，所以采用防御式拷贝
    //   所有属性都为private

    public ConcreteVerticesGraph() {
    }

    public void checkRep(){
        Set<L>ver = new HashSet<>();
        Iterator<Vertex<L>>iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v = iterator.next();
            assert !v.getTag().equals("") :"点的标签不能为空";
            assert ver.add(v.getTag()) :"不能出现重复的点";
        }
        iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v2 = iterator.next();
            Iterator<Map.Entry<L,Integer>>entryIterator = v2.getTargets().entrySet().iterator();
            while (entryIterator.hasNext()){
                Map.Entry<L,Integer>next = entryIterator.next();
                assert ver.contains(next.getKey()) :"出现了不存在的点";
            }
        }
    }
    @Override public boolean add(L vertex) {
        Vertex<L> v = new Vertex<L>(vertex);
        Iterator<Vertex<L>> iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> next = iterator.next();
            if(next.getTag().equals(vertex)) return false;
        }
        vertices.add(v);
        checkRep();
        return true;
    }
    
    @Override public int set(L source, L target, int weight) {
        add(source);
        add(target);
        Iterator<Vertex<L>> iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v = iterator.next();
            if(v.getTag().equals(source)){
                //删除边
                if(weight == 0){
                    return v.remove(target);
                }
                //更新边
                else {
                    return v.update(target,weight);
                }
            }
        }
        checkRep();
        return 0;
    }
    
    @Override public boolean remove(L vertex) {
        boolean hasVertex = false;
        Iterator<Vertex<L>> iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v =iterator.next();
            v.remove(vertex);
            if(v.getTag().equals(vertex)){
                vertices.remove(v);
                hasVertex = true;
            }
        }
        checkRep();
        return hasVertex;
    }
    
    @Override public Set<L> vertices() {
        Set<L> ver = new HashSet<>();
        Iterator<Vertex<L>>iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v = iterator.next();
            ver.add(v.getTag());
        }
        checkRep();
        return ver;
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer>sources = new HashMap<>();
        Iterator<Vertex<L>>iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v = iterator.next();
            if(v.hasTarget(target)){
                sources.put(v.getTag(),v.getWeight(target));
            }
        }
        checkRep();
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Iterator<Vertex<L>>iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v = iterator.next();
            if(v.getTag().equals(source)){
                return v.getTargets();
            }
        }
        checkRep();
        return new HashMap<L,Integer>();
    }

    @Override
    public String toString(){
        String res = "";
        Iterator<Vertex<L>> iterator = vertices.iterator();
        while (iterator.hasNext()){
            Vertex<L> v = iterator.next();
            res += v.toString();
        }
        return res;
    }
}

/**
 * 表示图中的一个点，包含它的标签以及用键值对储存的以该点为起点的带权有向边的终点和权值的集合
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    private L tag;
    private Map<L,Integer> targets;
    // Abstraction function:
    //   AF(tag,targets) = 一个标签为tag的点，从该点出发的带权有向边的集合用map表示，每一条边的起点是该点，终点是键值对的key，权值是键值对的value
    // Representation invariant:
    //   tag为非空字符串
    //   targets键值对中字符串非空，value值为非负整数
    // Safety from rep exposure:
    //   所有属性均为private
    //   tag为String类型，为不可变类型
    //   targets获取时通过getTargets方法，采用防御性copy返回新建的map

    /**
     * 创建一个以tag为标签的点，不包含边
     * @param tag 代表点的标签
     */
    public Vertex(L tag) {
        this.tag = tag;
        targets = new HashMap<L,Integer>();
    }

    /**
     * 创建一个tag为标签，targets为边集的点
     * @param tag 标签
     * @param targets 终点和权值的键值对的集合
     */
    public Vertex(L tag, Map<L, Integer> targets) {
        this.tag = tag;
        this.targets = targets;
    }

    /**
     * 检查是否符合rep
     */
    public void checkRep(){
        assert !tag.equals("") :"点的标签值不能为空";
        for (Map.Entry<L, Integer> target : targets.entrySet()) {
            L targetTag = target.getKey();
            Integer targetVal = target.getValue();
            assert !targetTag.equals("") : "边的目标点不能为空";
            assert targetVal > 0 : "边的权值不能为非正整数";
        }
    }

    /**
     * 获取该点到目标点的权值，没有则为0
     * @param target 目标点标签
     * @return 权值，如果目标点权值为空或者不存在为0
     */
    public Integer getWeight(L target){
        if(tag.equals("")) return 0;
        for (Map.Entry<L, Integer> next : targets.entrySet()) {
            if (next.getKey().equals(target)) return next.getValue();
        }
        return 0;
    }

    /**
     * 获取标签
     * @return 标签
     */
    public L getTag() {
        return tag;
    }

    /**
     * 获取所有目标点和权值
     * @return 包含所有目标点和权值的map
     */
    public Map<L, Integer> getTargets() {
        Map<L,Integer> tar = new HashMap<>();
        tar.putAll(targets);
        return tar;
    }

    /**
     * 判断是否存在到目标点的边
     * @param tag 目标点的标签
     * @return 包含则返回true，否则返回false
     */
    public boolean hasTarget(L tag){
        if(tag.equals("")) return false;
        Iterator<Map.Entry<L,Integer>> iterator = targets.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<L,Integer> next = iterator.next();
            if(next.getKey().equals(tag)) return true;
        }
        return false;
    }

    /**
     * 除去到目标点的边，不存在则不除
     * @param target 将要除去的目标点
     * @return 除去的边的权值
     */
    public int remove(L target){
        if(hasTarget(target)){
            int weight = targets.get(target);
            targets.remove(target);
            checkRep();
            return weight;
        }
        else return 0;
    }

    /**
     * 更新到目标点的边的权值
     * @param target 更新的目标点，不存在则添加
     * @param weight 新的权值
     * @return 原先的权值
     */
    public int update(L target,int weight){
        int lastWeight = 0;
        if(target.equals("")) return 0;
        if(hasTarget(target)){
            lastWeight = targets.get(target);
            targets.replace(target,weight);
        }
        else{
            targets.put(target,weight);
        }
        checkRep();
        return lastWeight;
    }

    /**
     * 获取转换的字符串
     * @return 转换后的字符串,source-weight-target
     */
    @Override
    public String toString(){
        String res = "";
        Iterator<Map.Entry<L,Integer>>iterator = targets.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<L,Integer>target = iterator.next();
            res += tag + "-" + target.getValue() + "-" + target.getKey();
            res += ",";
        }
        return res;
    }
    //test
}
