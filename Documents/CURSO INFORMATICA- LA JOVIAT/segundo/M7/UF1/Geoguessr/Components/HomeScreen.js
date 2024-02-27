import React from "react";
import { View, Button, Text, StyleSheet } from "react-native";
import { useNavigation } from "@react-navigation/native";

const HomeScreen = () => {
  const navigation = useNavigation();

  return (
    <View style={styles.container}>
      <Text style={styles.welcomeText}>Bienvenido al Juego</Text>
      <Button
        title="Empezar Partida"
        onPress={() => navigation.navigate("GameScreen")}
        color="blue"
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#fff",
  },
  welcomeText: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
  },
  // Puedes añadir más estilos si lo necesitas
});

export default HomeScreen;
