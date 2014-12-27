package model.MARK_II.connectTypes;

import model.MARK_II.*;
import java.awt.Point;

/**
 * @author Nathan Waggoner(nwagg14@vt.edu)
 * @author Quinn Liu(quinnliu@vt.edu)
 * @version 10/16/14
 */
public class RegionToRegionRectangleConnect extends
        AbstractRegionToRegionConnect {
    @Override
    public void connect(Column[][] bottomLayer, Column[][] topLayer,
                        int numberOfColumnsToOverlapAlongNumberOfRows,
                        int numberOfColumnsToOverlapAlongNumberOfColumns) {
        int rowBinitial, rowBfinal, colBinitial, colBfinal;

        int topRowLength = topLayer.length;
        int topColLength = topLayer[0].length;
        int botRowLength = bottomLayer.length;
        int botColLength = bottomLayer[0].length;

        for(int rowT = 0; rowT < topRowLength; rowT++){
            Point rowReceptiveField = updateReceptiveFieldDimensionLengthWithOverlap(topRowLength, botRowLength, rowT, numberOfColumnsToOverlapAlongNumberOfRows);
            rowBinitial = (int) rowReceptiveField.getX();
            rowBfinal = (int) rowReceptiveField.getY();

            for(int colT = 0; colT < topColLength; colT++) {
                Point colReceptiveField = updateReceptiveFieldDimensionLengthWithOverlap(topColLength, botColLength, colT, numberOfColumnsToOverlapAlongNumberOfColumns);
                colBinitial = (int) colReceptiveField.getX();
                colBfinal = (int) colReceptiveField.getY();

                // actually add synapses from bottom layer receptive field to top layer column
                Column topColumn = topLayer[rowT][colT];
                for (int rowB = rowBinitial; rowB <= rowBfinal; rowB++){
                    for (int colB = colBinitial; colB <= colBfinal; colB++) {
                        for(Neuron neuron : bottomLayer[rowB][colB].getNeurons()){
                            topColumn.getProximalSegment().addSynapse(new Synapse<Cell>(neuron, rowB, colB));
                        }
                    }
                }
            }
        }
    }
    
    Point updateReceptiveFieldDimensionLength (int topLength, int botLength, int topIndex) {
        if (topLength > botLength) {
            throw new IllegalStateException("In class NewRegionToRegionRectangleConnect" +
                " method updateReceptiveFieldDimensionLength() topLength must be <= botLength");
        } 

        int Binitial;
        int Bfinal;
        if (topIndex < botLength % topLength) {
            Binitial = topIndex * (botLength/topLength) + topIndex;
            Bfinal = (topIndex + 1) * (botLength/topLength) + (topIndex + 1) -1; // -1 of next rowBinitial
        } else {
            Binitial = topIndex * (botLength/topLength) + botLength % topLength;
            Bfinal = (topIndex + 1) * (botLength/topLength) + botLength % topLength - 1;
        }
        
        return new Point(Binitial, Bfinal);
    }

    Point updateReceptiveFieldDimensionLengthWithOverlap(int topLength, int botLength, int topIndex, int overlap ) {
        Point withoutOverlap = updateReceptiveFieldDimensionLength (topLength, botLength, topIndex);

        int newBinitial = (int) withoutOverlap.getX() - overlap;
        if (newBinitial < 0) {
            newBinitial = 0;
        }

        int newBfinal = (int) withoutOverlap.getY() + overlap;
        if (newBfinal > botLength - 1) {
            newBfinal = botLength - 1;
        }

        return new Point(newBinitial, newBfinal);
    }
}
