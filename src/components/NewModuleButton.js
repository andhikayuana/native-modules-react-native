import React, { useEffect } from 'react'
import {
    View,
    Button,
    NativeEventEmitter,
    NativeModules
} from 'react-native'
import CalendarModule from "../CalendarModule";

const NewModuleButton = () => {

    useEffect(() => {
        // Anything in here is fired on component mount.
        const eventEmitter = new NativeEventEmitter(NativeModules.ToastExample)
        this.eventListener = eventEmitter.addListener('ReminderCounterEvent', (event) => {
            //listen data from native here
            console.log(event)
        });
        return () => {
            // Anything in here is fired on component unmount.
            this.eventListener.remove()
        }
    }, [])

    const onReminderStart = () => {
        console.log("onReminderStart")
        CalendarModule.reminderStart()
    }

    const onReminderStop = () => {
        console.log("onReminderStop")
        CalendarModule.reminderStop()
    }

    const onPress = async () => {
        //call the native method
        CalendarModule.createCalendarEvent('testName', 'testLocation')

        //to trigger error, please set empty event name
        CalendarModule.createCalendarEventWithCallback(
            'testName',
            'testLocation',
            (eventId) => {
                console.log(`Event created with id: ${eventId}`)
            },
            (errorMessage) => {
                console.log(errorMessage)
            }
        )

        try {
            //to trigger error, please set empty event name
            const eventId = await CalendarModule.createCalendarEventWithPromise(
                'testName',
                'testLocation'
            )

            console.log(`created new event with id: ${eventId}`)
        } catch (error) {
            console.log(error)
        }

        //get constant from native
        const { DEFAULT_EVENT_NAME } = CalendarModule.getConstants()
        console.log(DEFAULT_EVENT_NAME)
    }

    return (
        <View>
            <Button
                title="Click to invoke your native module!"
                color="#841584"
                onPress={onPress}
            />
            <Button
                title='Reminder Start'
                onPress={onReminderStart}
            />
            <Button
                title='Reminder Stop'
                onPress={onReminderStop}
            />
        </View>

    )
}

export default NewModuleButton;