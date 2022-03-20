/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gargoyle.ct.ui.util.render

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.LayoutManager2
import java.awt.Rectangle
import kotlin.math.max

/**
 * The `GraphPaperLayout` class is a layout manager that
 * lays out a container's components in a rectangular grid, similar
 * to GridLayout.  Unlike GridLayout, however, components can take
 * up multiple rows and/or columns.  The layout manager acts as a
 * sheet of graph paper.  When a component is added to the layout
 * manager, the location and relative size of the component are
 * simply supplied by the constraints as a Rectangle.
 *
 * ``<pre>
 * import java.awt.*;
 * import java.applet.Applet;
 * public class ButtonGrid extends Applet {
 * public void init() {
 * setLayout(new GraphPaperLayout(new Dimension(5,5)));
 * // Add a 1x1 Rect at (0,0)
 * add(new Button("1"), new Rectangle(0,0,1,1));
 * // Add a 2x1 Rect at (2,0)
 * add(new Button("2"), new Rectangle(2,0,2,1));
 * // Add a 1x2 Rect at (1,1)
 * add(new Button("3"), new Rectangle(1,1,1,2));
 * // Add a 2x2 Rect at (3,2)
 * add(new Button("4"), new Rectangle(3,2,2,2));
 * // Add a 1x1 Rect at (0,4)
 * add(new Button("5"), new Rectangle(0,4,1,1));
 * // Add a 1x2 Rect at (2,3)
 * add(new Button("6"), new Rectangle(2,3,1,2));
 * }
 * }
</pre> *
 *
 * @author Michael Martak
 */
class GraphPaperLayout private constructor(gridSize: Dimension, hGap: Int, vGap: Int) : LayoutManager2 {
    private val hGap //horizontal gap
            : Int
    private val components //constraints (Rectangles)
            : MutableMap<Component?, Rectangle>
    private val vGap //vertical gap
            : Int
    private var gridSize //grid size in logical units (n x m)
            : Dimension

    /**
     * Creates a graph paper layout with a default of a 1 x 1 graph, with no
     * vertical or horizontal padding.
     */
    constructor() : this(Dimension(1, 1))

    /**
     * Creates a graph paper layout with the given grid size, with no vertical
     * or horizontal padding.
     */
    private constructor(gridSize: Dimension) : this(gridSize, 0, 0)

    /**
     * Creates a graph paper layout with the given grid size and padding.
     *
     * @param width  size of the graph paper in logical units (n x m)
     * @param height size of the graph paper in logical units (n x m)
     * @param hGap   horizontal padding
     * @param vGap   vertical padding
     */
    constructor(width: Int, height: Int, hGap: Int, vGap: Int) : this(Dimension(width, height), hGap, vGap)

    /**
     * Creates a graph paper layout with the given grid size and padding.
     *
     * @param gridSize size of the graph paper in logical units (n x m)
     * @param hGap     horizontal padding
     * @param vGap     vertical padding
     */
    init {
        require(!(gridSize.width <= 0 || gridSize.height <= 0)) { "dimensions must be greater than zero" }
        this.gridSize = Dimension(gridSize)
        this.hGap = hGap
        this.vGap = vGap
        components = HashMap()
    }

    /**
     * Creates a graph paper layout graph, with no
     * vertical or horizontal padding.
     *
     * @param width size of the graph paper in logical units (n x m)
     * @param height size of the graph paper in logical units (n x m)
     */
    constructor(width: Int, height: Int) : this(Dimension(width, height))

    /**
     * Adds the specified component with the specified name to
     * the layout.  This does nothing in GraphPaperLayout, since constraints
     * are required.
     */
    override fun addLayoutComponent(name: String, comp: Component) {}

    /**
     * Algorithm for calculating the largest minimum or preferred cell size.
     *
     *
     * Largest cell size is calculated by getting the applicable size of each
     * component and keeping the maximum value, dividing the component's width
     * by the number of columns it is specified to occupy and dividing the
     * component's height by the number of rows it is specified to occupy.
     *
     * @param parent      the container in which to do the layout.
     * @param isPreferred true for calculating preferred size, false for
     * calculating minimum size.
     * @return the largest cell size required.
     */
    private fun getLargestCellSize(
        parent: Container,
        isPreferred: Boolean
    ): Dimension {
        val componentCount = parent.componentCount
        val maxCellSize = Dimension(0, 0)
        for (i in 0 until componentCount) {
            val c = parent.getComponent(i)
            val rect = components[c]
            if (c != null && rect != null) {
                val componentSize = if (isPreferred) c.preferredSize else c.minimumSize
                // Note: rect dimensions are already asserted to be > 0 when the
                // component is added with constraints
                maxCellSize.width = max(
                    maxCellSize.width,
                    componentSize.width / rect.width
                )
                maxCellSize.height = max(
                    maxCellSize.height,
                    componentSize.height / rect.height
                )
            }
        }
        return maxCellSize
    }

