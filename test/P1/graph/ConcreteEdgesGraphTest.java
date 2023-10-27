/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // 划分等价类:
    // 空图和非空图
    // toString格式"source-weight-target,..."
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToString(){
        Graph<String>testGraph = emptyInstance();
        String ans = "";
        assertEquals(ans,testGraph.toString());
        testGraph.set("hello","bro",1);
        testGraph.set("bye","bro",2);
        ans += "hello-1-bro,bye-2-bro,";
        assertEquals(ans,testGraph.toString());
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //getSource方法测试划分:
    //正确定义的edge
    //getTarget方法测试划分:
    //正确定义的edge
    //getWeight方法划分:
    //正确定义的edge
    //toString方法划分:
    //格式"source-weight-target"
    
    // TODO tests for operations of Edge
    @Test
    public void testGetSource(){
        Edge e = new Edge("hello","bro",2);
        assertEquals("hello",e.getSource());
    }
    @Test
    public void testGetTarget(){
        Edge e = new Edge("hello","bro",2);
        assertEquals("bro",e.getTarget());
    }
    @Test
    public void testGetWeight(){
        Edge e = new Edge("hello","bro",2);
        assertEquals(2,e.getWeight());
    }
    @Test
    public void testEdgeToString(){
        Edge edge = new Edge("hello","bro",2);
        assertEquals("hello-2-bro",edge.toString());
    }
}
