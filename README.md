# planets

- [planets](#planets)
    - [Projektbeschreibung](#projektbeschreibung)
    - [Geplante Vorgehensweise](#geplante-vorgehensweise)
        - [Darstellung mit StdDraw](#darstellung-mit-stddraw)
        - [Planeten als Objekte](#planeten-als-objekte)
        - [Physikalische und Geometrische Berechnung](#physikalische-und-geometrische-berechnung)
    - [Dokumentation der einzelnen Arbeitsschritte](#dokumentation-der-einzelnen-arbeitsschritte)
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

### Darstellung mit [StdDraw](https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html)

### Planeten als Objekte

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
$\overrightarrow{p_1p_2}$ berechnet. Mit einem Skalar s multipliz
Also muss gelten: $|s \cdot \overrightarrow{p_1p_2}| = F$

Um aus einem Zustand der Simulation den nächsten zu berechnen,
muss für alle Kombinationen von
zwei Planeten die Gravitationskraft berechnet werden.
Dann kann für jeden Planeten
durch vektorielles Addieren der einzelnen Kräfte eine Gesamtkraft
auf ihn bestimmt werden.

Mit dem zweiten Newtonschem Gesetz $ \overrightarrow{F} = m \cdot \overrightarrow{a} $ kann dann die
die Beschleunigung des Planeten bestimmt werden.
Diese wird auf seine Geschwindigkeit addiert (eine neue Berechnung
der Simulation entspricht einer Zeiteinheit). Dann wird die
Geschwindigkeit auf die Position addiert, jetzt kann der Planet an
seiner neuen Position gezeichnet werden.

## Dokumentation der einzelnen Arbeitsschritte


## Reflexion des Programmierprozesses


## Fazit

