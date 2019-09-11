Akash Shah
D-24
131205

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotbfs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class RobotBFS {
    static int row,col,startR,startC,goalR,goalC;
    static int grid[][];
    /**
     * @param args the command line arguments
     */
    
    static void formGrid() throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        
        String line=br.readLine();
        String line1[];
        line1=line.split(" ");
        row=Integer.parseInt(line1[0]);
        col=Integer.parseInt(line1[1]);
        
        grid=new int[row][col];
        int i=0,j=0;
        
        while((line=br.readLine())!=null)
        {
            line1=line.split(" ");
            j=0;
            while(j<col)
            {
                if(line1[j].contains("-"))
                {
                    grid[i][j]=99;  //empty=99
                }
                else if(line1[j].contains("."))
                {
                    grid[i][j]=88; //block=88
                }
                else if(line1[j].contains("s"))
                {
                    grid[i][j]=99;
                    startR=i;
                    startC=j;
                }
                else
                {
                    grid[i][j]=66;  //goal=66
                    goalR=i;
                    goalC=j;
                }
                    
                j++;
            }
            
            i++;
        }
    }
    
    static void findManhattan()
    {
        int i=0,j=0,val;
        
        while(i<row)
        {
            j=0;
            while(j<col)
            {
                if(grid[i][j]==99)
                {
                    val=Math.abs(i-goalR)+Math.abs(j-goalC);
               //     System.out.println(val);
                    grid[i][j]=val;
                }                                
                j++;
            }
            i++;
        }
    }
    
    static void disp()
    {
        int i=0,j=0;
        
        while(i<row)
        {
            j=0;
            while(j<col)
            {
                if(grid[i][j]==55)
                {                    
                    System.out.print("\t*");
                }
                else if(grid[i][j]==66)
                {
                    System.out.print("\tG");
                }
                else if(grid[i][j]==88)
                {
                    System.out.print("\t-");
                }
                else if(grid[i][j]==44)
                {
                    System.out.print("\tS");
                }
                else
                {
                    System.out.print("\t.");
                }
                j++;
            }
            System.out.println("");
            i++;
        }
    }
    
    static void backPath() //to backtrack found path
    {
        int currR=goalR,currC=goalC;
        
        while(true)
        {
            if(currR==startR && currC==startC)
            {
                break;
            }
            else if(currR-1>=0 && grid[currR-1][currC]==77)
            {
                grid[currR-1][currC]=55;
                currR=currR-1;
            }
            else if(currR+1<row && grid[currR+1][currC]==77)
            {
                grid[currR+1][currC]=55;
                currR=currR+1;
            }
            else if(currC-1>=0 && grid[currR][currC-1]==77)
            {
                grid[currR][currC-1]=55;
                currC=currC-1;
            }
            else if(currC+1<col && grid[currR][currC+1]==77)
            {
                grid[currR][currC+1]=55;
                currC=currC+1;
            }
        }
        grid[startR][startC]=44;
    }
    
    static void findPath()
    {        
        int currR=startR,currC=startC,smallR=startR,smallC=startC,closeLoc=0,openSize=0;
        ArrayList<int[]> openList=new ArrayList<int[]>();
        ArrayList<int[]> closeList=new ArrayList<int[]>();
        int currLoc[]=new int[2];
        
        
        while(true)
        {
            grid[currR][currC]=77;            
            currLoc=new int[2];
            currLoc[0]=currR;
            currLoc[1]=currC;
            closeList.add(currLoc);
            closeLoc++;           
            smallR=currR;
            smallC=currC;
            int i=0;
            
            if(currR-1>=0 && grid[currR-1][currC]!=77 && grid[currR-1][currC]!=88)
            {
                if(grid[currR-1][currC]==66)
                {
                    break;
                }
         //       System.out.println("R-1");
                currLoc=new int[2];
                currLoc[0]=currR-1;
                currLoc[1]=currC;
                openList.add(currLoc);
                
                openSize++;
            }
            if(currR+1<row && grid[currR+1][currC]!=77 && grid[currR+1][currC]!=88)
            {
                if(grid[currR+1][currC]==66)
                {
                    break;
                }
                currLoc=new int[2];
                currLoc[0]=currR+1;
                currLoc[1]=currC;
                openList.add(currLoc);
                openSize++;
         //       System.out.println("R+1");
            }
            if(currC-1>=0 && grid[currR][currC-1]!=77 && grid[currR][currC-1]!=88)
            {
                if(grid[currR][currC-1]==66)
                {
                    break;
                }
                currLoc=new int[2];
                currLoc[0]=currR;
                currLoc[1]=currC-1;
                openList.add(currLoc);
                openSize++;
         //       System.out.println("C-1");
            }
            if(currC+1<col && grid[currR][currC+1]!=77 && grid[currR][currC+1]!=88)
            {
                if(grid[currR][currC+1]==66)
                {
                    break;
                }
                currLoc=new int[2];
                currLoc[0]=currR;
                currLoc[1]=currC+1;
                openList.add(currLoc);
                openSize++;
             //   System.out.println("C+1");
            }
            if(openSize>0)    
            {                
                currLoc=new int[2];
                currLoc=openList.get(0);
                smallR=currLoc[0];
                smallC=currLoc[1];
                i=1;
                int smallLoc=0;
                while(i<openSize)
                {
                    currLoc=new int[2];
                    currLoc=openList.get(i);
                    if(grid[smallR][smallC]>grid[currLoc[0]][currLoc[1]])
                    {
                        smallR=currLoc[0];
                        smallC=currLoc[1];                        
                        smallLoc=i;
                    }
            //        System.out.println("cyril_ai_robotbfs.Cyril_ai_RobotBFS.findPath()"+grid[currLoc[0]][currLoc[1]]);
                    i++;    
                }
                currLoc=new int[2];
                currLoc[0]=smallR;
                currLoc[1]=smallC;
                //System.out.println("cyril_ai_robotbfs.Cyril_ai_RobotBFS.findPath()"+openList.size()+i);
                openList.remove(smallLoc);
             //   System.out.println("cyril_ai_robotbfs.Cyril_ai_RobotBFS.findPath()"+openList.size());
                openSize--;
                currR=smallR;
                currC=smallC;
            }
            //System.out.println("====================");
            //disp();
        }
        backPath();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        formGrid();
        findManhattan();
        disp();
        System.out.println("====================");
        findPath();
        disp();
    }
    
}

