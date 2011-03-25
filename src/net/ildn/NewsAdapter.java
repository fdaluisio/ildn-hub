package net.ildn;

import java.util.ArrayList;

import net.ildn.fedorait.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<NewsItemRow> {

	private ArrayList<NewsItemRow> items;
	private Context cx;
	private String fonte;

	public NewsAdapter(Context context, int textViewResourceId,
			ArrayList<NewsItemRow> items, String fonte) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.cx = context;
		this.fonte=fonte;
		Log.i("NewsAdapter:", " instanciated and fonte: "+ fonte);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Log.i("NewsAdapter", "executing getView");
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.cx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.feednewsrow, null);
			Log.i("NewsAdapter", "view is null");
		}

		Log.i("NewsAdapter", "processo posizione: " + position);
		NewsItemRow o = items.get(position);
		if (o != null) {
			TextView creatore = (TextView) v.findViewById(R.id.newscreatore);
			TextView titolo = (TextView) v.findViewById(R.id.newstitolo);
			TextView data = (TextView) v
					.findViewById(R.id.newsdatapublicazione);
			TextView descrizione = (TextView) v
					.findViewById(R.id.newscontenuto);
			creatore.setText("Creato da:  " + o.getCreatore());
			data.setText("Pubblicato:  " + o.getDatapub());
			//meglio uno switch?			
			if (fonte.equalsIgnoreCase(cx.getString(R.string.intestazionefedora))) {
				titolo.setTextColor(cx.getResources().getColor(R.color.fedora));
			}
			else if (fonte.equalsIgnoreCase(cx.getString(R.string.intestazionedebian))) {
				titolo.setTextColor(cx.getResources().getColor(R.color.debian));
			}
			else if (fonte.equalsIgnoreCase(cx.getString(R.string.intestazionesuse))) {
				titolo.setTextColor(cx.getResources().getColor(R.color.suse));
			}
			else if (fonte.equalsIgnoreCase(cx.getString(R.string.intestazionemandriva))) {
				titolo.setTextColor(cx.getResources().getColor(R.color.mandriva));
			}
			else 
				titolo.setTextColor(cx.getResources().getColor(R.color.fedora));
			titolo.setText(o.getTitle());			
			descrizione.setText(o.getDescription());
			descrizione.setVisibility(View.GONE);
		}
		return v;
	}
}