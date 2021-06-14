import java.util.Scanner;
import java.io.FileInputStream;

public class Grille
{
	private int nbLig;
	private int nbCol;
	private char[][] grille;

	private int niveau;
	private String path;
	private int pas;
	private boolean[] tabFini;
	private int posLig;
	private int posCol;
	private String dossier;
	private int numTheme;
	private char[] ctrlZ;
	private int nbElt;

	public Grille()
	{
		int nbLigne;
		int nbColonne;
		String ligne;
		
		this.path ="../defis/defi01.xsb";
		this.dossier = "../images/classique/";
		nbLigne=1;
		nbColonne=0;
		this.numTheme = 0 ;
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( this.path ) );
			
			ligne = sc.nextLine();
			nbColonne= ligne.length();
			
			while ( sc.hasNextLine() )
			{
				nbLigne++;
				
				sc.nextLine();
			}
			
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }
		
		this.nbLig  = nbLigne;
		this.nbCol  = nbColonne;
		this.grille = new char[this.nbLig][this.nbCol];

		this.pas	= 0;
		this.niveau = 1;
		this.tabFini = new boolean[21];
		for(int cpt=0; cpt<21; cpt++)
			tabFini[cpt] = false;
		this.ctrlZ = new char[10000];
		this.nbElt = 0;
		
		
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( this.path ) );
		
			for(int cptLig=0; cptLig<this.nbLig; cptLig++)
			{
				String line;
				line = sc.nextLine();
				for(int cptCol=0; cptCol<this.nbCol; cptCol++)
				{
					this.grille[cptLig][cptCol] = line.charAt(cptCol);
				}
			}
			sc.close();
			}
			catch (Exception e){ e.printStackTrace(); }
	}


	public void deplacer(char dir)
	{

		this.ctrlZ[this.nbElt] = dir;
		this.nbElt++;

		coordonee();

		mouvement(dir, this.posLig, this.posCol);

		this.pas++;
		
		
		
		testVictoire();
	}

	public void defiSuivant()
	{

		//Variables//
		String path, deb, fin;
		String niveauStr="";


		//Instructions//

		deb = "../defis/defi";					//Debut du chemin
		fin = ".xsb";							//Fin du chemin


		if (tabFini[this.niveau] == true)
		{
			this.niveau += 1;				//Incrémente le niveau de 1

			if (this.niveau >= 20){this.niveau = 1 ;}			//Si on est au dernier niveau on revient au premier
			niveauStr = String.format("%02d", this.niveau);	//Formate le numero de niveau pour avoir deux decimales
			this.path = deb + niveauStr + fin;		//Concaténation du chemin
			recommencer();
			this.ctrlZ = new char[10000];
			this.nbElt = 0;
			this.pas  = 0;
		}
		
	}

	
	public char getOrientation()
	{
		return ctrlZ[this.nbElt];
	}
	public void defiPrecedent()
	{

		//Variables//
		String path, deb, fin;
		String niveauStr="";
		int niveauTemp;

		//Instructions//

		deb = "../defis/defi";                                    //Debut du chemin
		fin = ".xsb";                                             //Fin du chemin

		niveauTemp = this.niveau;
		this.niveau -= 1;                                         //Déincrémente le niveau de 1

		if (this.niveau < 1 && tabFini[20-1]){this.niveau = 20 ;}
		else if (this.niveau < 1 ) {this.niveau=niveauTemp;}      //Si on est au premier niveau on va au dernier
		niveauStr = String.format("%02d", this.niveau);           //Formate le numero de niveau pour avoir deux decimales
		this.path = deb + niveauStr + fin;                        //Concaténation du chemin
		recommencer();
		this.ctrlZ = new char[10000];
		this.nbElt = 0;
		this.pas  = 0;

	}

	public void recommencer()
	{
		int nbLigne;
		int nbColonne;
		String ligne;
		
		nbLigne=1;
		nbColonne=0;
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( this.path ) );
			
			ligne = sc.nextLine();
			nbColonne= ligne.length();
			
			while ( sc.hasNextLine() )
			{
				nbLigne++;
				
				sc.nextLine();
			}
			
			sc.close();
		}
		catch (Exception e){ e.printStackTrace(); }

		this.grille = new char[nbLigne][nbColonne];
		this.nbLig  = nbLigne;
		this.nbCol  = nbColonne;
		
		try
		{
			Scanner sc = new Scanner ( new FileInputStream ( this.path ) );
		
			for(int cptLig=0; cptLig<this.nbLig; cptLig++)
			{
				String line;
				line = sc.nextLine();
				for(int cptCol=0; cptCol<this.nbCol; cptCol++)
				{
					this.grille[cptLig][cptCol] = line.charAt(cptCol);
				}
			}
			sc.close();
			}
			catch (Exception e){ e.printStackTrace(); }

		coordonee();
	}

	
	
	public String getMessage()
	{
		// variable //
		String message;

		//instruction//
		message = "Niveau : "+this.niveau;
		if(tabFini[this.niveau])
		{
			message +=" fini";
		}

		return message;
	}

	public int getNbPas(){ return this.pas;}
	public int getNbLigne(){ return this.nbLig;}
	public int getNbColonne(){ return this.nbCol;}
	public char getSymbole (int lig, int col){return this.grille[lig][col];}

	
	
	
	
	private void testVictoire()
	{
		/*variable*/
		int cptCaisse, cptLig,cptCol;
		cptCaisse=0;

		for(cptLig=0;cptLig<this.getNbLigne();cptLig++)
		{
			for(cptCol=0;cptCol<this.getNbColonne();cptCol++)
			{
				if(this.getSymbole(cptLig,cptCol)=='$')
					cptCaisse++;
			}
		}
		if (cptCaisse == 0)
			this.niveauFini();
	}
	
	
	
	
	
	private void niveauFini()
	{
		this.tabFini[this.niveau]=true;
	}

	
	
	
	private void mouvement(char dir, int posLig, int posCol)
	{
		/////////////
		//Variables//
		/////////////
		int posColA, posLigA, posColB, posLigB;
		boolean colision;

		////////////////
		//Instructions//
		////////////////
		
		posColA = posColB = posCol;
		posLigA = posLigB = posLig;
		
		switch(dir)
		{
			case 'N' ->{posLigA-=2; posLigB-=1;}
			case 'O' ->{posColA-=2; posColB-=1;}
			case 'S' ->{posLigA+=2; posLigB+=1;}
			case 'E' ->{posColA+=2; posColB+=1;}
		}

		colision = true ;
		
		
		////////////////////////////////////////////////////////////
		//Detection de Colisions rendant le deplacement impossible//
		////////////////////////////////////////////////////////////
		
		if (this.grille[posLigB][posColB] == '#'){colision = false;}                                        //Il y a un mur
		
		if (this.grille[posLigB][posColB] == '$' && this.grille[posLigA][posColA] == '#'){colision = false;}//Un mur bloque la caisse
		if (this.grille[posLigB][posColB] == '$' && this.grille[posLigA][posColA] == '*'){colision = false;}//Une caisse bloque la caisse
		if (this.grille[posLigB][posColB] == '$' && this.grille[posLigA][posColA] == '$'){colision = false;}//Une caisse bloque la caisse
		
		if (this.grille[posLigB][posColB] == '*' && this.grille[posLigA][posColA] == '#'){colision = false;}//Un mur bloque la caisse
		if (this.grille[posLigB][posColB] == '*' && this.grille[posLigA][posColA] == '*'){colision = false;}//Une caisse bloque la caisse
		if (this.grille[posLigB][posColB] == '*' && this.grille[posLigA][posColA] == '$'){colision = false;}//Une caisse bloque la caisse


		/////////////////////////////////////////////////////////
		//Deplacement en fonction des cas de figures rencontrer//
		/////////////////////////////////////////////////////////
		
		if (colision)
		{
			if (this.grille[posLigB][posColB] == '$' && this.grille[posLigA][posColA] == '.')		//Caisse vers depot
			{
				this.grille[posLigB][posColB] = '@';
				this.grille[posLigA][posColA] = '*';
			}
			else if (this.grille[posLigB][posColB] == '$' && this.grille[posLigA][posColA] == ' ')	//Caisse vers case vide
			{
				this.grille[posLigB][posColB] = '@';
				this.grille[posLigA][posColA] = '$';
			}
			else if (this.grille[posLigB][posColB] == '*' && this.grille[posLigA][posColA] == '.')  //Caisse sur depot vers depot
			{
				this.grille[posLigB][posColB] = '+';
				this.grille[posLigA][posColA] = '*';
			}
			else if (this.grille[posLigB][posColB] == '*')                                          //Caisse sur Depot vers case vide
			{
				this.grille[posLigB][posColB] = '+';
				this.grille[posLigA][posColA] = '$';
			}
			else if (this.grille[posLigB][posColB] == '.'){this.grille[posLigB][posColB] = '+';}    //Pousseur vers depot
			else if (this.grille[posLigB][posColB] == ' '){this.grille[posLigB][posColB] = '@';}    //pousseur vers case vide
			
			//Case d'ou part le pousseur//
			if (this.grille[posLig][posCol] == '+'){this.grille[posLig][posCol] = '.';}             //depot-pousseur devient depot
			else {this.grille[posLig][posCol] = ' ';}                                               //case vide
		}

	}
	
	
	
	///////////////////////////////////////////
	//Fonction de retour d'un coup en arriere//
	///////////////////////////////////////////
	public void retour()
	{
		if( this.nbElt > 0 )
		{
			this.nbElt--;
			recommencer();
			
			for(int cpt=0; cpt<this.nbElt; cpt++)
			{
				coordonee();
				mouvement(this.ctrlZ[cpt], this.posLig, this.posCol);
			}
			this.pas++;
		}
	}
	
	///////////////////////////////////////////
	//Fonction d'appel du repertoire du theme//
	///////////////////////////////////////////
	
	public String repertoire ()
	{
		String rep;
		rep = this.dossier;
		return rep;
	}
	
	///////////////////////////////////
	//Fonction de changement de theme//
	///////////////////////////////////
	
	public void changerTheme()
	{
		/////////////
		//Variables//
		/////////////
		int nbTheme;
		String [] theme;
		
		
		nbTheme = 4 ;
		this.numTheme ++ ;
		if (numTheme >= nbTheme){numTheme = 0;}
		
		theme = new String [nbTheme];
		
		theme [0] = "../images/classique/";
		theme [1] = "../images/geometrique/";
		theme [2] = "../images/link/";
		theme [3] = "../images/nature/";
		
		this.dossier = theme[this.numTheme];
		
	}

	private void coordonee()
	{
		for(int cptLig=0; cptLig<this.nbLig; cptLig++)
		{
			for(int cptCol=0; cptCol<this.nbCol; cptCol++)
			{
				if(grille[cptLig][cptCol] == '@' || grille[cptLig][cptCol] == '+' ) 
				{
					this.posCol = cptCol;
					this.posLig = cptLig;
				}
			}
		}
	}
}
