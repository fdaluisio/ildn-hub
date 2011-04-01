package net.ildn;

import java.util.ArrayList;

import net.ildn.fedorait.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

public class ListaPortali extends GlobalMenu {

	private static final String LOG_ID = "ildn - ListaPortali";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listaportali);
		ArrayList<ImageView> listaPortali = new ArrayList<ImageView>();
		
		final int portalidisponibili[] = {
				R.drawable.debianlogo,
				R.drawable.fedoralogo,
				R.drawable.suselogo,
				R.drawable.mandrivalogo
		};
		
		for (int i=0; i<portalidisponibili.length; i++) {	
        	ImageView iv = new ImageView(this);
        	iv.setImageResource(portalidisponibili[i]);
        	listaPortali.add(iv);
		}
		ImageAdapter p_adapter = new ImageAdapter(this, R.id.imageView1, listaPortali);
		setListAdapter(p_adapter);

	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i(LOG_ID, "Click item in lista posizione:"+ position );
		
		Intent mainIntent;
		switch (position) {
		case 0:
			mainIntent = new Intent(getString(R.string.portaledebian));
			break;
		case 1:
			mainIntent = new Intent(getString(R.string.portalefedora));
			break;
		case 2:
			mainIntent = new Intent(getString(R.string.portalesuse));
			break;
		case 3:
			mainIntent = new Intent(getString(R.string.portalemandriva));
			break;
		default:
			mainIntent = new Intent(getString(R.string.portalefedora));
			break;
		}
		startActivity(mainIntent);
		finish();
	}

}
