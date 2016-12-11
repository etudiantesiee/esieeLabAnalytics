Demarrer Cassandra en mode analytique
-------------------------------------

1 - Si un installation de Cassandra a été demarré en mode non analytique : exécuter le script reset-cassandra-install-dir.sh (dans le répertoire doc)

2 - Si nécessaire, créer le keyspace et les tables associées en utilisant : playlist_comments.cql (src/main/scripts)

3 - Lancer cassandra avec la commande ./dse -k -f (-k pour le mode analytique)


Administrer l'exécution des JOB spark
-------------------------------------

Utiliser l'interface d'administration de spark : http://127.0.0.1:7080