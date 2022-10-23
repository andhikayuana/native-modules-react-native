package com.nativemodulesreactnativedemo.modules.calendar

import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.util.*

class CalendarModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    private val reminderCounter by lazy {
        ReminderCounter(
            onTick = {
                Log.d("CalendarModule", "[NATIVE] onTick $it")
                sendEvent(reactContext, ReminderCounter.EVENT_NAME, Arguments.createMap().apply {
                    putString("millisUntilFinished", it.toString())
                })
            },
            onFinish = {
                Log.d("CalendarModule", "[NATIVE] onFinish")
                sendEvent(reactContext, ReminderCounter.EVENT_NAME, Arguments.createMap().apply {
                    putString("millisUntilFinished", "0")
                })
            }
        )
    }

    override fun getName(): String = "CalendarModule"

    override fun getConstants(): MutableMap<String, Any> = hashMapOf(
        "DEFAULT_EVENT_NAME" to "New Event"
    )

    private fun sendEvent(reactContext: ReactContext, eventName: String, params: WritableMap?) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    override fun initialize() {
        super.initialize()
        Log.d("CalendarModule", "INITIALIZE")
    }

    @ReactMethod
    fun reminderStart() {
        // Set up any upstream listeners or background tasks as necessary
        reminderCounter.start()
    }

    @ReactMethod
    fun reminderStop() {
        // Remove upstream listeners, stop unnecessary background tasks
        reminderCounter.cancel()
    }

    @ReactMethod
    fun createCalendarEvent(
        name: String,
        location: String
    ) {
        Log.d(
            "CalendarModule",
            "[NATIVE] Create event called with name: $name and location: $location"
        )
    }

    @ReactMethod
    fun createCalendarEventWithCallback(
        name: String,
        location: String,
        success: Callback,
        failure: Callback
    ) {
        Log.d(
            "CalendarModule",
            "[NATIVE] Create event called with name: $name and location: $location"
        )
        val eventId = UUID.randomUUID().toString()
        if (name.isEmpty()) {
            failure.invoke("event name is required!")
        } else {
            success.invoke(eventId)
        }
    }

    @ReactMethod
    fun createCalendarEventWithPromise(
        name: String,
        location: String,
        promise: Promise
    ) {
        try {
            Log.d(
                "CalendarModule",
                "[NATIVE] Create event called with name: $name and location: $location"
            )
            val eventId = UUID.randomUUID().toString()
            if (name.isEmpty().not()) {
                promise.resolve(eventId)
            } else {
                throw RuntimeException("event name is required!")
            }
        } catch (t: Throwable) {
            promise.reject("create event error", t)
        }
    }
}