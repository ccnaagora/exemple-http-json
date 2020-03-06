package agora.ccna.testrequetehttpjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/*
La requête HTTP pour invoquer un script php est asynchrone
 et doit interagir avec les composants graphiques de l'activité principale.
 C'est pourquoi elle est implémentée dans une classe de type "thread" ou tâche Asynchrone

 les paramètres template sont< String , Void , String >:
        =>  String      url, nom, password, méthode
        =>  void         inutilisé ici
        =>  String      un texte json reçu du serveur qui sera affiché dans un TextView (passé en paramètre au constructeur de la classe
 */

//déclaration de la classe asynchrone
public class clientHttp extends AsyncTask <String , Void, String>{

    String url = null;
    //un textview de l'activité principale qui sera rempli avec la réponse du serveur: le json
    //le second TextView affichera  un objet Etudiant (voir classe) issu du JSON décodé
    TextView json , jsonDecode;
    clientHttp(TextView js , TextView jsdeco ){
        super();
        json = js;
        jsonDecode = jsdeco;
    }
    //exécutée lors de l'appel de "execute(String ... ) depuis l'activité principale
    //lors de l'appel depuis l'activité principale,
    // afin de récuperer les paramètres dans le bon ordre, il faut les passer dans l'ordre suivant:
    //              méthode, url , nom , password
    //      ex :    clienthttp.execute("GET" , "http://127.0.0.1/monprojet/testJson.php" , "Simplet" , "xd-*89QR");
    @Override
    protected String doInBackground(String... x) {
        Log.i("ADAP" , "doInBackground : " + x[0].toString() );
        //test de la méthode pour compléter l'url (GET) ou ajouter les données à la fin (POST)
        if(x[0].toUpperCase().compareTo("GET") == 0){
            String r = doGet(x);
            return r;
        }
        if(x[0].toUpperCase().compareTo("POST") == 0){
            String r = doPost(x);
            return r;
        }
        return "";
    }
    @Override
    protected void onProgressUpdate(Void... i){
        super.onProgressUpdate(i);
    }
    //exécutée à la fin de la méthode doItBackground
    //r est la réponse du serveur format json
    @Override
    protected void onPostExecute(String rJson){
        super.onPostExecute(rJson);
        //on affecte au textView la réponse Json
        json.setText(rJson);
        Etudiant e = new Etudiant();
        e.creerEtudiantDepuisJSON(rJson);
        jsonDecode.setText(e.toString());
    }
    //***************
    //méthode qui invoque le script en GET et retourne la chaine au format Json reçue
    public String  doGet(String[] param) {

        //concaténation de l'url complète
        String data = "nom=" + param[2] ;
        data += "&password=";
        data += param[3];
        String url = "http://" + param[1] ;
        url += ("?" + data);
        Log.i("ADAP", "url=" + url);
        URL obj;
        try {
            //création de l'Url
            obj = new URL(url);
            //ouverture de la connexion
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            Log.i("ADAP", "Apres connection");
            con.setRequestMethod("GET");
            //création de l'entête http de la requête : A voir
            con.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
            con.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");
            con.setRequestProperty("Upgrade-Insecure-Requests", "1");
            con.setReadTimeout(15*1000);

            Log.i("ADAP", "sortie envoie requete");
            //réception du code de retour du serveur
            //200 si OK (protocole htpp
            int responseCode = con.getResponseCode();
            Log.i("ADAP", "Rep code=" + responseCode);
            //lecture du flux issu du socket
            InputStream in = con.getInputStream();
            byte[] b = new byte[1024];
            int nb = in.read(b);
            String reponse = new String(b , 0 , nb);
            return reponse;
        } catch (  MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.i("ADAP", "exception malformed: " + e.getMessage());
            e.printStackTrace();
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            Log.i("ADAP", "exception generale: " + e.getMessage());
            e.printStackTrace();
        }
        //on retourne une chaine vide si une erreur est survenue
        return "";
    }
    //traitement methode POST
    public String  doPost(String[] param) {

        //concaténation de l'url complète
        String data = "nom=" + param[2] ;
        data += "&password=";
        data += param[3];
        //data += "\r\n\r\n";
        String url = "http://" + param[1] ;
        Log.i("ADAP", "url=" + url);
        URL obj;
        try {
            //création de l'Url
            obj = new URL(url);
            //ouverture de la connexion
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            Log.i("ADAP", "Apres connection");
            //POST
            con.setRequestMethod("POST");
            con.setReadTimeout(15*1000);
            //nécessaire pour envoyer les 2 \r\n puis les données nom=ffff&password=fffff
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(data.getBytes());
            Log.i("ADAP", "POST: " + data + "   taille" + data.length());
            //réception du code de retour du serveur
            //200 si OK (protocole htpp
            int responseCode = con.getResponseCode();
            Log.i("ADAP", "Rep code=" + responseCode);
            //lecture du flux issu du socket
            InputStream in = con.getInputStream();
            Log.i("ADAP", "get input stream");
            byte[] b = new byte[1024];
            int nb = in.read(b);
            String reponse = new String(b , 0 , nb);
            Log.i("ADAP", "lecture in nb = " +nb  + "   rep=" + reponse);
            return reponse;
        } catch (  MalformedURLException e) {
            // TODO Auto-generated catch block
            Log.i("ADAP", "exception malformed: " + e.getMessage());
            e.printStackTrace();
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            Log.i("ADAP", "exception generale: " + e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}

