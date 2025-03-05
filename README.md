# planets

Programmierprojekt Informatik Q2

- [planets](#planets)
    - [Projektbeschreibung](#projektbeschreibung)
    - [Geplante Vorgehensweise](#geplante-vorgehensweise)
        - [Planeten als Objekte](#planeten-als-objekte)
        - [Physikalische und Geometrische Berechnung](#physikalische-und-geometrische-berechnung)
    - [Dokumentation der einzelnen Arbeitsschritte](#dokumentation-der-einzelnen-arbeitsschritte)
        - [Grundlage: Bewegliche Planeten zeichnen](#grundlage-bewegliche-planeten-zeichnen)
        - [Kraftvektoren in eigener Datenstruktur speichern](#kraftvektoren-in-eigener-datenstruktur-speichern)
    - [Reflexion des Programmierprozesses](#reflexion-des-programmierprozesses)
    - [Fazit](#fazit)

## Projektbeschreibung
Mit planets soll die Wirkung der Gravitationskraft
zwischen Körpern (z.B. Planeten) simuliert werden.
Ein Planet wird dabei vereinfacht als Punkt angenommen,
der sich auf einer Ebene bewegen kann.

Die Darstellung der Simulation soll mithilfe der
Klasse 
[StdDraw](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html)
erfolgen.



## Geplante Vorgehensweise

### Planeten als Objekte
Ein Planet soll in planets als Objekt der Klasse `planet` angelegt werden.
Die wichtigsten Attribute der Klasse sind
`position`, `velocity` und `mass` als grundlegende physikalische
Größen der Simulation.
Die Klasse planet benötigt u.a. Methoden um den Planeten zu zeichnen, um
die insgesamt auf ihn wirkende Gravitationskraft zu berechnen sowie
um daraus Beschleunigung, Geschwindigkeit und letztlich Position zu berechnen.

Des weiteren verfügt die Klasse `planet` über ein Attribut `next`, das
auf den nächsten Planeten verweist. So entsteht ein dynamischer Datentyp,
in dem jedes Objekt vom Typ `planet` auf die nachfolgenden Planeten zugreifen
kann.
Vorteil dieser Herangehensweise ist, dass während die Simulation läuft Planeten
hinzugefügt oder gelöscht werden können und keine Maximalanzahl von
Planeten festgelegt werden muss.

### Physikalische und Geometrische Berechnung

Die Position und Geschwindigkeit eines Planeten sowie die
Kräfte zwischen ihnen sind zweidimensionale Vektoren.
Sie werden je in einem double-Array der Länge zwei gespeichert.

Physikalische Grundlage der Simulation ist das
Gravitationsgesetz, mit dem die Gravitationskraft zwischen
zwei Planeten berechnet werden kann:

```math
F = G \cdot \frac{m_1 \cdot m_2}{r^2}
```

Dabei ist
$G = 6,6743 \cdot 10^{-11} \frac{\mathrm{m}^3}{\mathrm{kg} \cdot \mathrm{s}}$
die Gravitationskonstante, $m_1$ und $m_2$ sind die Massen der beiden Planeten und
$r$ ist der Abstand zwischen beiden.

Allerdings wird die Gravitationskraft
als Vektor benötigt. $\overrightarrow{F}$ ist der Vektor mit dem 
Betrag $F$, der vom ersten der Planeten ($p_1$)
in Richtung des zweiten ($p_2$) zeigt.
Um $\overrightarrow{F}$ zu bestimmen, wird erst der Verbindungsvektor
$\overrightarrow{p_1p_2}$ berechnet. Mit einem Skalar $s$ multipliziert
ist dieser $\overrightarrow{F}$.
Für $s$ muss gelten: 

```math
\begin{aligned}

s \cdot |\overrightarrow{p_1p_2}| = F \\
s \cdot \sqrt{(x_{p1p2})^2 + (y_{p1p2})^2} = F \\
s = \frac{F}{\sqrt{(x_{p1p2})^2 + (y_{p1p2})^2}}

\end{aligned}
```

Um aus einem Zustand der Simulation den nächsten zu berechnen,
muss für alle Kombinationen von
zwei Planeten die Gravitationskraft berechnet werden.
Dann kann für jeden Planeten
durch vektorielles Addieren der einzelnen Kräfte eine Gesamtkraft
auf ihn bestimmt werden.

Mit dem zweiten Newtonschem Gesetz
```math
\overrightarrow{F} = m \cdot \overrightarrow{a}
```
kann dann die
die Beschleunigung des Planeten bestimmt werden.
Diese wird mit `simulationTimeMultiplier` multipliziert und
auf seine Geschwindigkeit addiert. Dann wird auch die
Geschwindigkeit mit `simulationTimeMultiplier` multipliziert
und auf die Position addiert. Jetzt kann der Planet an
seiner neuen Position gezeichnet werden.

## Dokumentation der einzelnen Arbeitsschritte

### Grundlage: Bewegliche Planeten zeichnen
Zuerst habe ich mich mit der Benutzung vom
[StdDraw](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html)
beschäftigt und eine mitgelieferte Beispielzeichnung getestet.
Dann habe ich mit der Implementierung der Klasse `planet` begonnen.
Zuerst wurde dafür die grundlegende Funktion der
Methoden `reDraw()` und `recalculate()` sowie
die Methode `addPlanet()` implementiert.
Die Methode `reDraw()` wird rekursiv für alle Planeten aufgerufen.
So können bereits mehrere Planeten angelegt und
ihre Bewegungen gezeichnet werden, allerdings
noch ohne gegenseitige Beeinflussung durch Gravitation.
Das heißt, mit dieser Implementierung fliegen die Planeten
noch geradeaus aus dem Bild heraus.

```java
public void reDraw(){
    StdDraw.circle(position[0], position[1], mass * 0.01);
    recalculate();

    if (next != null) {
        next.reDraw();
    }
}
private void recalculate(){
    position[0] += velocity[0];
    position[1] += velocity[1];
    }
```

### Kraftvektoren in eigener Datenstruktur speichern
Im nächsten Schritt musste also die Berechnung der Gravitationskräfte
zwischen den Planeten implementiert werden.
Die [physikalischen und geometrischen Berechnungen](#physikalische-und-geometrische-berechnung)
werden mit den Methoden `gravityBetweenPlanets()` und `calcAcceleration()` sowie
`connectingVector()` und `vectorLength()` durchgeführt.

Für jeden Planeten muss die Gravitation zu jedem anderen berechnet und dann zu
einer Gesamtkraft aufaddiert werden. Allerdings kann ein Planet
mit dem Attribut `next` nur auf die Planeten nach ihm zugreifen.
Um dieses Problem zu lösen war die erste Idee, die Datenstruktur
der Planeten zu einer Art Ring zu verbinden, in dem das Attribut
`next` des letzten Planeten wieder auf den ersten Planeten verweist.
So könnte vom aktuellen Planeten aus die Kraft zu allen darauffolgenden
Planeten berechnet werden bis man wieder beim aktuellen Planeten
(erkennbar an der `planetId`) ankommt. Dann würde der nächste Planet zum
aktuellen Planeten und das gleiche Prinzip könnte rekursiv auf alle Planeten
angewendet werden.

Diese Vorgehen hätte aber der Nachteil, dass jede einzelne Gravitationskraft
doppelt berechnet werden würde
(als $\overrightarrow{F_{p1p2}}$ und $\overrightarrow{F_{p2p1}}$).
Um das zu umgehen, werden die bereits berechneten Kraftvektoren
in einer eigenen Datenstruktur gespeichert.
Diese Struktur besteht aus Objekten der Klasse `forceStructure`.
Eines dieser Objekte hat drei Attribute: `value` speichert
einen Kraftvektor und `nextCol` bzw. `nextRow` verweisen auf
das nächste Objekt in der aktuellen Zeile bzw.
das erste Objekt der nächsten Zeile. In einer Zeile sind
alle Kräfte eines Planeten zu den Planeten, auf
die er mit `next` zugreifen kann (Planeten nach ihm)
gespeichert.

![forceStructure Diagramm](./images/forceStructure.drawio.svg)

## Reflexion des Programmierprozesses
Einige Schritte des Projektes wie zum Beispiel die
Berechnungen mit Vektoren oder die Datenstruktur für
die Gravitationskräfte zwischen den Planeten könnten
mit Verwendung fertiger Java-Funktionen und Bibliotheken
einfacher und eleganter gelöst werden.
Allerdings ist es Teil des Projekts, für diese Probleme
eine eigene Lösung zu finden.
Sehr hilfreich ist dagegen die vorgegebene Klasse 
[StdDraw](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html),
die die Darstellung der Simulation deutlich vereinfacht.

Um die Bedienung der Simulation zu vereinfachen wäre als nächster
Schritt die Implementierung eines Kommandozeileninterface
nötig. Damit könnten über einfache Befehle während der Simulation
Planeten hinzugefügt und gelöscht werden und möglicherweise bestimmte
Größen wie die Kraft auf einen bestimmten Planeten ausgegeben werden.
Um sich mit einem Befehl auf einen Planeten beziehen zu können,
müsste die `planetId` neben die Planeten gezeichnet werden.

Auch andere grafische Darstellungsmöglichkeiten
für die Simulation wären interessant.
Beispielsweise könnten auch die Bahnen der Planeten oder Kraftvektoren
angezeigt werden.

Während des Programmierprojekts habe ich nicht nur einiges
über die Programmierung in Java gelernt, sondern mich
auch erstmals mit git und GitHub beschäftigt.
Das hat es deutlich einfacher gemacht, sowohl in der
Schule als auch Zuhause am Projekt zu arbeiten.


## Fazit
Die Simulation der Gravitation mit planets funktioniert gut.
Es ist sehr spannend, zu beobachten, wie die Planeten sich
gegenseitig beeinflussen und sich ihre Bewegung verändert
(etwa im Beispiel, das von der `mainClass` standardmäßig
dargestellt wird).
Allerdings braucht es etwas Experimentierfreude um eine Kombination an
Ausgangsbedingungen zu finden, in der die die Planeten nicht
sofort auseinanderfliegen.



