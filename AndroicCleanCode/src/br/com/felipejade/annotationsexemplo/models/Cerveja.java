
package br.com.felipejade.annotationsexemplo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cerveja implements Parcelable {

	private int id;
	private String nome;
	private double teorAlcoolico;
	private String cervejaria;
	private String descricao;
	
	public Cerveja() {
		super();
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCervejaria() {
		return cervejaria;
	}

	public void setCervejaria(String cervejaria) {
		this.cervejaria = cervejaria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeInt(id);
		dest.writeString(nome);
		dest.writeDouble(teorAlcoolico);
		dest.writeString(descricao);
		dest.writeString(cervejaria);
	}
	
	public static final Parcelable.Creator<Cerveja> CREATOR = new Parcelable.Creator<Cerveja>() {
		@Override
		public Cerveja createFromParcel(Parcel source) {
			
			Cerveja cerveja = new Cerveja();
			
			cerveja.setId(source.readInt());
			cerveja.setNome(source.readString());
			cerveja.setTeorAlcoolico(source.readDouble());
			cerveja.setDescricao(source.readString());
			cerveja.setCervejaria(source.readString());
			
			return cerveja;
		}

		@Override
		public Cerveja[] newArray(int size) {
			return new Cerveja[size];
		}
	};
}