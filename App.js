/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react'
import { View, Text, Button, NativeModules } from "react-native"
import NewModuleButton from "./src/components/NewModuleButton";

export default function App() {

  const onPress = () => {
    console.log("Pressed!")
  }

  return (
    <View>
      <Text>
        Hello
      </Text>
      <Button title='Press Me!' onPress={onPress}></Button>
      <NewModuleButton></NewModuleButton>
    </View>
  )
}