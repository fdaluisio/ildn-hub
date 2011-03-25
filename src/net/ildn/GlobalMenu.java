package net.ildn;

import java.util.List;

import net.ildn.fedorait.R;
import net.ildn.feed.Message;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GlobalMenu extends ListActivity {

	private boolean flash;
	private boolean js;
	private String portaledefault;
	private String scelta;
	private String sharedresource;

	protected List<Message> messages;
	protected NewsAdapter n_adapter;
	protected ProgressDialog progressDialog;
	private static int MSG_REFRESH = 0;

	private static final String LOG_ID = "Ildn - GlobalMenu";

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.arg1 == MSG_REFRESH)
				refreshContent();
		}
	};

	/**
	 * @return the sharedresource
	 */
	public String getSharedresource() {
		return sharedresource;
	}

	/**
	 * @param sharedresource
	 *            the sharedresource to set
	 */
	public void setSharedresource(String sharedresource) {
		this.sharedresource = sharedresource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionmenu, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.id.menuinfo:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Credits");
			builder.setMessage(R.string.creditsildn);
			builder.setCancelable(true);
			AlertDialog alert = builder.create();
			alert.show();
			return true;

		case R.id.portaleswitch:
			AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
			builder3.setTitle(getString(R.string.portaledipartenza));
			builder3.setCancelable(false);
			setSharedresource(this.getString(R.string.ildnPreference));
			SharedPreferences settings3 = getSharedPreferences(sharedresource,
					0);
			portaledefault = settings3.getString("portaledefault",
					this.getString(R.string.intestazionefedora));
			final String[] portalidisponibili = {
					this.getString(R.string.intestazionedebian),
					this.getString(R.string.intestazionefedora),
					this.getString(R.string.intestazionesuse),
					this.getString(R.string.intestazionemandriva) };
			scelta = portaledefault;
			builder3.setSingleChoiceItems(portalidisponibili, -1,
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Log.i(LOG_ID, "onClickListener onClick "
									+ portalidisponibili[which]
									+ " as been selected");
							scelta = portalidisponibili[which];
						}
					});

			builder3.setPositiveButton("Ok", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(LOG_ID,
							"PositiveButton OnClickListener button pressed");
					SharedPreferences settings = getSharedPreferences(
							sharedresource, 0);
					SharedPreferences.Editor editor = settings.edit();

					/*
					 * Salvo le preferenze
					 */
					editor.putString("portaledefault", scelta);
					editor.commit();
				}
			});

			builder3.setNegativeButton("Annulla", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(LOG_ID,
							"NegativeButton OnClickListener button pressed");
					// non faccio nulla
				}
			});

			AlertDialog alert3 = builder3.create();
			alert3.show();
			return true;

		case R.id.pluginswitch:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle(getString(R.string.sceglicosaabilitare));
			builder2.setCancelable(false);

			/*
			 * Loading SharedPreferences per i plugin
			 */
			setSharedresource(this.getString(R.string.ildnPreference));
			SharedPreferences settings = getSharedPreferences(sharedresource, 0);
			flash = settings.getBoolean("flash", false);
			js = settings.getBoolean("js", true);

			final String[] plugins = { this.getString(R.string.flash),
					this.getString(R.string.js) };
			final boolean[] values = { flash, js };
			builder2.setMultiChoiceItems(plugins, values,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							Log.i(LOG_ID, "onOptionsItemSelected onClick "
									+ plugins[which] + " as selected as "
									+ isChecked);

							switch (which) {
							case 0:
								// Contenuto Flash
								values[which] = isChecked;

							case 1:
								// Contenuto Javascript
								values[which] = isChecked;

							default:
								;
							}
						}
					});

			builder2.setPositiveButton("Ok", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(LOG_ID,
							"PositiveButton OnClickListener button pressed");
					SharedPreferences settings = getSharedPreferences(
							sharedresource, 0);
					SharedPreferences.Editor editor = settings.edit();

					/*
					 * Salvo le preferenze
					 */
					editor.putBoolean("flash", values[0]);
					editor.putBoolean("js", values[1]);
					editor.commit();
				}
			});

			builder2.setNegativeButton("Annulla", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i(LOG_ID,
							"NegativeButton OnClickListener button pressed");
					SharedPreferences settings = getSharedPreferences(
							sharedresource, 0);
					values[0] = settings.getBoolean("flash", false);
					values[1] = settings.getBoolean("flash", true);
				}
			});

			AlertDialog alert2 = builder2.create();
			alert2.show();

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showProgressDialog() {
		progressDialog = ProgressDialog.show(this, "",
				getString(R.string.loading), true);
	}

	public void hideProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void updateContents(List<Message> messages) {
		this.messages = messages;
		android.os.Message progressMsg = new android.os.Message();
		progressMsg.arg1 = MSG_REFRESH;
		handler.sendMessage(progressMsg);
	}

	private void refreshContent() {
		for (Message msg : messages) {
			NewsItemRow i = new NewsItemRow();
			i.setCreatore(msg.getCreator());
			i.setDatapub(msg.getDate());
			i.setTitle(msg.getTitle());
			i.setDescription(msg.getDescription());
			n_adapter.add(i);
		}
		n_adapter.notifyDataSetChanged();
		hideProgressDialog();
	}

}
