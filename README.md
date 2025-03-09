🕹️ Minesweeper - Programming III Project

📌 About the Project

This project consists of the development of a graphical application of the Minesweeper game as part of the Programming III course in the Computer Engineering - Networks and Telecommunications degree. The main objective is to consolidate knowledge acquired throughout the semester by applying object-oriented programming (OOP) concepts such as inheritance, polymorphism, and event-driven programming, as well as utilizing data structures and Java Streams API.

🎮 Game Description

Minesweeper is a classic puzzle game where the player must uncover a grid of NxM cells, avoiding hidden mines. The game was developed using a predefined class hierarchy, including:

GameMinesweeper: Manages game logic, board state, and win/lose conditions.

MinesweeperFrame: Implements the graphical interface and user interactions.

Cell subclasses: Represent individual board cells with different properties.

The board is managed as a 2D matrix of Cell objects, where each cell can be revealed, flagged, or contain a mine. The game tracks the player’s progress and determines win/lose conditions dynamically.

🚀 Features

✅ Fully functional Minesweeper game with different difficulty levels✅ Graphical interface with an interactive grid✅ Timer to track the player's time✅ Win/Lose conditions with real-time updates✅ Leaderboard system that saves top scores✅ Event-driven programming using Java listeners✅ Data persistence to store player records

📂 Project Structure

📦 Minesweeper Project
├── src/
│   ├── GameMinesweeper.java  // Game logic
│   ├── MinesweeperFrame.java // GUI implementation
│   ├── Cell.java             // Base class for board cells
│   ├── Square.java           // Graphical representation of cells
│   ├── PlayerRecord.java     // Player records and score tracking
│   ├── LeaderBoard.java      // Leaderboard system with file storage
│   ├── GameListener.java     // Interface for event notifications
│   ├── MouseListener.java    // Handles user interactions
│   └── Timer.java            // Manages in-game timer
└── resources/
    ├── leaderboard.txt       // Stored player records
    └── icons/                // UI assets

🛠️ How to Run Locally

If you want to run or modify the game, follow these steps:

#Clone the repository

git clone https://github.com/YOUR_GITHUB_USERNAME/Minesweeper.git  

#Navigate to the project directory

cd Minesweeper  

#Compile the Java files

javac src/*.java  

#Run the game

java src.MinesweeperFrame  

#📬 Contact

For any questions or collaborations, feel free to reach out:📧 Email: joao.evaristo.work@gmail.com🔗 LinkedIn: João Evaristo🐙 GitHub: joaoevaristo11

💡 If you liked this project, consider giving it a ⭐ on GitHub!
