@echo off
javac --module-path "C:\Program Files (x86)\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml,javafx.media src/elements/*.java
javac --module-path "C:\Program Files (x86)\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml,javafx.media src/*.java
javac --module-path "C:\Program Files (x86)\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml,javafx.media src/animation/*.java
java --module-path "C:\Program Files (x86)\Java\javafx-sdk-22\lib" --add-modules javafx.controls,javafx.fxml,javafx.media src.App