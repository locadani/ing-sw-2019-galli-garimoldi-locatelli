![Logo](http://www.spinmastergames.com/img/games/logo-p21321.png)

# Santorini Board Game 
## Components
* Galli Paolo
* Garimoldi Tommaso
* Locatelli Daniele

## Implemented functionalities
* Complete rules
* CLI
* GUI
* Socket
* 1 advanced funcitonality (Advanced gods)
  * Ares
  * Charon
  * Hera
  * Hestia
  * Limus
  
# Instructions

In order to properly display CLI colors the first operation is to insert a key in the system registry.
1. Open command prompt
2. Type regedit, press `Enter` key, then `Yes`
3. Right click the following folder `Computer\HKEY_CURRENT_USER\Console`
4. Choose `New -> DWORD value`
5. Call the key `VirtualTerminalLevel` and assign it value `1`, then press `OK`

In the `deliveries/final/jar` folder there are two JAR files to run Server and Client.
1. Download both files
2. Open command line and go to the folder that contains both files
3. Execute Server JAR on command line typing `java -jar Server.jar`
4. Execute `java -jar Client.jar` as many times as the number of players who want to take part in the match
5. For each client started you will be asked to choose between CLI or GUI, then game setup will start

# Match instructions

* How to perform an action in your match turn
1. Click the desired action (move, build, godpower)
2. Select the worker who has to perform the action
3. Select the cell in which the worker has to perform the action;
this last point can be done multiple times when you change your mind.
If you want to change action or worker who has to perform the action restart from point `1`
4. Click `Confirm`
5. To end your turn simply click end turn, then confirm <br></br>

* Use of `Godpower` button
When you need to use your divinity godpower with Atlas or Charon follow the instructions above 
choosing `Godpower` in point `1`
With every other divinity godpower is handled as `Move` or `Build` action
