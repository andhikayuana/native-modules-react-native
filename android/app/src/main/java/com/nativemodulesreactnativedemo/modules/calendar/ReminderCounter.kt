package com.nativemodulesreactnativedemo.modules.calendar

import android.os.CountDownTimer

class ReminderCounter(
    private val onTick: (millisUntilFinished: Long) -> Unit,
    private val onFinish: () -> Unit
) : CountDownTimer(MILLISINFUTURE, COUNTDOWN_INTERVAL) {

    companion object {
        const val MILLISINFUTURE: Long = 30000
        const val COUNTDOWN_INTERVAL: Long = 1000

        const val EVENT_NAME = "ReminderCounterEvent"
    }

    override fun onTick(millisUntilFinished: Long) = onTick.invoke(millisUntilFinished)

    override fun onFinish(): Unit = onFinish.invoke()
}