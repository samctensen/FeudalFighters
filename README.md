# Feudal Fighters

A tower-defense game built with Java and Swing.

Originally developed in December 2020 over roughly two weeks before Christmas break. Uploaded to GitHub in February 2021; later commits added documentation and a demo.

## Run

With a JDK installed, run these commands from the repository root:

```sh
mkdir -p out
javac -d out src/a9/*.java
java -cp out a9.TheGreatBattle
```

The game opens a Swing window. Run it from the repository root so its image paths resolve. Compilation has been checked with Java 25.

## Project

- `src/a9/` — game actors, combat, and Swing UI.
- `src/a9/Icons/` — original game artwork.
- [Demo video](Demo.mp4).

The source and artwork are preserved from the original course project.
