package P2;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.util.*;

import static org.junit.Assert.*;

/** 
* FriendshipGraph Tester.
*/ 
public class FriendshipGraphTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

//test strategy:
//addVertex划分：
//person:空字符串、合法字符串，重复加入同一字符串
//addEdge划分:
//source,target:空字符串、合法字符串，重复加入同一边
//getDistance:
//source,target:空字符串、合法字符串，在图中的点、不在图中的点，可达、不可达，自己到自己

@Test
public void testAddVertex() throws Exception {
    FriendshipGraph graph = new FriendshipGraph();
    graph.addVertex(new Person(""));
    Set<Person> expect = new HashSet<>();
    assertEquals(expect,graph.getGraph().vertices());

    Person rachel = new Person("Rachel");
    Person ross = new Person("Ross");
    graph.addVertex(rachel);
    graph.addVertex(ross);
    expect.add(rachel);
    expect.add(ross);
    assertEquals(expect,graph.getGraph().vertices());

    //重复加入同一人测试
    graph.addVertex(new Person("Rachel"));
    assertEquals(expect,graph.getGraph().vertices());
}

/**
 *
 * Method: addEdge(Person source, Person target)
 *
 */

@Test
public void testAddEdge() throws Exception {
    FriendshipGraph graph = new FriendshipGraph();
    Map<Person,Integer>targets = new HashMap<>();
    Person rachel = new Person("Rachel");
    Person ross = new Person("Ross");
    Person ben = new Person("Ben");
    Person kramer = new Person("Kramer");
    graph.addVertex(rachel);
    graph.addVertex(ross);
    graph.addVertex(ben);
    graph.addVertex(kramer);

    graph.addEdge(rachel,new Person(""));
    assertEquals(targets,graph.getGraph().targets(rachel));

    graph.addEdge(rachel,ross);
    graph.addEdge(rachel,ben);
    targets.put(ben,1);
    targets.put(ross,1);
    assertEquals(targets,graph.getGraph().targets(rachel));

    graph.addEdge(rachel,rachel);
    targets.put(rachel,1);
    assertEquals(targets,graph.getGraph().targets(rachel));

    graph.addEdge(rachel,ross);
    assertEquals(targets,graph.getGraph().targets(rachel));
}

/**
 *
 * Method: getDistance(Person origin, Person target)
 *
 */

@Test
public void testGetDistance() throws Exception {
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

    assertEquals(-1,graph.getDistance(rachel,new Person("")));

    //正常情况测试，包含自己到自己，自己到可达的他人，自己到不可达的他人
    assertEquals(0,graph.getDistance(rachel,rachel));
    assertEquals(2,graph.getDistance(rachel,ben));
    assertEquals(-1,graph.getDistance(kramer,ben));
    //测试单向边是否反向可达
    graph.addEdge(ben,kramer);
    assertEquals(-1,graph.getDistance(kramer,ben));
}

}
