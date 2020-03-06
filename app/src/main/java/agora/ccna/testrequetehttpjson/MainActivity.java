package agora.ccna.testrequetehttpjson;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
/*
Exemple d'une application android qui met en oeuvre une requête http (GET ou POST au choix) afin de récupérer un
texte au format JSON représentant les caractéristiques d'unétudiant 'nom, prénom, password, mail, établissement, classe, groupe)
Une fois la réponse JSON arrivée, l'application affiche dans un textView la chaine brute au format JSON
et dans un autre TextView, affiche les membres d'un objet Etudiant (voir la classe jointe) créé à partir du décodage du texte JSON.

A titre d'exemple, le script coté serveur est fourni ci dessous: tstJson.php
	<?php
	//simulation d'une requete dans une bdd en fonction d'un login nom/password
	//qui retourne les caratctéristiques d'un étudiant inscrit dans établissement scolaire
    //Le résultat sera une chaine json : {"nom": lenom , "prenom" : leprenom , "mail" : lemail ,
    //                "caracteristiqueEtudiant" : {"etablissement" : etablissament , "classe": nomclasse , "groupe" : legroupe}}

   if($_SERVER["REQUEST_METHOD"] == "GET") {
	  $nom = $_GET['nom'];
	  $password = $_GET['password'];
      echo '{"nom": '.$nom.' , "prenom" : "simplet" , "password" : '.$password.' , "mail" : "simplet@septnains.org" , "caracteristiqueEtudiant" : {"etablissement" : "LT Agora" , "classe": "BTS2" , "groupe" : "GR1"}}';
   }
    if($_SERVER["REQUEST_METHOD"] == "POST") {
   	  $nom = $_POST['nom'];
   	  $password = $_POST['password'];
      echo '{"nom": '.$nom.' , "prenom" : "simplet" , "password" : '.$password.' , "mail" : "simplet@septnains.org" , "caracteristiqueEtudiant" : {"etablissement" : "LT Agora" , "classe": "BTS2" , "groupe" : "GR1"}}';
      }
   ?>
 */
public class MainActivity extends AppCompatActivity {

    //déclaration des widgets pour le traitement des parametres et résultats (json brut et objets
    TextView tvjson, tvJsonDecode , tvurl;
    EditText ednom , edpass, edurl;
    ToggleButton tgb;
    Button bgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //instancition des widgets
        tvjson = findViewById(R.id.tvjson);
        tvJsonDecode = findViewById(R.id.tvjsonobjet);
        tvurl = findViewById(R.id.tvurl);
        ednom = findViewById(R.id.ednom);
        edpass = findViewById(R.id.edpass);
        edurl = findViewById(R.id.edurl);
        tgb = findViewById(R.id.get_post);
        bgo = findViewById(R.id.bgo);
        //gestionnaire click sur bouton => envoi de la requete au serveur
        //avec test sur la méthode GET ou POST
        bgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //création du client http et déclenchement de la requête en fonction de la méthode chooisie GET ou POST
                clientHttp cli = new clientHttp(tvjson , tvJsonDecode);
                if(!tgb.isChecked())
                    cli.execute("GET" , edurl.getText().toString() , ednom.getText().toString() , edpass.getText().toString());
                else
                    cli.execute("POST" , edurl.getText().toString() , ednom.getText().toString() , edpass.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
