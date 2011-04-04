package net.ildn;

import java.util.List;

import net.ildn.fedorait.R;
import net.ildn.feed.Message;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class GlobalMenu extends ListActivity {

	private boolean flash;
	private boolean js;
	private String ildnuser;
	private String ildnpasswd;
	private String portalelogin;
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

	public String getSharedresource() {
		return sharedresource;
	}

	public void setSharedresource(String sharedresource) {
		this.sharedresource = sharedresource;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionmenu, menu);
		return true;
	}

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

		case R.id.pluginswitch:
			AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
			builder2.setTitle(getString(R.string.sceglicosaabilitare));
			builder2.setCancelable(true);

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
			
		case R.id.credentialswitch:
			AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
			builder4.setTitle(getString(R.string.credentialswitch));
			builder4.setCancelable(true);

			LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.alert_dialog_user_pass, null);
            builder4.setView(textEntryView);  
            
            // Inizializzazioni oggetti della grafica
            final TextView testousername = (TextView)textEntryView.findViewById(R.id.username_view);
            final TextView testopassword = (TextView)textEntryView.findViewById(R.id.password_view);
            final EditText tvname = (EditText)textEntryView.findViewById(R.id.username_edit);
            final EditText tvpass = (EditText)textEntryView.findViewById(R.id.password_edit);
            final Spinner  mSpinner = (Spinner)textEntryView.findViewById(R.id.spinnerportali);
            
            // Inizializzazioni delle preferenze
			SharedPreferences settings4 = getSharedPreferences(getString(R.string.ildnPreference),MODE_PRIVATE);
			portalelogin = settings4.getString("portalelogin","nessuno");
			final UserCredential uc = new UserCredential(this);
			
			final String portalidisponibili1[] = {
					this.getString(R.string.intestazionedebian),
					this.getString(R.string.intestazionefedora),
					this.getString(R.string.intestazionesuse),
					this.getString(R.string.intestazionemandriva),
					"nessuno"};
            
			// Inizializzazioni contenuto degli oggetti della grafica
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, portalidisponibili1); 
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setPrompt(getString(R.string.portaleswitch));
            mSpinner.setAdapter(adapter);
            int k=4; //portaledefault=nessuno
            for (int i=0; i<portalidisponibili1.length; i++) {
            	Log.i(LOG_ID, "portale disponibile: " + portalidisponibili1[i]);
            	if (portalidisponibili1[i].equalsIgnoreCase(portalelogin)) {
            		k=i;
            		Log.i(LOG_ID, "portale trovato: " + portalidisponibili1[i]+ " in posizione k: "+k);
            		break;
            	}
            }
            mSpinner.setSelection(k);
            
            mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                	//selectedItemView.
                    Log.i(LOG_ID, "selectedItemView is "+selectedItemView);
                    String st = (String)mSpinner.getAdapter().getItem(position);
                    if (st!=null && !st.equalsIgnoreCase("nessuno")) {
                    	Log.i(LOG_ID, "elemento diverso da nessuno: "+st);
                    	testousername.setVisibility(View.VISIBLE);
                    	testopassword.setVisibility(View.VISIBLE);
                    	tvname.setVisibility(View.VISIBLE);
                    	
                    	tvname.setFocusable(true);
                    	tvname.setFocusableInTouchMode(true);
                    	
                    	boolean result = tvname.requestFocus();
                    	Log.i(LOG_ID, "username requestFocusFromTouch: " + result);
                    	
                    	tvname.setText(uc.getPrefs("ildnuser"));
                    	tvname.setFocusable(true);
                    	tvpass.setVisibility(View.VISIBLE);
                    	tvpass.setFocusable(true);
                    	tvpass.setFocusableInTouchMode(true);
                    	tvpass.setText(uc.getPrefs("ildnpasswd"));
                    	/*
                    	mgr.showSoftInput(tvpass, InputMethodManager.SHOW_FORCED);
						*/
                    }
                    else {
                    	Log.i(LOG_ID, "elemento uguale a nessuno");
                    	testousername.setVisibility(View.GONE);
                    	testopassword.setVisibility(View.GONE);
                    	tvname.setText("");
                    	tvname.setFocusable(false);
                    	tvpass.setText("");
                    	tvpass.setFocusable(false);
                    	tvname.setVisibility(View.GONE);
                    	tvpass.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    ;
                }

            });
			
            /*	
			 * Loading SharedPreferences for ILDN username/password
			 */            
            
            ildnuser=uc.getPrefs("ildnuser");
            ildnpasswd=uc.getPrefs("ildnpasswd");
            tvname.setText(ildnuser);
            tvpass.setText(ildnpasswd);             
            
            /*
             * Setting Ok botton
             */
			builder4.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
					@Override
                    public void onClick(DialogInterface dialog, int whichButton) {
						
						setSharedresource(getString(R.string.ildnPreference));
						SharedPreferences settings = getSharedPreferences(sharedresource, 0);
    					SharedPreferences.Editor editor = settings.edit();
    					
						Log.i(LOG_ID,"PositiveButton OnClickListener button pressed");
						
						final Spinner  mSpinner = (Spinner)textEntryView.findViewById(R.id.spinnerportali);
						scelta = mSpinner.getSelectedItem().toString();
						Log.i(LOG_ID,"Portale di default:"+scelta);

    					if (scelta.equalsIgnoreCase("nessuno")) {
    						//Clean dell'Account Manager
    						uc.setPrefs("ildnuser", "");
    						uc.setPrefs("ildnpasswd", "");
    						editor.putString("portalelogin",  "nessuno");
        					editor.putString("portaledefault",  "nessuno");
    						Log.i(LOG_ID, "clean dell'Account Manager ");
    					}
    					else {
    						editor.putString("portalelogin",  scelta);
        					editor.putString("portaledefault",  scelta);
    						uc.setPrefs("ildnuser", tvname.getText().toString());
    						uc.setPrefs("ildnpasswd", tvpass.getText().toString());
    					}

    					/*
    					 * Salvo la preferenze portalelogin/portaledefault
    					 */
    					
    					boolean val = editor.commit();
    					Log.i(LOG_ID, "return commit: "+ val);
    					
    					/* 
    					 * Start Activity for default portal
    					 */
    					Intent mainIntent = null;
    					if (scelta
    							.equalsIgnoreCase(getString(R.string.intestazionedebian))) {
    						mainIntent = new Intent(getString(R.string.portaledebian));
    					} else if (scelta
    							.equalsIgnoreCase(getString(R.string.intestazionefedora))) {
    						mainIntent = new Intent(getString(R.string.portalefedora));
    					} else if (scelta
    							.equalsIgnoreCase(getString(R.string.intestazionesuse))) {
    						mainIntent = new Intent(getString(R.string.portalesuse));
    					} else if (scelta
    						.equalsIgnoreCase(getString(R.string.intestazionesuse))) {
    						mainIntent = new Intent(getString(R.string.portalemandriva));
						} else if (scelta
							.equalsIgnoreCase("nessuno")) {
							mainIntent = null;
						}
    					
						if (mainIntent != null) { 
							startActivity(mainIntent);
							finish();
						}
                       
                    }
			});

			/*
			 * Setting Annulla botton
			 */
            builder4.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            	
            		@Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked cancel so do some stuff */
    					Log.i(LOG_ID,
						"NegativeButton OnClickListener button pressed");
                    }
            });
			
			AlertDialog alert4 = builder4.create();
			alert4.show();
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
