package br.com.felipejade.annotationsexemplo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockFragment;

public class CervejaFragment extends SherlockFragment {

	private Cerveja cerveja;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		cerveja = getArguments().getParcelable(CervejaActivity.CERVEJA);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.cerveja_frag, container, false);
		return preencherCerveja(v);
	}

	private View preencherCerveja(View view) {
		
		TextView nome = (TextView) view.findViewById(R.id.cerveja_nome);
		nome.setText(cerveja.getNome());
		
		return view;
	}
}
