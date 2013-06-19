package br.com.felipejade.annotationsexemplo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;

@EActivity(R.layout.cerveja)
public class CervejaActivity extends SherlockFragmentActivity {

	public static final String CERVEJA = "CERVEJA";

	private Cerveja cerveja;

	@AfterViews
	protected void init() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Beer");

		Intent intent = getIntent();

		if (intent != null) {

			cerveja = intent.getParcelableExtra(CERVEJA);

			CervejaFragment frag = new CervejaFragment_();
			Bundle args = new Bundle();
			args.putParcelable(CERVEJA, cerveja);
			frag.setArguments(args);

			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.add(R.id.cerveja_detalhes, frag);
			transaction.commit();
		}
	}

	@OptionsItem(android.R.id.home)
	protected void back() {
		finish();
	}
}
