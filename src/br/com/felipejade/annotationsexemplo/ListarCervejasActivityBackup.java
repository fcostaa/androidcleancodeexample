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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import br.com.felipejade.annotationsexemplo.adapters.ListaCervejasAdapter;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockListActivity;

public class ListarCervejasActivityBackup extends SherlockListActivity {
	
	private static final String TAG = ListarCervejasActivityBackup.class.getSimpleName();
	
	private ArrayList<Cerveja> cervejas;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lista_cervejas);
		
		listView = (ListView) findViewById(android.R.id.list);
		
		getSupportActionBar().setTitle("Listar Cervejas");
		
		baixarCervejas();
	}
	
	private void baixarCervejas() {
		
		BaixadorDeCervejas baixadorDeCervejas = new BaixadorDeCervejas();
		baixadorDeCervejas.execute();
	}
	
	private void listarCervejas(ArrayList<Cerveja> cervejas) {
		
		this.cervejas = cervejas;
		
		if(cervejas.size() > 0) {
			ListaCervejasAdapter adapter = new ListaCervejasAdapter(this, cervejas);
			listView.setAdapter(adapter);
		} else {
			Log.w(TAG, "O array de cervejas veio vazio :(");
		}
		
	}
	
	private void parsearJSON(String json) {
		
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(getApplicationContext(), CervejaActivity.class);
		intent.putExtra(CervejaActivity.CERVEJA, cervejas.get(position));
		startActivity(intent);
	}
	
	
	private class BaixadorDeCervejas extends AsyncTask<Void, Void, Object> {

		private String JSONCervejas; 
		
		@Override
		protected Object doInBackground(Void... params) {
			
			HttpURLConnection urlConnection = null;
			try {
				URL url = new URL("http://api.openbeerdatabase.com/v1/beers.json");
				urlConnection = (HttpURLConnection) url.openConnection();
//				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				
				readStream(urlConnection.getInputStream());
				
			} catch(IOException e) {
				e.printStackTrace();
				Log.e(TAG, "Erro ao baixar lista de cervejas");
			} finally {
				
				if(urlConnection != null)
					urlConnection.disconnect();
			}
			   
			return null;
		}
	
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			
			parsearJSON(JSONCervejas);
		}
		
		private void readStream(InputStream in) {

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
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Log.i("JADE", "Setando o valor no JSON do Async");
				JSONCervejas = strBuffer.toString();
			}
		}
	}
	
}
