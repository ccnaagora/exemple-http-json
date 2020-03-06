package agora.ccna.testrequetehttpjson;
//import org.apache.*;
import org.json.JSONException;
import org.json.JSONObject;

/*
Cette classe Etudiant est un exemple montrant comment, à partir d'une chaine JSON, décoder la chaine
puis fabriquer et exploiter un objet JAVA construit à partir du JSON
 */
public class Etudiant {
    //déclarations des membres représentant un objet "Etudiant"
    private String nom;
    private String password;
    private String prenom;
    private String mail;
    private String etablissement;
    private String classe;
    private String groupe;

    //constructeur : ne fait rien car l'affectation des membres se fera via une chaine JSON
    //mais il est nécessaire pour créer un objet "vide"
    public Etudiant(){

    }
    //méthode qui crée un Etudiant à partir d'un JSON passé en paramètre. (elle pourrait être statique et retourner un Etudiant
    //la chaine JSON est de la forme:
    //  {"nom": '.$nom.' , "prenom" : "simplet" , "password" : "xd-*89QR" , "mail" : "simplet@septnains.org" , "caracteristiqueEtudiant" : {"etablissement" : "LT Agora" , "classe": "BTS2" , "groupe" : "GR1"}}';
    //
    public void creerEtudiantDepuisJSON(String chaine){
        try {
            //extraction de l'objet JSON racine : sommet de l'arbre
            JSONObject racine = new JSONObject(chaine);
            //extraction et affectation des champs à partir de la racine
            nom = racine.getString("nom");
            prenom = racine.getString("prenom");
            password = racine.getString("password");
            mail = racine.getString("mail");
            //extraction de l'objet JSON caracteristiqueEtudiant afin de pouvoir extraire ensuite les objets qu'il contient
            //extarction et affectation des champs depuis l'objet caracteristiqueEtudiant
            JSONObject carac = racine.getJSONObject("caracteristiqueEtudiant");
            etablissement = carac.getString("etablissement");
            classe = carac.getString("classe");
            groupe = carac.getString("groupe");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //méthode toString afin de vérifier et afficher l'objet décodé JSON => Etudiant
    @Override
    public String toString(){
        String r="";
        r = String.format("Nom:\t\t\t\t%s\nPrenom:\t\t%s\nPassword:\t%s\nMail:\t\t\t\t%s\nLycée:\t\t\t\t%s\t%s\t%s" , getNom(),prenom,password,mail,etablissement,classe,groupe);
        return r;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }



}
