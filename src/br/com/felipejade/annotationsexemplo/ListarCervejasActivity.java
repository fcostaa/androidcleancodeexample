package br.com.felipejade.annotationsexemplo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import br.com.felipejade.annotationsexemplo.adapters.ListaCervejasAdapter;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockListActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.lista_cervejas)
public class ListarCervejasActivity extends SherlockListActivity {
	
	private static final String TAG = ListarCervejasActivity.class.getSimpleName();
	
	private ArrayList<Cerveja> cervejas;
	
	@ViewById(android.R.id.list)
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setTitle("Listar Cervejas");
	}
	
	@AfterViews
	@Background
	protected void baixarCervejas() {
		Log.i(TAG, "baixar cervejas: " + Thread.currentThread().getId());
		
		String json = null;
		
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL("http://api.openbeerdatabase.com/v1/beers.json");
			urlConnection = (HttpURLConnection) url.openConnection();
			
			json = readStream(urlConnection.getInputStream());
			
		} catch(IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Erro ao baixar lista de cervejas");
		} finally {
			
			if(urlConnection != null)
				urlConnection.disconnect();
		}
		
		parsearJSON(json);
	}
	
	
	private String readStream(InputStream in) {
		Log.i(TAG, "threadID em readStream: " + Thread.currentThread().getId());
		
		BufferedReader reader = null;
		StringBuffer strBuffer = new StringBuffer();
		
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				strBuffer.append(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return strBuffer.toString();
	}

	private void parsearJSON(String json) {
		Log.i(TAG, "threadID em parsearJSON: " + Thread.currentThread().getId());
		
		JSONArray jArray;
		ArrayList<Cerveja> cervejas = new ArrayList<Cerveja>();
		
		try {
			jArray = new JSONObject(json).getJSONArray("beers");
		
			int qtdDeCervejas = jArray.length();
			
			for(int i = 0; i < qtdDeCervejas; i++) {
				
				JSONObject jObjCerva = jArray.getJSONObject(i);
				Cerveja cerva = new Cerveja();
				
				cerva.setId(jObjCerva.getInt("id"));
				cerva.setNome(jObjCerva.getString("name"));
				cerva.setTeorAlcoolico(jObjCerva.getDouble("abv"));
				cerva.setDescricao(jObjCerva.getString("description"));
				cerva.setCervejaria(jObjCerva.getJSONObject("brewery").getString("name"));
				
				cervejas.add(cerva);
			}
			
			
		} catch (JSONException e) {
			Log.e(TAG, "Erro ao parsear JSON de cervejas.");
			e.printStackTrace();
			finish();
		}
		
		listarCervejas(cervejas);
	}

	@UiThread
	protected void listarCervejas(ArrayList<Cerveja> cervejas) {
		
		Log.i(TAG, "threadID em listarCervejas: " + Thread.currentThread().getId());
		
		this.cervejas = cervejas;
		
		if(cervejas.size() > 0) {
			ListaCervejasAdapter adapter = new ListaCervejasAdapter(this, cervejas);
			listView.setAdapter(adapter);
		} else {
			Log.w(TAG, "O array de cervejas veio vazio :(");
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(getApplicationContext(), CervejaActivity.class);
		intent.putExtra(CervejaActivity.CERVEJA, cervejas.get(position));
		startActivity(intent);
	}
}
