package br.com.felipejade.annotationsexemplo;

import android.widget.TextView;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

import com.actionbarsherlock.app.SherlockFragment;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;

@EFragment(R.layout.cerveja_frag)
public class CervejaFragment extends SherlockFragment {

	private Cerveja cerveja;

	@ViewById(R.id.cerveja_nome)
	TextView nome;

	@ViewById(R.id.cerveja_teor)
	TextView teorAlcoolico;

	@ViewById(R.id.cerveja_cervejaria)
	TextView fabricante;

	@ViewById(R.id.cerveja_descricao)
	TextView descricao;

	@AfterViews
	protected void preencherCerveja() {
		cerveja = getArguments().getParcelable(CervejaActivity.CERVEJA);

		// Nome
		nome.setText(cerveja.getNome());

		// Teor
		teorAlcoolico.setText("Teor: " + cerveja.getTeorAlcoolico());

		// Cervejaria fabricante
		fabricante.setText(cerveja.getCervejaria());

		// Descrição
		descricao.setText(cerveja.getDescricao());

	}
}
