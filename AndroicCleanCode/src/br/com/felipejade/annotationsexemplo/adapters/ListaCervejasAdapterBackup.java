package br.com.felipejade.annotationsexemplo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.felipejade.annotationsexemplo.R;
import br.com.felipejade.annotationsexemplo.models.Cerveja;

public class ListaCervejasAdapterBackup extends BaseAdapter {

	private ArrayList<Cerveja> cervejas;
	private Context context;
	
	public ListaCervejasAdapterBackup(Context context, ArrayList<Cerveja> cervejas) {
		this.cervejas = cervejas;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return cervejas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return cervejas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Cerveja cerva = cervejas.get(position);
		ViewHolder holder;
		
		View rowView;
		
		if(convertView == null || convertView.getTag() == null) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.lista_cervejas_celula, parent, false);
			
			holder = new ViewHolder(rowView);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
			rowView = convertView;
		}
		
		holder.nome.setText(cerva.getNome());
		holder.teor.setText("" + cerva.getTeorAlcoolico());
		
		rowView.setTag(holder);
		
		return rowView;
	}
	
	private class ViewHolder {
		
		TextView nome, teor;
		
		public ViewHolder(View v) {
			nome = (TextView) v.findViewById(R.id.cervejas_lista_nome);
			teor = (TextView) v.findViewById(R.id.cervejas_lista_teor);
		}
	}
	
}
