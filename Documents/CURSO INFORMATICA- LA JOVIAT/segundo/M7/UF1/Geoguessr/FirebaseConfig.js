import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyAs7OMpyBSqm7Fmg7SxnMmqgottFcOsxMs",
  authDomain: "geoguessr-526f5.firebaseapp.com",
  projectId: "geoguessr-526f5",
  storageBucket: "geoguessr-526f5.appspot.com",
  messagingSenderId: "1066702915365",
  appId: "1:1066702915365:web:c7a5a5e31acfe1fdf37ccc",
  measurementId: "G-ENZ14NCN2V",
};

const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

export { db };
