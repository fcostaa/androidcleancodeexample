package br.com.felipejade.annotationsexemplo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class CervejaActivityBackup extends SherlockFragmentActivity {

	public static final String CERVEJA = "CERVEJA";
	
	private Cerveja cerveja;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.cerveja);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Cerveja");
		
		Intent intent = getIntent();
		
		if(intent != null) {
			
			cerveja = intent.getParcelableExtra(CERVEJA);
			
			CervejaFragment frag = new CervejaFragment();
			Bundle args = new Bundle();
			args.putParcelable(CERVEJA, cerveja);
			frag.setArguments(args);
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.cerveja_detalhes, frag);
			transaction.commit();		
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == android.R.id.home) {
			finish();
		}
		
		return super.onOptionsItemSelected(item);
	}
}