=================================
INPUT:
5 11
- - - - - - - - - - -
- . - - - . . . . . -
- . . - - - g . . . -
s - . . . . . . . . -
- - - - - - - - - - -



OUTPUT:
	.	.	.	.	*	*	*	*	*	*	*
	.	-	.	.	*	-	-	-	-	-	*
	.	-	-	.	*	*	G	-	-	-	*
	S	*	-	-	-	-	-	-	-	-	*
	.	*	*	*	*	*	*	*	*	*	*
	
	
	
	===============================================
	
	
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robotAStar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class RobotAStar {
    static int row,col,startR,startC,goalR,goalC;
    static int grid[][];
    /**
     * @param args the command line arguments
     */
    
    static void formGrid() throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        
        String line=br.readLine();
        String line1[];
        line1=line.split(" ");
        row=Integer.parseInt(line1[0]);
        col=Integer.parseInt(line1[1]);
        
        grid=new int[row][col];
        int i=0,j=0;
        
        while((line=br.readLine())!=null)
        {
            line1=line.split(" ");
            j=0;
            while(j<col)
            {
                if(line1[j].contains("-"))
                {
                    grid[i][j]=99;  //empty=99
                }
                else if(line1[j].contains("."))
                {
                    grid[i][j]=88; //block=88
                }
                else if(line1[j].contains("s"))
                {
                    grid[i][j]=99;
                    startR=i;
                    startC=j;
                }
                else
                {
                    grid[i][j]=66;  //goal=66
                    goalR=i;
                    goalC=j;
                }
                    
                j++;
            }
            
            i++;
        }
    }
    
    static void findManhattan()
    {
        int i=0,j=0,val;
        
        while(i<row)
        {
            j=0;
            while(j<col)
            {
                if(grid[i][j]==99)
                {
                    val=Math.abs(i-goalR)+Math.abs(j-goalC);
                    grid[i][j]=val;
                }                                
                j++;
            }
            i++;
        }
    }
    
    static void disp()
    {
        int i=0,j=0;
        
        while(i<row)
        {
            j=0;
            while(j<col)
            {
                if(grid[i][j]==55)
                {                    
                    System.out.print("\t*");
                }
                else if(grid[i][j]==66)
                {
                    System.out.print("\tG");
                }
                else if(grid[i][j]==88)
                {
                    System.out.print("\t-");
                }
                else if(grid[i][j]==44)
                {
                    System.out.print("\tS");
                }
                else
                {
                    System.out.print("\t.");
                }
                j++;
            }
            System.out.println("");
            i++;
        }
    }
    
    static int backPath(int tempGrid[][],int currR,int currC) //to backtrack found path
    {
        int tempGrid1[][]=new int[row][col];
        int i=0,j=0;
        
        while(i<row)
        {
            j=0;
            while(j<col)
            {
                tempGrid1[i][j]=tempGrid[i][j];
                j++;
            }
            i++;
        } 
        tempGrid1[currR][currC]=99;
        
        if(currR==startR && currC==startC)
        {
            grid[currR][currC]=44;
            return 1;
        }
        if(currR-1>=0 && tempGrid1[currR-1][currC]==77)
        {
            i=0;
            i=backPath(tempGrid1,currR-1,currC);
            if(i==1)
            {
                grid[currR][currC]=55;
                return 1;
            }
        }
        if(currR+1<row && tempGrid1[currR+1][currC]==77)
        {
            i=0;
            i=backPath(tempGrid1,currR+1,currC);
            if(i==1)
            {
                grid[currR][currC]=55;
                return 1;
            }
        }
        if(currC-1>=0 && tempGrid1[currR][currC-1]==77)
        {
            i=0;
            i=backPath(tempGrid1,currR,currC-1);
            if(i==1)
            {
                grid[currR][currC]=55;
                return 1;
            }
        }
        if(currC+1<col && tempGrid1[currR][currC+1]==77)
        {
            i=0;
            i=backPath(tempGrid1,currR,currC+1);
            if(i==1)
            {
                grid[currR][currC]=55;
                return 1;
            }
        }
        
        return 0;
    }
    
    static void findPath()
    {        
        int currR=startR,currC=startC,smallR=startR,smallC=startC,closeLoc=0,openSize=0,distance=0;
        ArrayList<int[]> openList=new ArrayList<int[]>();
        ArrayList<int[]> closeList=new ArrayList<int[]>();
        int currLoc[]=new int[3];
        
        
        while(true)
        {
            grid[currR][currC]=77;            
            currLoc=new int[3];
            currLoc[0]=currR;
            currLoc[1]=currC;
            currLoc[2]=distance;
            closeList.add(currLoc);
            closeLoc++;           
            smallR=currR;
            smallC=currC;
            int i=0;
            
            if(currR-1>=0 && grid[currR-1][currC]!=77 && grid[currR-1][currC]!=88)
            {
                if(grid[currR-1][currC]==66)
                {
                    break;
                }
         //       System.out.println("R-1");
                currLoc=new int[3];
                currLoc[0]=currR-1;
                currLoc[1]=currC;
                currLoc[2]=distance+1;
                grid[currLoc[0]][currLoc[1]]=grid[currLoc[0]][currLoc[1]]+distance+1;
                openList.add(currLoc);
                
                openSize++;
            }
            if(currR+1<row && grid[currR+1][currC]!=77 && grid[currR+1][currC]!=88)
            {
                if(grid[currR+1][currC]==66)
                {
                    break;
                }
                currLoc=new int[3];
                currLoc[0]=currR+1;
                currLoc[1]=currC;
                currLoc[2]=distance+1;
                grid[currLoc[0]][currLoc[1]]=grid[currLoc[0]][currLoc[1]]+distance+1;
                openList.add(currLoc);
                openSize++;
         //       System.out.println("R+1");
            }
            if(currC-1>=0 && grid[currR][currC-1]!=77 && grid[currR][currC-1]!=88)
            {
                if(grid[currR][currC-1]==66)
                {
                    break;
                }
                currLoc=new int[3];
                currLoc[0]=currR;
                currLoc[1]=currC-1;
                currLoc[2]=distance+1;
                grid[currLoc[0]][currLoc[1]]=grid[currLoc[0]][currLoc[1]]+distance+1;
                openList.add(currLoc);
                
                openSize++;
         //       System.out.println("C-1");
            }
            if(currC+1<col && grid[currR][currC+1]!=77 && grid[currR][currC+1]!=88)
            {
                if(grid[currR][currC+1]==66)
                {
                    break;
                }
                currLoc=new int[3];
                currLoc[0]=currR;
                currLoc[1]=currC+1;
                currLoc[2]=distance+1;
                grid[currLoc[0]][currLoc[1]]=grid[currLoc[0]][currLoc[1]]+distance+1;
                openList.add(currLoc);
                openSize++;
             //   System.out.println("C+1");
            }
            if(openSize>0)    
            {                
                currLoc=new int[3];
                currLoc=openList.get(0);
                smallR=currLoc[0];
                smallC=currLoc[1];
                distance=currLoc[2];
                i=1;
                int smallLoc=0;
                while(i<openSize)
                {
                    currLoc=new int[3];
                    currLoc=openList.get(i);
                    if(grid[smallR][smallC]>grid[currLoc[0]][currLoc[1]])
                    {
                        smallR=currLoc[0];
                        smallC=currLoc[1];                        
                        distance=currLoc[2];
                        smallLoc=i;
                    }
                    i++;    
                }
                currLoc=new int[3];
                currLoc[0]=smallR;
                currLoc[1]=smallC;
                currLoc[2]=distance;
               
                openList.remove(smallLoc);
            
                openSize--;
                currR=smallR;
                currC=smallC;
            }
           // System.out.println("====================");
            //disp();
        }
        try {
            backPath(grid,goalR,goalC);
            grid[goalR][goalC]=66;
        } catch(StackOverflowError t) {
            // more general: catch(Error t)
            // anything: catch(Throwable t)
            System.out.println("Caught "+t);
            t.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        formGrid();
        findManhattan();
        disp();
        System.out.println("====================");
        findPath();
        disp();
    }
    
}


=================================
INPUT:
5 11
- - - - - - - - - - -
- . - - - . . . . . -
- . . - - - g . . . -
s - . . . . . . . . -
- - - - - - - - - - -

OUTPUT:

	*	*	*	*	*	.	.	.	.	.	.
	*	-	.	.	*	-	-	-	-	-	.
	*	-	-	.	*	*	G	-	-	-	.
	S	.	-	-	-	-	-	-	-	-	.
	.	.	.	.	.	.	.	.	.	.	.	
