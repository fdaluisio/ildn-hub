package net.ildn;

import java.util.ArrayList;

import net.ildn.fedorait.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ImageAdapter extends ArrayAdapter<ImageView> {

	private ArrayList<ImageView> items;
	private Context cx;
	private static final String LOG_ID = "ildn - ImageAdapter";
	
	public ImageAdapter(Context context, int textViewResourceId,
			ArrayList<ImageView> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.cx = context;
		Log.i(LOG_ID, " instanciated");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Log.i(LOG_ID, "executing getView");
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.cx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.imagerow, null);
			Log.i(LOG_ID, "view is null");
		}

		Log.i(LOG_ID, "processo posizione: " + position);
		ImageView o = items.get(position);
		if (o != null) {
			ImageView data = (ImageView) v.findViewById(R.id.imageView1);
			data.setImageDrawable(o.getDrawable());
		}
		return v;
	}
}