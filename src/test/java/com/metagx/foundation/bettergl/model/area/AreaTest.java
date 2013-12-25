package com.metagx.foundation.bettergl.model.area;

import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.model.LineType;
import com.metagx.foundation.bettergl.model.MotionModel;
import com.metagx.foundation.bettergl.model.OpenGLLine;
import com.metagx.foundation.math.Rectangle;
import com.metagx.foundation.math.Vector;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Adam on 12/17/13.
 */
public class AreaTest {

    GLGraphics glGraphics;

    @Before
    public void setup() {
        glGraphics = mock(GLGraphics.class);
    }

    @Test
    public void testAreaSplitHorizontal() {
        float centerX = 20;
        float centerY = 20;
        float width = 20;
        float height = 20;
        Area area = new Area(glGraphics, centerX, centerY, width, height);

        Vector splitPosition = new Vector(15, 15);
        /*
            Split the
           10,20 ___________ 30,30
                |         |
                |         |
                |---------|  - - -- -  - - - x,15 horizontal line
            10,10 |_________| 30,10

            new area's after split ->

            1:
            10,30   30, 30
            10,15    30, 15

            2:
            10,15    30,15
            10,10    30,10

         */

        Rectangle expectedAreaTop = new Rectangle(10,15,20,15);
        Rectangle expectedAreaBottom = new Rectangle(10,10,20,5);

        MotionModel mockLineMotionModel = mock(MotionModel.class);
        when(mockLineMotionModel.getPosition()).thenReturn(splitPosition);

        OpenGLLine mockLine = mock(OpenGLLine.class);
        when(mockLine.getLineType()).thenReturn(LineType.HORIZONTAL);
        when(mockLine.getMotionModel()).thenReturn(mockLineMotionModel);

        SplitArea areaPair = area.splitArea(mockLine);

        assertRectCompare(expectedAreaTop, areaPair.leftTop.areaBounds);
        assertRectCompare(expectedAreaBottom, areaPair.rightBottom.areaBounds);
    }

    @Test
    public void testAreaDoubleSplitHorizontalOnBottom() {
        float centerX = 20;
        float centerY = 20;
        float width = 20;
        float height = 20;
        Area area = new Area(glGraphics, centerX, centerY, width, height);

        Vector splitPosition = new Vector(15, 15);
        /*
            Split the
           10,20 ___________ 30,30
                |         |
                |         |
                |---------|  - - -- -  - - - x,15 horizontal line
            10,10 |_________| 30,10

            new area's after split ->

            1:
            10,30   30, 30
            10,15    30, 15

            2:
            10,15    30,15
            10,10    30,10

         */

        Rectangle expectedAreaTop = new Rectangle(10,15,20,15);
        Rectangle expectedAreaBottom = new Rectangle(10,10,20,5);

        MotionModel mockLineMotionModel = mock(MotionModel.class);
        when(mockLineMotionModel.getPosition()).thenReturn(splitPosition);

        OpenGLLine mockLine = mock(OpenGLLine.class);
        when(mockLine.getLineType()).thenReturn(LineType.HORIZONTAL);
        when(mockLine.getMotionModel()).thenReturn(mockLineMotionModel);

        SplitArea areaPair = area.splitArea(mockLine);

        assertRectCompare(expectedAreaTop, areaPair.leftTop.areaBounds);
        assertRectCompare(expectedAreaBottom, areaPair.rightBottom.areaBounds);

        //2nd split

        Area bottom = areaPair.rightBottom;

        Vector splitPosition2 = new Vector(12, 12);
        when(mockLineMotionModel.getPosition()).thenReturn(splitPosition2);

        SplitArea pair2 = bottom.splitArea(mockLine);

        /**
         * split 10,10,20,5
         * at 12,12
         *
         * 10,15  30,15
         * 10,12  30,12
         *
         * 10,12  30,12
         * 10,10  30,10
         */


        Rectangle expectedPair2Top = new Rectangle(10,12,20,3);
        Rectangle expectedPair2Bottom = new Rectangle(10,10,20,2);

        assertRectCompare(expectedPair2Top, pair2.leftTop.areaBounds);
        assertRectCompare(expectedPair2Bottom, pair2.rightBottom.areaBounds);

    }

    @Test
    public void testAreaSplitVertical() {
        float centerX = 20;
        float centerY = 20;
        float width = 20;
        float height = 20;
        Area area = new Area(glGraphics, centerX, centerY, width, height);

        Vector splitPosition = new Vector(15, 15);
        /*
            Split the
           10,30  ___________ 30,30
                  |  |      |
                  |  |      |
                  |  |      |
            10,10 |__|______| 30,10

                    15,y vertical line

            new area's after split ->

            1:
            10,30   15, 30
            10,10   15, 10

            2:
            15,30    30,30
            15,10    30,10

         */

        Rectangle expectedAreaTop = new Rectangle(10,10, 5, 20);
        Rectangle expectedAreaBottom = new Rectangle(15,10, 15,20);

        MotionModel mockLineMotionModel = mock(MotionModel.class);
        when(mockLineMotionModel.getPosition()).thenReturn(splitPosition);

        OpenGLLine mockLine = mock(OpenGLLine.class);
        when(mockLine.getLineType()).thenReturn(LineType.VERTICAL);
        when(mockLine.getMotionModel()).thenReturn(mockLineMotionModel);

        SplitArea areaPair = area.splitArea(mockLine);

        assertRectCompare(expectedAreaTop, areaPair.leftTop.areaBounds);
        assertRectCompare(expectedAreaBottom, areaPair.rightBottom.areaBounds);
    }

    private void assertRectCompare(Rectangle a, Rectangle b) {
        assertEquals(a.lowerLeft.x, b.lowerLeft.x);
        assertEquals(a.lowerLeft.y,b.lowerLeft.y);
        assertEquals(a.width,b.width);
        assertEquals(a.height,b.height);
    }

    @Test
    public void testAreaBounds() {
        float centerX = 10;
        float centerY = 10;
        float width = 20;
        float height = 20;

        //Should make it
        /*
            0,20   20,20
            0,0    20,0
         */

        Area area = new Area(glGraphics, centerX, centerY, width, height);

        assertTrue(area.areaBounds.lowerLeft.x == 0);
        assertTrue(area.areaBounds.lowerLeft.y == 0);

        assertTrue(area.areaBounds.width == 20);
        assertTrue(area.areaBounds.height == 20);
    }
}
