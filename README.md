# Projet - Maio

Recreation of the original Super Mario Bros (from the NES) in Kotlin.

Author :
- Augustin MERCIER

This game uses the KorGE library for rendering graphics. Documentation is available here : https://docs.korge.org/korge/

The `game` folder contains the game itself. It is a Gradle project, and can be opened with IntelliJ IDEA or used only with the command line (all commands are available below).
The `sprites` folder contains all the source files for the sprites. They are in the form of `.ase` files, which can be opened with Aseprite (https://www.aseprite.org/).
The hierarchy of the sprites contained in the `sprites` folder should be the same as the hierarchy of the `game/src/commonMain/resources/sprites` folder, which contains the rendered sprites (in the form of `.png` files). These sprites are then compiled to be upscaled to the correct size for the game using the `compileImages.py` script (see below).

## Web version

A web version should be playable at this address : http://test.maio.fr/

## Launching the project

First place yourself in the `game` folder :
```bash
cd game
```

### Compiling images
This operation lets you scale the sprites to the correct size for the game.
It requires Python and the (Pillow)[https://pillow.readthedocs.io/en/stable/] library.
Until the images are not modified, this step is not necessary (the images are already compiled).
```bash
pip install pillow
python3 src/compileImages.py
```

### Launching the game
```bash
./gradlew runJvm
```
