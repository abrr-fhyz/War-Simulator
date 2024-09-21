# Bangladesh Liberation War Simulator

This application simulates the Bangladesh Liberation War, focusing on the conventional warfare that took place from the start of the war to its conclusion. The simulation includes data regarding the positions and movements of military units from Bangladesh, Pakistan, and India, along with information about their respective commanders.

## Key Points:
- **Authenticity of Unit Positions and Troop Movements**: 
  The simulator displays all known positions of Pakistani, Bangladeshi, and Indian brigades, as well as Division HQs, along with the names of their commanders. The names and ranks of the commanders are updated to the next rounded month. The troop movement schematic was developed based on the positions of troops in March, May, August, October, November, and December 3rd. The approximate position of the brigades on the map represents their real positions to +/- 30 km. There may be inaccuracies in the change of command due to inconsistencies between different sources. The simulation focused on the active military brigade, smaller subunits like battalions or regiments are not shown, while larger units like Corps are not explicitly mentioned.

- **Indian Invasion Movements**: 
  The movement of Indian forces between December 3rd and 16th is based on the best available approximations due to limited data. Furthermore, data on operations conducted by the Indian Armed Forces come explicitly from Indian sources, making it harder to discern the actual situation at the time from propaganda.

- **Sector Commands & Guerilla Warfare**: 
  This simulation is based on conventional WW2-style warfare models. However, due to the nature of guerilla warfare, there were limitations in the portrayal of sector commands. Sector command units appear at locations where fighting occurred at a given time and do not necessarily represent the presence of the sector HQ at that position. These units represent engagements rather than fixed command HQs. Non-uniformed forces such as the Mujib, Kaderia, Afsar, Hemayet, Baten and Akbar Bahinis could not be presented as they are outside the scope of this simulation, but their areas of operation are marked as contested within the map. 

- **General Officers**:
  The commanding lieutenant generals of the four Indian corps or frontlines are only labeled as GOC, Corps Commander/Eastern Command. The specific commanders of each corps are not identified, as the simulation primarily focuses on the brigade level. The chief commanding officer for each side is considered to be the highest-ranked officer in the Eastern theater of the war, which is why General Yahya Khan and Field Marshal (then General) Sam Manekshaw are not mentioned.

- **Map**: 
  The map in the simulation shows the major highways and road networks in East Pakistan at the time. The map may contain inaccuracies as it was processed via a Python script that analyzed three historical maps, which were then edited. Specifically, the path of the Brahmaputra River may be subject to interpretation.

- **Reading Save Files**: 
  Run the `Reader.java` file for a detailed console view of the commanders, dates, and units involved, along with their allegiances, for any given save file.

- **Sources**:
  The major sources utilized for this simulation are available on the ```Sources.txt``` file.

## Running the Application:
- Edit the `./build` and `./run` files for fast compilation and build using the direct address of JavaFX libraries, or use Maven/Gradle. If not using Maven/Gradle, follow the steps below:
- To **build** the simulator, run:
  ```bash
  ./build
  ```
- To **run** the simulator, execute:
  ```bash
  ./run
  ```
