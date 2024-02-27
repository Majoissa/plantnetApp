import React, { useState, useRef, useEffect } from "react";
import { View, Button, Text, StyleSheet, Dimensions } from "react-native";
import MapView, { Marker, Polyline } from "react-native-maps";
import { Crosshair } from "react-native-feather";
import { db } from "../FirebaseConfig";
import { collection, getDocs } from "firebase/firestore";
import { useNavigation } from "@react-navigation/native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "flex-start",
    alignItems: "center",
    marginTop: 50,
  },
  map: {
    width: Dimensions.get("window").width * 0.8,
    height: Dimensions.get("window").height * 0.5,
    marginVertical: 20,
    borderColor: "green",
    borderWidth: 5,
  },
  crosshair: {
    position: "absolute",
    top: "37%",
    alignSelf: "center",
  },
  buttonContainer: {
    marginVertical: 20,
  },
  navigationButtons: {
    flexDirection: "row",
    justifyContent: "space-around",
    width: "100%",
    marginTop: 10,
  },
  distanceText: {
    fontSize: 16,
    marginVertical: 8,
    marginHorizontal: 15,
    fontWeight: "bold",
  },
  correctGuessesText: {
    fontSize: 18,
    fontWeight: "bold",
    marginVertical: 8,
  },
  questionText: {
    fontSize: 22,
    fontWeight: "bold",
    marginVertical: 8,
  },
});

const GameScreen = () => {
  const [capitalList, setCapitalList] = useState([]);
  const [currentCapital, setCurrentCapital] = useState(null);
  const [correctGuesses, setCorrectGuesses] = useState(0);
  const [showAnswer, setShowAnswer] = useState(false);
  const [markerPosition, setMarkerPosition] = useState(null);
  const [distance, setDistance] = useState(null);
  const [polylineCoordinates, setPolylineCoordinates] = useState([]);
  const mapRef = useRef(null);
  const navigation = useNavigation();

  useEffect(() => {
    const fetchCapitalsData = async () => {
      try {
        const querySnapshot = await getDocs(collection(db, "Capitales"));
        const capitalsData = [];
        querySnapshot.forEach((doc) => {
          capitalsData.push({ id: doc.id, ...doc.data() });
        });
        setCapitalList(capitalsData);
        selectRandomCapital(capitalsData);
      } catch (error) {
        console.error("Error al obtener los datos de las capitales:", error);
      }
    };

    fetchCapitalsData();
  }, []);

  const selectRandomCapital = (capitals) => {
    if (capitals.length > 0) {
      const randomIndex = Math.floor(Math.random() * capitals.length);
      setCurrentCapital(capitals[randomIndex]);
    }
  };

  // Manejador de 'Next' para mostrar la siguiente capital
  const handleNextPress = () => {
    selectRandomCapital(capitalList);
    setShowAnswer(false); // Oculta la respuesta y espera por una nueva selección
    setDistance(null);
    setMarkerPosition(null);
    setPolylineCoordinates([]);
  };

  const handleFinishPress = () => {
    navigation.navigate("EndGameScreen", { correctGuesses: correctGuesses });
  };

  const calculateDistance = (point1, point2) => {
    if (!point1 || !point2) {
      return 0;
    }
    const radiusOfEarth = 6371;
    const lat1 = deg2rad(point1.latitude);
    const lon1 = deg2rad(point1.longitude);
    const lat2 = deg2rad(point2.latitude);
    const lon2 = deg2rad(point2.longitude);

    const dLat = lat2 - lat1;
    const dLon = lon2 - lon1;

    const a =
      Math.sin(dLat / 2) ** 2 +
      Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return radiusOfEarth * c;
  };

  const deg2rad = (degree) => {
    return degree * (Math.PI / 180);
  };

  const handleCheckAnswerPress = () => {
    if (currentCapital && markerPosition) {
      const newDistance = calculateDistance(
        {
          latitude: currentCapital.lat,
          longitude: currentCapital.lon,
        },
        markerPosition
      );
      setDistance(newDistance);
      setShowAnswer(true);
      if (newDistance <= 3) {
        setCorrectGuesses(correctGuesses + 1);
      }
    }
  };

  const handleMapPress = (e) => {
    setMarkerPosition(e.nativeEvent.coordinate);
    if (currentCapital) {
      setPolylineCoordinates([
        e.nativeEvent.coordinate,
        { latitude: currentCapital.lat, longitude: currentCapital.lon },
      ]);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.questionText}>
        ¿Cuál es la capital de {currentCapital?.pais}?
      </Text>
      <MapView
        ref={mapRef}
        style={styles.map}
        initialRegion={{
          latitude: currentCapital?.lat || 0,
          longitude: currentCapital?.lon || 0,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
        onPress={handleMapPress}
      >
        {markerPosition && <Marker coordinate={markerPosition} />}
        {showAnswer && currentCapital && (
          <Marker
            coordinate={{
              latitude: currentCapital.lat,
              longitude: currentCapital.lon,
            }}
            pinColor="green"
          />
        )}
        {polylineCoordinates.length === 2 && (
          <Polyline
            coordinates={polylineCoordinates}
            strokeWidth={2}
            strokeColor="red"
          />
        )}
      </MapView>
      <Crosshair style={styles.crosshair} width={40} height={40} />
      <View style={styles.navigationButtons}>
        <Button title="Next" onPress={handleNextPress} />
        <Button title="Finish" onPress={handleFinishPress} />
      </View>
      <View style={styles.buttonContainer}>
        <Button title="Comprobar respuesta" onPress={handleCheckAnswerPress} />
        {showAnswer && distance !== null && (
          <Text style={styles.distanceText}>
            Distancia entre el punto marcado y la respuesta correcta:{" "}
            {distance.toFixed(2)} km
          </Text>
        )}
      </View>
    </View>
  );
};
export default GameScreen;
