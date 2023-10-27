/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import P1.graph.Graph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;


/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //  GraphPoet方法测试分类:
    //corpus:错误文件、正确但错误格式文件、正确且格式正确文件
    //  poem方法分类
    //input:可联想的字符串，不可联想的字符串
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGraphPoetWithError() throws IOException {
        thrown.expect(IOException.class);
        File fp = new File("1.txt");
        GraphPoet testGraph = new GraphPoet(fp);
    }
    @Test
    public void testGraphPoet() throws IOException {
        File fp = new File("src/P1/poet/easy.txt");
        GraphPoet testGraph = new GraphPoet(fp);
        assertTrue(testGraph.hasTarget("hi","hello"));
        assertTrue(testGraph.hasTarget("hello","hi"));
        assertTrue(testGraph.hasTarget("hello","everyone."));
    }
    @Test
    public void testPoem() throws IOException {
        //File fp = new File("src/P1/poet/easy.txt");
        GraphPoet testGraph = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
        String input = "i am everyone.";
        String ans = testGraph.poem(input);
        assertEquals(input,ans);
        input = "i love theater system.";
        // input = "hi everyone.";
        ans = testGraph.poem(input);
        assertEquals("i love theater sound system.",ans);
    }
    @Test
    public void testToString() throws IOException {
        File fp = new File("src/P1/poet/easy.txt");
        GraphPoet testGraph = new GraphPoet(fp);
        assertEquals("hello-1-hi,hi-2-hello,hello-1-everyone.,",testGraph.toString());
    }
}
