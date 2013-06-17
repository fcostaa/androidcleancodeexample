package br.com.felipejade.annotationsexemplo;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import br.com.felipejade.annotationsexemplo.adapters.ListaCervejasAdapter;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockListActivity;

public class ListarCervejasActivity extends SherlockListActivity {
	
	private ArrayList<Cerveja> cervejas;
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lista_cervejas);
		
		listView = (ListView) findViewById(android.R.id.list);
		
		baixarCervejas();
	}
	
	private void baixarCervejas() {
		
		cervejas = new ArrayList<Cerveja>();
		
		cervejas.add(new Cerveja("Brahma", 0.4));
		cervejas.add(new Cerveja("Antartica", 0.5));
		cervejas.add(new Cerveja("Skol", 0.45));
		
		listarCervejas(cervejas);
		
//		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//		        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//		        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//		        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//		        "Android", "iPhone", "WindowsMobile" };
//
//		    final ArrayList<String> list = new ArrayList<String>();
//		    for (int i = 0; i < values.length; ++i) {
//		      list.add(values[i]);
//		    }
//		
//		final StableArrayAdapter adapter = new StableArrayAdapter(this,
//		        android.R.layout.simple_list_item_1, list);
//		    listView.setAdapter(adapter);
	}
	
	private void listarCervejas(ArrayList<Cerveja> cervejas) {
		
		ListaCervejasAdapter adapter = new ListaCervejasAdapter(this, cervejas);
		listView.setAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(getApplicationContext(), CervejaActivity.class);
		intent.putExtra(CervejaActivity.CERVEJA, cervejas.get(position));
		startActivity(intent);
	}
}
