# exemple-http-json
Exemple d'une application android qui met en oeuvre une requête http (GET ou POST au choix) afin de récupérer un
texte au format JSON représentant les caractéristiques d'un étudiant (nom, prénom, password, mail, établissement, classe, groupe)

Une fois la réponse JSON arrivée, l'application affiche dans un textView la chaine brute au format JSON
et dans un autre TextView, affiche les membres d'un objet Etudiant (voir la classe jointe) créé à partir du décodage du texte JSON.

A titre d'exemple, le script coté serveur est fourni en commentaires dans MainActivity.java
