jellyfish
=========
Jellefishchess project is a Java UCI middleware for driving chess engines and UI's for playing chess. It's name is a friedly wink to Stockfish chess engine that gave the motivation and (hmmhmm) inspiration to start Jellefishchess project ;)

2D ui developpement is complete. 3D OpenGL user interface is currently being developed but is not yet linked up to the chess engine. To switch from 2D or 3D UI see main method in Starter.java class. Librairies used for OpenGL are LWJGL 2.9.1 : http://legacy.lwjgl.org/ 

Project IDE setup :
  - NetBeans recomended.
  - Stockfish chess engine : http://stockfishchess.org/  stockfish_x64 or stockfish_32bit .exe must be in project-directory/jfgjellyfishchess/ directory. Constants for engine lauch can be modified or checked in ExternalEngineConst.java in fr.com.jellyfish.jfgjellyfishchess.jellyfishchess.jellyfish.constants package. If you wish to use a Linux or Mac OS Stockfish build then extra setup will be requiered.

Recomended chess engine : Stockfish http://stockfishchess.org/<br>
Jellefishchess uses TinySound lib that can be found here on GitHub : https://github.com/finnkuusisto/TinySound

Under BSD3 license.
