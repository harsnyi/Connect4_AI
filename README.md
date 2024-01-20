# BMEVIMIAC10 Mesterséges intelligencia 1. házi feladat

Ebben a házi feladatban a feladat egy ágens implementálása, amely képes egy másik ágenst legyőzni a Connect4 nevű játékban. A játék kétszemélyes, egy 6 ×7-es táblán játszódik. A játékosok felváltva ejtik bele a táblába a saját színüket. A győzelemhez 4 saját színt kell egy vonalban kirakni (függőlegesen/vízszintesen/átlósan). A játék, egy már megoldott játék, tehát annak jelenlegi állapotát ismerve a kimenete ismert, feltéve, hogy a játékosok tökéletesen játszanak. A tökéletes játékhoz azonban vagy előzetes számítások, vagy lépésenként sok idő szükséges. Ezért a MiniMax algoritmus mélységkorlátozott változatának használatát javasoljuk. A feladat maximális pontot érő megoldásához szükség lehet bizonyos extrák használatára, ilyenek többek között az α-β nyesés és a transzpozíciós tábla. Az ágens implementálható java és python nyelven is. A kiértékelés 3 lépésben történik, mindhárom esetben maximum 40 (java) ill. 50 (python) másodperc alatt kell lefutnia a programnak, és bele kell férnie 500 MB memóriába.

## Feladat

1. Játék egy mohó játékos ellen. (4 pont)
2.  Játék egy olyan játékos ellen, amelyik 3 mélységig járja be a keresési fát. (4 pont) 
3.  Játék egy olyan játékos ellen, amelyik 5 mélységig járja be a keresési fát. (4 pont)
