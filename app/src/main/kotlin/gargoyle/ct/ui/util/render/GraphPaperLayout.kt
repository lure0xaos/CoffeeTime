package gargoyle.ct.ui.util.render

import java.awt.*
import kotlin.math.max

class GraphPaperLayout private constructor(gridSize: Dimension, hGap: Int, vGap: Int) : LayoutManager2 {
    private val hGap: Int
    private val components: MutableMap<Component, Rectangle>
    private val vGap: Int
    private var gridSize: Dimension

    constructor() : this(Dimension(1, 1))

    private constructor(gridSize: Dimension) : this(gridSize, 0, 0)

    constructor(width: Int, height: Int, hGap: Int, vGap: Int) : this(Dimension(width, height), hGap, vGap)

    init {
        require(gridSize.width > 0 && gridSize.height > 0) { "dimensions must be greater than zero" }
        this.gridSize = Dimension(gridSize)
        this.hGap = hGap
        this.vGap = vGap
        components = mutableMapOf()
    }

    constructor(width: Int, height: Int) : this(Dimension(width, height))

    override fun addLayoutComponent(name: String, comp: Component) {}

    private fun getLargestCellSize(parent: Container, isPreferred: Boolean): Dimension {
        val maxCellSize = Dimension(0, 0)
        for (component in parent.components) {
            if (component in components) {
                val rect = components[component]!!
                val componentSize = if (isPreferred) component.preferredSize else component.minimumSize
                maxCellSize.width = max(maxCellSize.width, componentSize.width / rect.width)
                maxCellSize.height = max(maxCellSize.height, componentSize.height / rect.height)
            }
        }
        return maxCellSize
    }

    override fun preferredLayoutSize(parent: Container): Dimension = getLayoutSize(parent, true)

    override fun minimumLayoutSize(parent: Container): Dimension = getLayoutSize(parent, false)

    override fun removeLayoutComponent(comp: Component) {
        components.remove(comp)
    }

    private fun getLayoutSize(parent: Container, isPreferred: Boolean): Dimension {
        val largestSize = getLargestCellSize(parent, isPreferred)
        val insets = parent.insets
        return Dimension(
            largestSize.width * gridSize.width + hGap * (gridSize.width + 1) + insets.left + insets.right,
            largestSize.height * gridSize.height + vGap * (gridSize.height + 1) + insets.top + insets.bottom
        )
    }

    override fun layoutContainer(parent: Container) {
        synchronized(parent.treeLock) {
            val insets = parent.insets
            if (parent.components.isEmpty()) return
            val size = parent.size
            val total = Dimension(size.width - (insets.left + insets.right), size.height - (insets.top + insets.bottom))
            val totalCell = Dimension(total.width / gridSize.width, total.height / gridSize.height)
            val cell = Dimension(
                (total.width - (gridSize.width + 1) * hGap) / gridSize.width,
                (total.height - (gridSize.height + 1) * vGap) / gridSize.height
            )
            for (component in parent.components) {
                if (component in components) {
                    val rect = components[component]!!
                    component.setBounds(
                        insets.left + totalCell.width * rect.x + hGap,
                        insets.top + totalCell.height * rect.y + vGap,
                        cell.width * rect.width - hGap,
                        cell.height * rect.height - vGap
                    )
                }
            }
        }
    }

    override fun addLayoutComponent(component: Component, constraints: Any) {
        require(constraints is Rectangle) { "cannot add to layout: constraint must be a Rectangle" }
        require(constraints.width > 0 && constraints.height > 0) { "cannot add to layout: rectangle must have positive width and height" }
        require(constraints.x >= 0 && constraints.y >= 0) { "cannot add to layout: rectangle x and y must be >= 0" }
        components[component] = Rectangle(constraints)
    }

    override fun maximumLayoutSize(target: Container): Dimension = Dimension(Int.MAX_VALUE, Int.MAX_VALUE)

    override fun getLayoutAlignmentX(target: Container): Float = H_CENTER

    override fun getLayoutAlignmentY(target: Container): Float = V_CENTER

    override fun invalidateLayout(target: Container): Unit = Unit

    fun getGridSize(): Dimension = Dimension(gridSize)

    fun setGridSize(d: Dimension) {
        gridSize = Dimension(d.width, d.height)
    }

    companion object {
        private const val H_CENTER = 0.5f
        private const val V_CENTER = 0.5f
    }
}
