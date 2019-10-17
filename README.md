# KF2-Easy-Server-Map-Adder
A simple program that grabs your subscribed steam workshop maps and outputs the lines you have to add to the server configs to get custom maps on your server

> * [How To Use](https://github.com/SethCohen/KF2-Easy-Server-Map-Adder#how-to-use)
> * [Screenshots](https://github.com/SethCohen/KF2-Easy-Server-Map-Adder#screenshots)
> * [Video Tutorial](https://youtu.be/maLiXN8EfG4)

## How To Use:

1. Download and run the exe (or jar)
2. Select the directory where the workshop maps are saved to (Typically found in **C:\Program Files (x86)\Steam\steamapps\workshop\content\232090** or **\Documents\My Games\KillingFloor2\KFGame\Cache**)
3. Press the **Confirm** button
4. Check the newly generated Output folder and open each .txt file
5. Press Ctrl+A to **Select All** and Ctrl+C to **Copy**
6. Paste the text from *ServerSubscribedWorkshopItems.txt* into **PCServer-KFEngine.ini** under the appropriate section
7. Paste the text from *WebAdmin.txt* into **PCServer-KFGame.ini** under the appropriate section
8. Paste the text from *GameMapCycles.txt* into **PCServer-KFGame.ini** at the end of the line "GameMapCycles=". **Dont forget to remove the last two "))" at the end of the line before pasting the new text in. Also make sure to re-add it at the end after pasting.**
9. Save everything
10. Run server
11. Let server install the maps (Will take a bit depending on how many maps you have installed. Be patient)

For additional help for anything past Step 4, follow the actual guide for adding custom maps to servers: https://wiki.killingfloor2.com/index.php?title=Dedicated_Server_(Killing_Floor_2)

## Screenshots:

![Screenshot1](https://i.imgur.com/gyyxMtP.png)
![Screenshot2](https://i.imgur.com/sRPrugG.png)
![Screenshot3](https://i.imgur.com/AWhoRFw.png)
![Screenshot4](https://i.imgur.com/ReBg7rE.png)
![Screenshot5](https://i.imgur.com/89ZI7mD.png)
![Screenshot6](https://i.imgur.com/eK5V4sy.png)
![Screenshot7](https://i.imgur.com/TXR7M2y.png)
