// Importation de la classe ArrayList pour stocker des listes de données
import java.util.ArrayList;

// Définition de la classe principale KNN
public class KNN {
    // pointSet stocke l'ensemble de points utilisé pour la recherche KNN
    private PointSet pointSet;
    // k est le nombre de voisins à rechercher dans l'algorithme KNN
    private int k;

    // Constructeur de la classe KNN
    public KNN(PointSet pointSet) {
        this.pointSet = pointSet;
    }

    // Méthode pour définir la valeur de k
    public void setK(int k) {
        this.k = k;
    }

    // Méthode pour trouver les k plus proches voisins d'un point donné
    public ArrayList<LabelledPoint> findNN(LabelledPoint queryPoint, int version) {
        // Récupération de la liste des points de l'ensemble
        ArrayList<LabelledPoint> points = pointSet.getPointsList();
        // Création d'une file de priorité pour stocker les voisins potentiels
        PriorityQueueIF<LabelledPoint> priorityQueue = new PriorityQueue1<>(k);

        // Vérification si la liste de points est vide ou si k est négatif
        if (points.isEmpty() || k <= 0) {
            return new ArrayList<>(); // Retourne une liste vide si aucune recherche n'est nécessaire
        }

        // Calcul des distances entre le point de requête et chaque point de l'ensemble
        for (LabelledPoint p : points) {
            double distance = queryPoint.distanceTo(p);
            p.setKey(distance); // Stocke la distance comme clé pour chaque point
        }

        // Ajout des points à la file de priorité tout en maintenant k voisins les plus proches
        for (LabelledPoint p : points) {
            priorityQueue.offer(p);
            if (priorityQueue.size() > k) {
                priorityQueue.poll(); // Retire le point le plus éloigné si la file est pleine
            }
        }

        // Récupération des k plus proches voisins à partir de la file de priorité
        ArrayList<LabelledPoint> neighbors = new ArrayList<>();
        while (!priorityQueue.isEmpty()) {
            neighbors.add(priorityQueue.poll());
        }
        return neighbors; // Retourne la liste des k plus proches voisins
    }

    // Méthode principale, point d'entrée du programme
    public static void main(String[] args) {
        // Vérification du nombre d'arguments en ligne de commande
        if (args.length != 4) {
            System.err.println("Usage: java KNN <numéro de version> <k> <nom du fichier de jeu de données> <nom du fichier de points de requête>");
            System.exit(1);
        }

        // Récupération des arguments en ligne de commande
        int version = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        String datasetFilename = args[2];
        String queryFilename = args[3];

        // Création de l'ensemble de points à partir du fichier de jeu de données
        PointSet pointSet = new PointSet(PointSet.read_ANN_SIFT(datasetFilename));

        // Création de l'objet KNN avec l'ensemble de points
        KNN knn = new KNN(pointSet);

        // Définition de la valeur de k
        knn.setK(k);

        long startTime = System.currentTimeMillis(); // Mesure du temps de début de l'exécution

        // Création de l'ensemble de points de requête à partir du fichier de points de requête
        PointSet querySet = new PointSet(PointSet.read_ANN_SIFT(queryFilename));

        // Boucle pour trouver les k plus proches voisins pour chaque point de requête
        for (int i = 0; i < querySet.getPointsList().size(); i++) {
            LabelledPoint queryPoint = querySet.getPointsList().get(i);
            ArrayList<LabelledPoint> neighbors = knn.findNN(queryPoint, version);

            // Affichage des résultats
            System.out.print(queryPoint.getLabel() + " : ");
            for (int j = 0; j < neighbors.size(); j++) {
                System.out.print(neighbors.get(j).getLabel() + ", ");
            }
            System.out.println();
        }
        long endTime = System.currentTimeMillis(); // Mesure du temps de fin de l'exécution
        System.out.println("Temps d'exécution total : " + (endTime - startTime) + " millisecondes");
    }
}
