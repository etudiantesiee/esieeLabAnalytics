package fr.esiee.pic.analytics.domain;

import java.io.Serializable;

/**
 * Represente un artiste.
 * Classe immuable
 * 
 * @author etudiant
 *
 */
public class ArtistByFirstLetter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6651174365327769226L;

	/**
	 * Lettre initial du nom de l'artiste
	 */
	private String firstLetter;
	
	/**
	 * Nom de l'artiste
	 */
	private String artist;

	public ArtistByFirstLetter(String firstLetter, String artist) {
		super();
		this.firstLetter = firstLetter;
		this.artist = artist;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "ArtistByFirstLetter [firstLetter=" + firstLetter + ", artist="
				+ artist + "]";
	}
}
