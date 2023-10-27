/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //
    //add方法划分：
    //vertices:加入不存在的节点，加入已经加入过的节点
    //set方法划分:
    //source，target:已经加入的点，未加入的点
    //weight:0，正数
    //remove方法划分:
    //vertex:已经存入graph的点，参与边的构成（含source和target）的点，未加入graph的点
    //vertices方法划分:
    //空图和非空图
    //source方法划分:
    //target:已经存入graph的点，作为target参与边的构成的点，未加入的点
    //target方法划分:
    //source：已经存入graph的点，作为source参与边的构成的点，未加入的点
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testAdd(){
        Graph<String>testGraph = emptyInstance();
        assertTrue(testGraph.add("hello"));
        assertFalse(testGraph.add("hello"));
    }

    @Test
    public void testSet() {
        Graph<String>testGraph = emptyInstance();
        assertEquals(0,testGraph.set("hello","bro",2));
        assertEquals(0,testGraph.set("hello","sis",1));
        assertEquals(0,testGraph.set("bye","bro",3));
        assertEquals(2,testGraph.set("hello","bro",4));
        assertEquals(1,testGraph.set("hello","sis",0));
    }

    @Test
    public void testRemove() {
        Graph<String>testGraph = emptyInstance();
        assertFalse(testGraph.remove("me"));
        testGraph.add("hello");
        assertTrue(testGraph.remove("hello"));
        assertFalse(testGraph.remove("bye"));
    }

    @Test
    public void testVertices() {
        Graph<String>testGraph = emptyInstance();
        Set<String>ver = new HashSet<>();
        assertEquals(ver,testGraph.vertices());
        testGraph.add("hello");
        testGraph.add("bye");
        ver.add("hello");
        ver.add("bye");
        assertEquals(ver,testGraph.vertices());
    }

    @Test
    public void testSources() {
        Graph<String>testGraph = emptyInstance();
        Map<String,Integer>sources = new HashMap<>();
        assertEquals(sources,testGraph.sources("hello"));
        testGraph.set("hello","bro",2);
        testGraph.set("bye","bro",3);
        sources.put("hello",2);
        sources.put("bye",3);
        assertEquals(sources,testGraph.sources("bro"));
    }

    @Test
    public void testTargets() {
        Graph<String>testGraph = emptyInstance();
        Map<String,Integer>targets = new HashMap<>();
        assertEquals(targets,testGraph.targets("hello"));
        testGraph.set("hello","bro",2);
        testGraph.set("hello","sis",3);
        targets.put("bro",2);
        targets.put("sis",3);
        assertEquals(targets,testGraph.targets("hello"));
    }
}
