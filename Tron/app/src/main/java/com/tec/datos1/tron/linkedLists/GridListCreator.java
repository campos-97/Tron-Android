package com.tec.datos1.tron.linkedLists;

	public class GridListCreator {
		
		private static GridLinkedList createGridLinkedList(int size, int y){
			GridLinkedList listToReturn = new GridLinkedList();
			for (int i = 0; i < size; i++){
				GridNode node = new GridNode();
				node.setX(i);
				node.setY(y);
				listToReturn.addLast(node);
			}
			return listToReturn;
		}
		
		private static void LinkTwoGridLinkedList(GridLinkedList listOne, GridLinkedList listTwo){
			int iOne = listOne.getSize();
			int j = 0;
			GridNode currentOne = listOne.getHead();
			GridNode currentTwo = listTwo.getHead();
			while (j < iOne){
				currentOne.lower = currentTwo;
				currentTwo.upper = currentOne;
				currentOne = currentOne.next;
				currentTwo = currentTwo.next;
				j++;
			}	
		}
		
		public static GridNode CreateGrid(int n, int m){
			GridLinkedList one = createGridLinkedList(m, 0);
			GridNode upperLeft = one.getHead();
			for (int i = 0; i < n-1; i++){
				GridLinkedList two = createGridLinkedList(m, i+1);
				LinkTwoGridLinkedList(one, two);
				one = two;
			}
			return upperLeft;
		}
	
}
