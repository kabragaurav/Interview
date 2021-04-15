/**
 * A 2D matrix MxN (M,N>=2) has unique numbers > 0. 
 * You can start from anywhere and go up, down, left and right. 
 * You can move in a direction if the number obtained is larger than the one on which you are.
 * What is maximum length you can tranverse?
 */

/**
 * @author: Gaurav Kabra
 * @date: Apr 15, 2021
 */

package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class MaxDepthSolver {
	
	static int globalMaxi = -1;
	

	// Sample inputs
/**
	static int x = 3;
	static int y = 4;
	static int[][] array = {
			{
				1, 24, 25, 2
			},
			{
				23, 5, 4, 3
			},
			{
				21, 6, 7, 8
			}
	};
**/
/**
	static int x = 5;
	static int y = 5;
	static int[][] array = {
			{
				1, 2, 3, 4, 5
			},
			{
				10, 9, 8, 7, 6
			},
			{
				11, 12, 13, 14, 15
			},
			{
				20, 19, 18, 17, 16
			},
			{
				21, 22, 23, 24, 25
			}
	};
**/

/**
	static int x = 2;
	static int y = 2;
	static int[][] array = {{1, 2},{3, 4}
	};
**/
	
	static int x = 5;
	static int y = 5;
	static int[][] array = {
			{
				1, 10, 17, 22, 25
			},
			{
				9, 2, 11, 18, 23
			},
			{
				16, 8, 3, 12, 19
			},
			{
				21, 15, 7, 4, 13
			},
			{
				24, 20, 14, 6, 5
			}
	};
	
	static Map<Integer, Map<Integer, Integer>> mp = new TreeMap<Integer, Map<Integer, Integer>>();
	static Map<Integer, List<Integer>> helper = new HashMap<Integer, List<Integer>>();
	
	static int[] xOperations = {-1, 0, 1, 0};
	static int[] yOperations = {0, 1, 0, -1};
	
	
	private static void getCoordinates() {
		Map<Integer, Integer> temp;
		// x is vertical, y is horizontal
		for(int i=0; i<x; i++) {
			for(int j=0; j<y; j++) {
				int value = array[i][j];
				temp = new HashMap<Integer, Integer>();
				temp.put(i, j);
				mp.put(value, temp);
				
				// also store value with initial list of {U, R, D, L} i.e. <value, {0,0,0,0}>
				helper.put(value,  new ArrayList<Integer>(Arrays.asList(0,0,0,0)));
			}
		}
		//System.out.println(mp);
	}
	
	private static boolean isBounded(int newX, int newY) {
		return newX >=0 && newY >=0 && newX < x && newY < y;
	}
	
	private static int getMaximum(int x, int y) {
		int maxi = -1;
		for(int i=0; i<4; i++) {
			int newX =  x + xOperations[i];
			int newY =  y + yOperations[i];
			if(isBounded(newX, newY)) {
				List<Integer> other = helper.get(array[newX][newY]);     // where you want to go
				maxi = Math.max(maxi, Collections.max(other));
			}
		}
		return maxi;
	}
	
	private static void upRightDownLeft(int key, Map<Integer, Integer> temp) {
		//System.out.println(temp);
		int x=0, y=0;
		for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
			x = entry.getKey();
			y = entry.getValue();
		}
		List<Integer> present = helper.get(array[x][y]);		// where you are
		for(int i=0; i<4; i++) {
			int newX =  x + xOperations[i];					
			int newY =  y + yOperations[i];
			
			if(isBounded(newX, newY) && array[x][y] < array[newX][newY]) {
				//List<Integer> other = helper.get(array[newX][newY]);      // where you want to go
				int mx = getMaximum(x, y)+1;
				present.set(i, mx);
				globalMaxi = Math.max(mx, globalMaxi);
			}
			/**else {
				present.set(i, 0);
			}**/
		}
		helper.put(key, present);			// update with new value
	}
	
	private static void explore() {
		for(Map.Entry<Integer, Map<Integer, Integer>> entry: mp.entrySet()) {
			int key = entry.getKey();						// value
			Map<Integer, Integer> temp = entry.getValue();  // coordinates
			
			upRightDownLeft(key, temp);
		}
	}

	public static void main(String[] args) {
		// for every value in matrix, store its <value, coordinates>
		getCoordinates();
		explore();
		System.out.println(globalMaxi);
	}
}
