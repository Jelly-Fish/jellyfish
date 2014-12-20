jellyfish
=========
Jellyfish project is a Java 1.7 middleware and user interface UCI chess engine driver and it's name is a friedly wink to stockfish that gave the motivation and (hmmhmm) inspiration to start jellyfish project ;)

Home page : http://jellyfishchess.org

Project IDE setup :
  - NetBeans recomended.
  - Stockfish chess engine : http://stockfishchess.org/  stockfish_x64 or stockfish_32bit .exe must be in <project-directory>/jellyfish/ directory. Constants for engine lauch can be modified or checked in ExternalEngineConst.java in jellyfish.constants package. If you wish to use a Linux or Mac OS Stockfish build extra setup will be requiered.

What does jellyfish do exactly ? The aim is to provide a middleware layer between UI and open source chess engines such as stockfish. It is written in Java. Basicly the jellyfish layer will supervise a chess game and validate chess moves both from UI and chess engine sides, also making IO easy and providing insight for UI side about just what exactly the engine is doing.
The purpose of jellyfish is also to help make chess interfaces that will offer every day players support for improving their skills and, above all, having fun playing chess.

We recomend stockfish chess engine : http://stockfishchess.org/<br>
Jellefish uses TinySound lib that can be found here on GitHub : https://github.com/finnkuusisto/TinySound

Under BSD3 license.
