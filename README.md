# [Isometric Snake](https://github.com/codesad/isometric-snake)
An isometric 3D take on snake. Built in Java Swing.

## How to Build and Run
1. Make sure you have **Java 16** (or newer) **and Maven** installed.
2. Open a terminal, and navigate to the project's folder.
3. Run
    ```bash
    mvn clean package
    ```
4. After it finishes, you should see a `.jar` file, e.g.: 
    ```
    target/IsometricSnake-1.0.jar
    ```
5. Run the game using:
    ```bash
    java -jar target/IsometricSnake-1.0.jar
    ```
6. The main menu should then appear.
7. Make sure to read the in-game guide in order to understand the mechanics!

## Features to Test
- **Main menu navigation** works intuitively.
- **The guide**'s information matches gameplay behaviour.
- **Settings changes** persist across sessions.
- **Sounds effects** and **music** play and react to volume settings.
- **Gameplay logic:**
  - Snake grows on food.
  - Collisions with another part of the snake end the game.
  - The snake appears on the other side on the map when going into a side of the map.
- Resizing dynamically updates graphics.

## Credits
- Isometric tiles: [Pixel Isometric Tiles (itch.io)](https://scrabling.itch.io/pixel-isometric-tiles).
- Font: [Oxygene (dafont.com)](https://www.dafont.com/oxygene.font).
- Sound Effects:
  - Food collect sound by <a href="https://pixabay.com/users/driken5482-45721595/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=236671">Driken Stan</a> from <a href="https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=236671">Pixabay</a>
  - Game over sound by <a href="https://pixabay.com/users/freesound_community-46691455/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=6008">freesound_community</a> from <a href="https://pixabay.com/sound-effects//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=6008">Pixabay</a>
  - Movement sound by <a href="https://pixabay.com/users/dragon-studio-38165424/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=405450">DRAGON-STUDIO</a> from <a href="https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=405450">Pixabay</a>
  - [Menu sound 1 (Pixabay)](https://pixabay.com/sound-effects/8-bit-game-sfx-sound-8-269967/)
  - [Menu sound 2 (Pixabay)](https://pixabay.com/sound-effects/8-bit-game-sfx-sound-5-269974/)
  - [Menu sound 3 (Pixabay)](https://pixabay.com/sound-effects/8-bit-game-sfx-sound-2-269966/)
- Background music by <a href="https://pixabay.com/users/sonican-38947841/?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=334910">Dvir Silverstone</a> from <a href="https://pixabay.com//?utm_source=link-attribution&utm_medium=referral&utm_campaign=music&utm_content=334910">Pixabay</a>
