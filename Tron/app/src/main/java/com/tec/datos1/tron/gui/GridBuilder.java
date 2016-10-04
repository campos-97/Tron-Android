package com.tec.datos1.tron.gui;

/**
 * Created by josea on 10/1/2016.
 */
public class GridBuilder {
    Line line = new Line();
    int numRows;
    int numColumns;

    public GridBuilder(int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        buildGrid();
    }

    public void buildGrid(){
        for(int i = 0 ; i <= numRows ; i++){
             line.addVertexCoord(0,-i,0);
             line.addVertexCoord(-numRows,-i,0);
         }

         for(int j = 0 ; j <= numColumns ; j++){
             line.addVertexCoord(-j,0,0);
             line.addVertexCoord(-j,numColumns,0);
         }
    }
}
