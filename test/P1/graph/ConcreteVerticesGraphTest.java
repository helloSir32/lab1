/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    // 划分等价类：
    //空图和非空图
    //toString格式：source-weight-target,...

    // I have done toString
    @Test
    public void testToString(){
        Graph<String>testGraph = emptyInstance();
        String ans = "";
        assertEquals(ans,testGraph.toString());
        testGraph.set("hello","bro",1);
        testGraph.set("bye","bro",2);
        ans = "hello-1-bro,bye-2-bro,";
        assertEquals(ans,testGraph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //getWeight方法:
    //target:空字符串、被包含在target的字符串和未被包含在target的字符串
    //getTag、getTargets方法：使用正确定义的点
    //hasTarget划分:
    //tag:空字符串、被包含在target的字符串和未被包含在target的字符串
    //remove划分:
    //target:空字符串、被包含在target的字符串和未被包含在target的字符串
    //update划分：
    //target:空字符串、被包含在target的字符串和未被包含在target的字符串
    //weight:正整数
    //toString：
    //格式：“source-weight-target,...”

    // I have done toString
    @Test
    public void testGetWeight(){
        Vertex v = new Vertex("hello");
        Integer ans = 0;
        assertEquals(ans,v.getWeight(""));
        assertEquals(ans,v.getWeight("bro"));
        v.update("bro",2);
        ans = 2;
        assertEquals(ans,v.getWeight("bro"));
    }
    @Test
    public void testGetTag(){
        Vertex v = new Vertex("hello");
        assertEquals("hello",v.getTag());
    }
    @Test
    public void testGetTargets(){
        Vertex v = new Vertex("hello");
        Map<String,Integer>ans = new HashMap<>();
        assertEquals(ans,v.getTargets());
        v.update("bro",2);
        v.update("sis",3);
        ans.put("bro",2);
        ans.put("sis",3);
        assertEquals(ans,v.getTargets());
    }
    @Test
    public void testHasTarget(){
        Vertex v = new Vertex("hello");
        assertFalse(v.hasTarget(""));
        assertFalse(v.hasTarget("bro"));
        v.update("sis",3);
        assertTrue(v.hasTarget("sis"));
    }
    @Test
    public void testRemove(){
        Vertex v = new Vertex("hello");
        assertEquals(0,v.remove(""));
        assertEquals(0,v.remove("bro"));
        v.update("bro",2);
        assertEquals(2,v.remove("bro"));
        Map<String,Integer> ans = new HashMap<>();
        assertEquals(ans,v.getTargets());
    }
    @Test
    public void testUpdate(){
        Vertex v = new Vertex("hello");
        assertEquals(0,v.update("bro",2));
        assertEquals(0,v.update("",3));
        assertEquals(2,v.update("bro",4));
        Map<String,Integer>ans = new HashMap<>();
        ans.put("bro",4);
        assertEquals(ans,v.getTargets());
    }
    @Test
    public void testVertexToString(){
        Vertex v = new Vertex("hello");
        assertEquals("",v.toString());
        v.update("bro",2);
        assertEquals("hello-2-bro,",v.toString());
    }
    // I have done toString
}
