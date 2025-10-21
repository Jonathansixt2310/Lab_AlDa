import java.util.Scanner;

public class mainSearch {
    public static void main(String[] args) {
        //Aufgabe 1a und 1b
        //Zufälliges Array
        int N = 100_000; //Anzahl der Elemente im Array
        int[] testArray = new int[N];
        for (int i = 0; i < N; i++) {
            testArray[i] = (int) (Math.random() * 10000); //Befüllt Array mit Zufallszahlen zwischen 0 und 9999
        }
        //Lineare Suche auf Array Ausführen
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bitte geben Sie eine Zahl zur Suche ein:");
        int zahl = scanner.nextInt();
        long startTime = System.nanoTime(); //Timer starten - Startzeit
        int result = search.linearSearch(testArray,zahl); //Methode aufrufen
        long endTime = System.nanoTime(); //Timmer stoppen - Endzeit
        long duration = endTime - startTime; //Laufzeit berechnen
        //Ausgabe
        System.out.println("Index: " + result);
        System.out.println("Startzeit: " + startTime);
        System.out.println("Endzeit: " + endTime);
        System.out.println("Laufzeit: " + duration + " Nanosekunden");
        System.out.println("Laufzeit: " + duration/1_000_000.0 + " Millisekunden");

        /*Teil 2a
        Fall 1: 1.4293 Millisekunden (N = 100.000)
        Fall 2: 1.5789 Millisekunden (N = 200.000)
        Fall 3: 1.7855 Millisekunden (N = 400.000)
        Fall 4: 2.4942 Millisekunden (N = 800.000)
        */

        /*Teil 2b
        Prognose
            linear steigend

        Erläutern Sie präzise, warum die lineare Suche im Worst Case als O(n) (lineare
        Laufzeit) klassifiziert wird:
            Im Worst Case ist das gesuchte Element nicht vorhanden und die for-Schleife muss N mal durchlaufen werden.
            Die Laufzeit steit also proportional/linear zur Array-Länge.
            Laufzeit also direkt von N abhängig

         Vergleichen Sie dieses empirische Ergebnis mit der formalen Big-O-Definition,
         die besagt, dass f(n)≤c⋅g(n) gilt, wobei g(n)=n.
            Hier g(n) = n --> lineares Wachstum
            Bedeutet: Die Laufzeit wächst höchstens linear mit der Eingabegröße
        */
    }
}
