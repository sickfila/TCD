//@version 1.3 19/10/18 
import java.util.Iterator;
import java.util.LinkedList;


public class DirectedAcyclicGraph
{
  private int totalVertices;
  private int totalEdges;
  LinkedList<Integer> successors[];
  private int[] indegree;
  
  @SuppressWarnings("unchecked")
  public DirectedAcyclicGraph(int totalVertices)
  {
    this.totalVertices = totalVertices;
    this.totalEdges = 0;
    successors = new LinkedList[totalVertices];
    indegree = new int[totalVertices];
    
    for (int i = 0; i < totalVertices; ++i)
    {
      successors[i] = new LinkedList<Integer>();
    }

  }
  
  public int getNoOFVertices()
  {
    return this.totalVertices;
  }
  public int getNoOfEdges()
  {
    return this.totalEdges;
  }
  private void setEdges(int a)
  {
    this.totalEdges=a;
  }
  private boolean validateVertex(int vertex)
  {
    if (vertex < 0 || vertex >= this.totalVertices)
      return false;
    return true;
  }
  
// from a -> z  
  public void addEdge(int a, int z) 
  {
    if(validateVertex(a) && validateVertex(z))
    {
      successors[a].add(z);
      indegree[z]++;
      setEdges(getNoOfEdges()+1);
    }
    else
    {
      throw new IllegalArgumentException("Error with validating vertex");
    }
  }

  public String toString()
  {
    StringBuilder stringB = new StringBuilder();
    stringB.append(totalVertices + " vertices, " + totalEdges + " edges \n");
    for (int v = 0; v < totalVertices; v++)
    {
      stringB.append(String.format("%d: ", v));
      for (int w : successors[v])
      {
        stringB.append(String.format("%d ", w));
      }
      stringB.append("\n");
    }
    return stringB.toString();
  }
  
  public int lowestCommonAncestor(int x, int y)
  {
    if (!validateVertex(x) || !validateVertex(y)) 
      return -1;
    int ret =-1;
    if(x==y)
      return x;
    for(int i=0; i<indegree.length; i++ )
    {
      if(indegree[i]==0)
        ret = i;
    }    
    if (ret == x || ret == y)
      return ret;
    LinkedList<Integer> df = DFS();
    LinkedList<Integer> new1 = new LinkedList<Integer>();
    boolean checkX = false;
    boolean checkY = false;
    int getNext;
    for(int i = 0; i< df.size() && (checkX==false||checkY==false) ; i++)
    {
      
      getNext= df.get(i);
      System.out.println(getNext);
      if(getNext==x)
      {
        checkX=true;
      }
      else if (getNext==y)
      {
        checkY=true;        
      }
      new1.add(df.get(i));
    }
    
    boolean end = false;
    boolean xFlag = false;
    boolean yFlag = false;

    for(int i=0; i<new1.size() && !end ; i++)
    {
      getNext = new1.get(i);
      for(int i2=0; i2<successors[getNext].size(); i2++)
      {
        if (successors[getNext].get(i2)==x) 
          xFlag=true;
        
        if (successors[getNext].get(i2)==y) 
          yFlag=true;
        
        if(getNext==x && yFlag==true)
        {
          end = true;
          return x;
        }
        else if(getNext==y && xFlag==true)
        {
          end = true;
          return y;
        }
        else if(yFlag==true&&xFlag==true&&!end)
        {
          end = true;
          return getNext;
        }
        else
        {
          yFlag=false;
          xFlag=false;
        }
      }
    }
    
    int px=0;
    int py=0;
    for(int i=0; i<new1.size(); i++)
    {
      getNext = new1.get(i);
      for(int i2=0; i2<successors[getNext].size(); i2++)
      {
        if (successors[getNext].get(i2)==x) 
        {
          px=getNext;
        }
        if (successors[getNext].get(i2)==y) 
        {
          py=getNext;
        }
        
      }
    }
    System.out.println(px+" "+py);
    return lowestCommonAncestor(px,py);
    //return ret;
  }
 
  
  // The function to do DFS traversal
  public LinkedList<Integer> DFS() 
  { 
    LinkedList<Integer> ret= new LinkedList<Integer>();
    int root =-1;
    for(int i=0; i<indegree.length; i++ )
    {
      if(indegree[i]==0)
      {
        root = i;
      }
    }
    // Mark all the vertices as not visited
    boolean visited[] = new boolean[indegree.length];

    // Call the recursive helper function to print DFS traversal
    return DFSUtil(ret, root, visited);
  } 
  
  private LinkedList<Integer> DFSUtil(LinkedList<Integer> ret,  int v,boolean visited[]) 
  { 
    // Mark the current node as visited and print it

    visited[v] = true;
    //System.out.print(v + " ");
    ret.add(v);
    // Recur for all the vertices adjacent to this vertex
    Iterator<Integer> i = successors[v].listIterator();
    while (i.hasNext()) 
    {
      int n = i.next();
      if (!visited[n]) 
      {
        DFSUtil(ret, n, visited);
      }
    }
    return ret;
  } 

 
  

}