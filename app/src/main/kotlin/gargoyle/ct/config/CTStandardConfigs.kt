package gargoyle.ct.config

import java.util.concurrent.TimeUnit

const val BLOCK_10M: Long = 10
const val BLOCK_20M: Long = 20
const val BLOCK_5M: Long = 5
const val WARN_3M: Long = 3
const val WHOLE_1H: Long = 60
const val WHOLE_2H: Long = 120
const val WHOLE_30M: Long = 30

object CTStandardConfigs : CTConfigs(
    CTConfig(TimeUnit.MINUTES, WHOLE_1H, BLOCK_10M, WARN_3M),
    CTConfig(TimeUnit.MINUTES, WHOLE_2H, BLOCK_20M, WARN_3M),
    CTConfig(TimeUnit.MINUTES, WHOLE_30M, BLOCK_5M, WARN_3M)
)
