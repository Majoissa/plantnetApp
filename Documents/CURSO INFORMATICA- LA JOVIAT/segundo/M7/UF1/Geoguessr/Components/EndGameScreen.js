import React from "react";
import { View, Text } from "react-native";

const EndGameScreen = ({ route }) => {
  const { correctGuesses } = route.params;

  return (
    <View style={{ flex: 1, alignItems: "center", justifyContent: "center" }}>
      <Text>Cantidad de aciertos: {correctGuesses}</Text>
    </View>
  );
};

export default EndGameScreen;