    /**
     * Calculates the preferred size dimensions for the specified
     * panel given the components in the specified parent container.
     *
     * @param parent the component to be laid out
     * @see .minimumLayoutSize
     */
    override fun preferredLayoutSize(parent: Container): Dimension {
        return getLayoutSize(parent, true)
    }

    /**
     * Calculates the minimum size dimensions for the specified
     * panel given the components in the specified parent container.
     *
     * @param parent the component to be laid out
     * @see .preferredLayoutSize
     */
    override fun minimumLayoutSize(parent: Container): Dimension {
        return getLayoutSize(parent, false)
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    override fun removeLayoutComponent(comp: Component) {
        components.remove(comp)
    }

    /**
     * Algorithm for calculating layout size (minimum or preferred).
     *
     *
     * The width of a graph paper layout is the largest cell width
     * calculated in `getLargestCellSize()` times the number of
     * columns, plus the horizontal padding times the number of columns
     * plus one, plus the left and right insets of the target container.
     *
     *
     * The height of a graph paper layout is the largest cell height
     * calculated in `getLargestCellSize()` times the number of
     * rows, plus the vertical padding times the number of rows
     * plus one, plus the top and bottom insets of the target container.
     *
     * @param parent      the container in which to do the layout.
     * @param isPreferred true for calculating preferred size, false for
     * calculating minimum size.
     * @return the dimensions to lay out the subcomponents of the specified
     * container.
     * @see GraphPaperLayout.getLargestCellSize
     */
    private fun getLayoutSize(parent: Container, isPreferred: Boolean): Dimension {
        val largestSize = getLargestCellSize(parent, isPreferred)
        val insets = parent.insets
        largestSize.width =
            largestSize.width * gridSize.width + hGap * (gridSize.width + 1) + insets.left + insets.right
        largestSize.height =
            largestSize.height * gridSize.height + vGap * (gridSize.height + 1) + insets.top + insets.bottom
        return largestSize
    }

    /**
     * Lays out the container in the specified container.
     *
     * @param parent the component which needs to be laid out
     */
    override fun layoutContainer(parent: Container) {
        synchronized(parent.treeLock) {
            val insets = parent.insets
            val componentCount = parent.componentCount
            if (componentCount == 0) {
                return
            }
            // Total parent dimensions
            val size = parent.size
            val totalW = size.width - (insets.left + insets.right)
            val totalH = size.height - (insets.top + insets.bottom)
            // Cell dimensions, including padding
            val totalCellW = totalW / gridSize.width
            val totalCellH = totalH / gridSize.height
            // Cell dimensions, without padding
            val cellW = ((totalW - (gridSize.width + 1) * hGap)
                    / gridSize.width)
            val cellH = ((totalH - (gridSize.height + 1) * vGap)
                    / gridSize.height)
            for (i in 0 until componentCount) {
                val c = parent.getComponent(i)
                val rect = components[c]
                if (rect != null) {
                    val x = insets.left + totalCellW * rect.x + hGap
                    val y = insets.top + totalCellH * rect.y + vGap
                    val w = cellW * rect.width - hGap
                    val h = cellH * rect.height - vGap
                    c.setBounds(x, y, w, h)
                }
            }
        }
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp        the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    override fun addLayoutComponent(comp: Component, constraints: Any) {
        if (constraints is Rectangle) {
            require(!(constraints.width <= 0 || constraints.height <= 0)) { "cannot add to layout: rectangle must have positive width and height" }
            require(!(constraints.x < 0 || constraints.y < 0)) { "cannot add to layout: rectangle x and y must be >= 0" }
            setConstraints(comp, constraints)
        } else error { "cannot add to layout: constraint must be a Rectangle" }
    }

    private fun setConstraints(comp: Component, constraints: Rectangle) {
        components[comp] = Rectangle(constraints)
    }

    /**
     * Returns the maximum size of this component.
     *
     * @see Component.getMinimumSize
     * @see Component.getPreferredSize
     * @see LayoutManager
     */
    override fun maximumLayoutSize(target: Container): Dimension {
        return Dimension(Int.MAX_VALUE, Int.MAX_VALUE)
    }

    /**
     * Returns the alignment along the x-axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    override fun getLayoutAlignmentX(target: Container): Float {
        return H_CENTER
    }
    // LayoutManager2 /////////////////////////////////////////////////////////
    /**
     * Returns the alignment along the y-axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc.
     */
    override fun getLayoutAlignmentY(target: Container): Float {
        return V_CENTER
    }

    /**
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     */
    override fun invalidateLayout(target: Container) {
        // Do nothing
    }

    /**
     * @return the size of the graph paper in logical units (n x m)
     */
    fun getGridSize(): Dimension {
        return Dimension(gridSize)
    }

    /**
     * Set the size of the graph paper in logical units (n x m)
     */
    fun setGridSize(d: Dimension) {
        setGridSize(d.width, d.height)
    }

    /**
     * Set the size of the graph paper in logical units (n x m)
     */
    private fun setGridSize(width: Int, height: Int) {
        gridSize = Dimension(width, height)
    }

    companion object {
        private const val H_CENTER = 0.5f
        private const val V_CENTER = 0.5f
    }
}
