package com.tec.datos1.tron.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.tec.datos1.tron.R;

import java.util.ArrayList;
import java.util.Random;

public class Board {

    public Cell[][] grid;
    private GameView gameView;
    private Bitmap cellSpriteSheet;
    private int boardSize;
    private int cellsRevealed;

    public Board(GameView gameView, int boardSize) {
        this.grid = new Cell[boardSize][boardSize];
        this.gameView = gameView;
        this.cellSpriteSheet = BitmapFactory.decodeResource(this.gameView.context.getResources(), R.drawable.cell_spritesheet_md);
        this.boardSize = boardSize;
    }

    public void move(){
        int amount = 2;
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid.length; j++) {
                if(amount >= 0){
                    if(!this.grid[i][j].isPaint()){
                        this.grid[i][j].setPaint(true);
                        amount--;
                    }
                }
            }
        }
    }

    /*
     * Draw a particular sprite from the sprite sheet to the screen.  If the cell has
     * not yet been revealed, show the blank sprite.  If the cell has been revealed and is
     * an indication of cheating, show a flag sprite.  If the cell has been revealed, show a
     * bomb.  Otherwise, show the number of neighbors that contain bombs in the cell.
     *
     * @param    Canvas canvas
     * @return   void
     */
    public void draw(Canvas canvas) {
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid.length; j++) {
                if(!this.grid[i][j].isPaint()) {
                    this.grid[i][j].onDraw(canvas, 0, 0);
                }else{
                    this.grid[i][j].onDraw(canvas, 0, 1);
                }
            }
        }
    }

    /*
     * Reset the board to the initial state.  This can act the same as an initialization function.
     * Resetting involves creating a new board, and placing bombs in new positions.
     *
     * @param
     * @return   void
     */
    public void reset() {
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid.length; j++) {
                this.grid[i][j] = new Cell(this.gameView, this.cellSpriteSheet, i, j, false);
            }
        }
        this.calculateCellNeighbors();
        this.setPositions();
        this.cellsRevealed = 0;
    }

    /*
     * Determine all the cells that touch a particular cell
     *
     * @param
     * @return   void
     */
    public void calculateCellNeighbors() {
        for(int x = 0; x < this.grid.length; x++) {
            for(int y = 0; y < this.grid.length; y++) {
                for(int i = this.grid[x][y].getX() - 1; i <= this.grid[x][y].getX() + 1; i++) {
                    for(int j = this.grid[x][y].getY() - 1; j <= this.grid[x][y].getY() + 1; j++) {
                        if(i >= 0 && i < this.grid.length && j >= 0 && j < this.grid.length) {
                            this.grid[x][y].addNeighbor(this.grid[i][j]);
                        }
                    }
                }
            }
        }
    }

    /*
     * Set the position of each cell on the board.  To make things a little more pleasing visually,
     * determine an offset so that the grid is a little more centered in the screen
     *
     * @param
     * @return   void
     */
    public void setPositions() {
        int horizontalOffset = (320 - (this.boardSize * 25)) / 2;
        for(int i = 0; i < this.grid.length; i++) {
            for(int j = 0; j < this.grid.length; j++) {
                this.grid[i][j].setX(horizontalOffset + i * 40);
                this.grid[i][j].setY(140 + j * 40);
            }
        }
    }

    /*
     * Reveal a particular cell on the board.  If it is not a bomb, check to see if it has zero
     * neighbors that are bombs.  Reveal all cells recursively that are neighbors until it finds
     * a neighboring cell that is a bomb and stop.
     *
     * @param    Cell c
     * @return   boolean bomb
     */
    public boolean reveal(Cell c) {
        c.reveal();
        if(!c.isBomb) {
            this.cellsRevealed++;
            if(c.getBombNeighborCount() == 0) {
                ArrayList<Cell> neighbors = c.getNeighbors();
                for(int i = 0; i < neighbors.size(); i++) {
                    if(!neighbors.get(i).isRevealed) {
                        reveal(neighbors.get(i));
                    }
                }
            }
        }
        return c.isBomb;
    }

    /*
     * Return how many cells on the board have been revealed.  Useful for calculating score
     *
     * @param
     * @return   int count
     */
    public int getRevealedCount() {
        return this.cellsRevealed;
    }

}