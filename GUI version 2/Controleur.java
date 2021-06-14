import ihmgui.*;

public class Controleur extends Controle/* A compléter */
{
	private FrameGrille frame;        // Classe Vue
	private Grille      metier;       // Classe Metier // inchangé par rapport au mode CUI
	private char orientation;
	

	public Controleur()
	{
		this.metier  = new Grille ();             // instanciation de la partie  Metier
		this.frame   = new FrameGrille ( this );  // instanciation de la fenêtre graphique

		frame.setSize     ( 1430, 800        );
		frame.setLocation ( 1,  1            );
		frame.setTitle    ( "Sokoban"        );
		frame.setVisible  ( true             );
	}


	/*--------------------------------------------------------*/
	/* Méthodes pour Construire l'IHM                         */
	/*--------------------------------------------------------*/

	/* Création des boutons  */
	public String setBouton(int numBtn)
	{
		String lib;


		switch ( numBtn )
		{
			case 0 : lib = "Defi Precedent";           break;
			case 1 : lib = "Defi Suivant";             break;
			case 2 : lib = "Recommencer";              break;
			case 3 : lib = "Changer theme";            break;
			default: lib = null;                      
		}

		return lib;
	}

	/* Création des Labels */
	public String setLabel (int numLbl)
	{
		String lib;

		switch ( numLbl )
		{
			case 0 : lib = "Pas :";        break;
			case 1 : lib = "Message :";        break;
			default: lib = null;					// cette dernière ligne est importante, elle met fin à la contruction des labels
		}

		return lib;
	}

	public int     setNbLigne        () { return this.metier.getNbLigne  (); }
	public int     setNbColonne      () { return this.metier.getNbColonne(); }
	public boolean setNumLigneColonne() { return false;                      }
	public int     setLargeurImg     () { return  48;                        }
	public int     setLargeurLabel   () { return 260;                        }
	public int     setMargeHaute     () { return -20;                        }
	public int     setMargeGauche    () { return -20;                        }
	public int     setMargeImageBtn  () { return 750;                        }
	public int     setMargeBtnLabel  () { return   2;                        }


	/*--------------------------------------------------------*/
	/* Méthodes déclenchées suite à une Action sur l'IHM      */
	/*--------------------------------------------------------*/

	/* Interception d'une touche */
	public void jouer (String touche)
	{
		if ( touche.equals ( "FL-H" ) ) { this.metier.deplacer ( 'N' ) ; this.orientation = 'N';}
		if ( touche.equals ( "FL-G" ) ) { this.metier.deplacer ( 'O' ) ; this.orientation = 'O';}
		if ( touche.equals ( "FL-B" ) ) { this.metier.deplacer ( 'S' ) ; this.orientation = 'S';}
		if ( touche.equals ( "FL-D" ) ) { this.metier.deplacer ( 'E' ) ; this.orientation = 'E';}
		if ( touche.equals ( "CR-Z" ) ) 
		{
			this.metier.retour() ;
			this.orientation = this.metier.getOrientation();
		}
		this.frame.majIHM();
	}


	/* Clique sur un boutons de commande */
	public void bouton  (int action            )
	{
		if ( action == 0 ) { this.metier.defiPrecedent ();  this.frame.majIHM(); }
		if ( action == 1 ) { this.metier.defiSuivant   ();  this.frame.majIHM(); }
		if ( action == 2 ) { this.metier.recommencer   ();  this.frame.majIHM(); }
		if ( action == 3 ) { this.metier.changerTheme  ();  this.frame.majIHM(); }
	}

	/*-----------------------------------------------*/
	/* Méthodes déclenchées pour redessinner l'IHM   */
	/*-----------------------------------------------*/

	/* Actualisation des Labels */
	public String  setTextLabel      (int numLbl)
	{
		switch ( numLbl )
		{
			case 0 : return String.format("%02d",this.metier.getNbPas());
			case 1 : return this.metier.getMessage ();
			default:;
			
		}
		return null;
		
	}
	

	public String setFondGrille ()
	{
		String imgFond;
		String rep = this.metier.repertoire();
		
		imgFond = rep + "fond.jpg";
		return imgFond;
	}
	
	
	/* affecte l'imagette située en ligne colonne                 */
	/* pour le moment on ne gèrera qu'une seule couche (couche=0) */
	public String setImage ( int ligne, int colonne, int couche)
	{
		char   symbole;
		String rep = this.metier.repertoire();
		String sImage=null;
		symbole = this.metier.getSymbole (ligne, colonne);

		if ( couche == 0 )
		{
			

			if      ( symbole == '#' ) sImage = rep + "mur.png";
			else if ( symbole == ' ' ) sImage = rep + "vide.png";
			else if ( symbole == '$' ) sImage = rep + "objet.png";
			else if ( symbole == '.' ) sImage = rep + "rangement.png";

			else if ( symbole == '*' ) sImage = rep + "objet_rangement.png";
			else if ( symbole == 'x' ) sImage = null;
			
			if ( symbole == '@' || symbole == '+')
				if ( this.orientation == 'N' ) { sImage = rep + "pousseur_nord.png";}
				else if ( this.orientation == 'O' ) { sImage = rep + "pousseur_ouest.png";}
				else if ( this.orientation == 'S' ) { sImage = rep + "pousseur_sud.png";}
				else if ( this.orientation == 'E' ) { sImage = rep + "pousseur_est.png";}
				else {sImage = rep + "pousseur_sud.png";}
			
		}

		return sImage;
	}


	public static void main(String[] a)
	{
		new Controleur();
	}
}

