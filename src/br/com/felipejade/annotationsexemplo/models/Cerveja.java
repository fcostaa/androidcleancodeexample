
package br.com.felipejade.annotationsexemplo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cerveja implements Parcelable {

	private String nome;
	private double teorAlcoolico;
	
	public Cerveja() {}
	
	public Cerveja(String nome, double teorAlcoolico) {
		super();
		this.nome = nome;
		this.teorAlcoolico = teorAlcoolico;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getTeorAlcoolico() {
		return teorAlcoolico;
	}
	public void setTeorAlcoolico(double teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nome);
		dest.writeDouble(teorAlcoolico);
	}
	
	
	public static final Parcelable.Creator<Cerveja> CREATOR = new Parcelable.Creator<Cerveja>() {

		@Override
		public Cerveja createFromParcel(Parcel source) {
			
			String nome = source.readString();
			double teor = source.readDouble();
			Cerveja cerveja = new Cerveja(nome, teor);
			return cerveja;
		}

		@Override
		public Cerveja[] newArray(int size) {
			return new Cerveja[size];
		}
	};
}