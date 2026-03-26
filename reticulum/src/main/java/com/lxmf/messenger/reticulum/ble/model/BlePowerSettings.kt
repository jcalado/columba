package com.lxmf.messenger.reticulum.ble.model

data class BlePowerSettings(
    val preset: BlePowerPreset = BlePowerPreset.BALANCED,
    val discoveryIntervalMs: Long = 15000L,
    val discoveryIntervalIdleMs: Long = 45000L,
    val scanDurationMs: Long = 8000L,
    val advertisingRefreshIntervalMs: Long = 120_000L,
)

enum class BlePowerPreset {
    PERFORMANCE,
    BALANCED,
    BATTERY_SAVER,
    CUSTOM,
    ;

    companion object {
        // Parameters: discoveryIntervalMs, discoveryIntervalIdleMs, scanDurationMs, advertisingRefreshIntervalMs
        // discoveryIntervalMs is the TOTAL cycle time (scan + gap), not just the gap.
        // Scan duration must be shorter than discovery interval to allow radio rest.
        fun getSettings(preset: BlePowerPreset): BlePowerSettings =
            when (preset) {
                PERFORMANCE -> BlePowerSettings(PERFORMANCE, 8000L, 20000L, 5000L, 60_000L)
                BALANCED -> BlePowerSettings(BALANCED, 15000L, 45000L, 8000L, 120_000L)
                BATTERY_SAVER -> BlePowerSettings(BATTERY_SAVER, 30000L, 120000L, 5000L, 180_000L)
                CUSTOM -> BlePowerSettings(CUSTOM) // Fallback defaults; configurePower() supplies real values
            }

        fun fromString(name: String): BlePowerPreset =
            try {
                valueOf(name.uppercase())
            } catch (
                @Suppress("SwallowedException") e: IllegalArgumentException,
            ) {
                BALANCED // Unknown preset name — fall back to balanced
            }
    }
}
