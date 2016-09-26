package com.tec.datos1.tron.gui;

import android.graphics.Bitmap;

import com.tec.datos1.tron.linkedLists.GridNode;

import java.util.ArrayList;

public class Cell extends Sprite {

    private GridNode node;
    private boolean paint;

    public Cell(GameView gameView, Bitmap spriteSheet, int x, int y) {
        super(gameView, spriteSheet, x, y, 3, 4);
        this.paint = false;
    }

    public boolean isPaint() {
        return paint;
    }

    public void setPaint(boolean paint) {
        this.paint = paint;
    }

    public GridNode getNode() {
        return node;
    }

    public void setNode(GridNode node) {
        this.node = node;
    }
}