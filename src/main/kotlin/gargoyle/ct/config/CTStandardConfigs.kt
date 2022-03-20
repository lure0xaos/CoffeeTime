package gargoyle.ct.config

import java.io.Serial

object CTStandardConfigs : CTConfigs() {
    const val BLOCK_10M: Long = 10
    const val BLOCK_20M: Long = 20
    const val BLOCK_5M: Long = 5
    const val WARN_3M: Long = 3
    const val WHOLE_1H: Long = 60
    const val WHOLE_2H: Long = 120
    const val WHOLE_30M: Long = 30

    @Serial
    private val serialVersionUID = -2778482225848242583L
}
