import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import { NavigationContainer } from "@react-navigation/native";
import HomeScreen from "./Components/HomeScreen";
import GameScreen from "./Components/GameScreen";
import EndGameScreen from "./Components/EndGameScreen";

const Stack = createStackNavigator();

function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="GameScreen" component={GameScreen} />
        <Stack.Screen name="EndGameScreen" component={EndGameScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;
