package net.ildn;

import java.util.ArrayList;
import net.ildn.fedorait.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OtherActivity extends GlobalMenu {

	private String fonte = "nothing";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent launchingIntent = getIntent();
		// Imposto sorgente del contenuto web
		fonte = launchingIntent.getExtras().getString("fonte");

		Log.i(fonte + " - OtherActivity", "Richiamato onCreate()");
		setContentView(R.layout.other);

		ArrayList<String> o_list = new ArrayList<String>();
		o_list.add(this.getString(R.string.intestazionedebian));
		o_list.add(this.getString(R.string.intestazionefedora));
		o_list.add(this.getString(R.string.intestazionesuse));
		o_list.add(this.getString(R.string.intestazionemandriva));
		o_list.add(this.getString(R.string.intestazionemageia));
		// aggiungere qui gli altri siti del network

		o_list.trimToSize();
		Log.i(fonte + " - OtherActivity",
				"Dimensione o_list dopo: " + o_list.size());
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				o_list));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Log.i(fonte + " - NewsActivity", "Click " + id + " in lista posizione "
				+ position);
		switch (position) {
		case 0:
			// Controllo per ovviare di andare su una fonte quando già ci sono
			if (!this.getString(R.string.intestazionedebian).contains(fonte)) {

				finish();
				startActivity(new Intent("net.ildn.debianitalia.DebianItalia"));
			} else
				Toast.makeText(
						this,
						"Sei già su "
								+ this.getString(R.string.intestazionedebian),
						Toast.LENGTH_SHORT).show();
			return;
		case 1:
			// Controllo per ovviare di andare su una fonte quando già ci sono
			if (!this.getString(R.string.intestazionefedora).contains(fonte)) {
				finish();
				startActivity(new Intent("net.ildn.fedorait.fedorait"));
			} else
				Toast.makeText(
						this,
						"Sei già su "
								+ this.getString(R.string.intestazionefedora),
						Toast.LENGTH_SHORT).show();
			return;
		case 2:
			// Controllo per ovviare di andare su una fonte quando già ci sono
			if (!this.getString(R.string.intestazionesuse).contains(fonte)) {
				finish();
				startActivity(new Intent("net.ildn.suseitalia.SuseItalia"));
			} else
				Toast.makeText(
						this,
						"Sei già su "
								+ this.getString(R.string.intestazionesuse),
						Toast.LENGTH_SHORT).show();
			return;
		case 3:
			// Controllo per ovviare di andare su una fonte quando già ci sono
			if (!this.getString(R.string.intestazionemandriva).contains(fonte)) {
				finish();
				startActivity(new Intent(
						"net.ildn.mandrivaitalia.MandrivaItalia"));
			} else
				Toast.makeText(
						this,
						"Sei già su "
								+ this.getString(R.string.intestazionemandriva),
						Toast.LENGTH_SHORT).show();
			return;
		case 4:
			// Controllo per ovviare di andare su una fonte quando già ci sono
			if (!this.getString(R.string.intestazionemageia).contains(fonte)) {
				finish();
				startActivity(new Intent(getString(R.string.portalemageia)));
			} else
				Toast.makeText(
						this,
						"Sei già su "
								+ this.getString(R.string.intestazionemageia),
						Toast.LENGTH_SHORT).show();
			return;
		default:
			return;
		}

	}

}
