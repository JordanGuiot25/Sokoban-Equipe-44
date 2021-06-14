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

	public Grille()
	{
		int nbLigne;
		int nbColonne;
		String ligne;
		
		this.path ="../defis/defi01.xsb";
		
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
		
		this.nbLig  = nbLigne;
		this.nbCol  = nbColonne;
		this.grille = new char[this.nbLig][this.nbCol];
		this.pas	= 0;
		this.niveau = 1;
		this.tabFini = new boolean[21];
		for(int cpt=0; cpt<21; cpt++)
			tabFini[cpt] = false;
		
		
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
		int posLig, posCol;

		posLig = posCol = 0;

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
			this.pas  = 0;
			recommencer();
		}
		
	}

	
	
	public void defiPrecedent()
	{

		//Variables//
		String path, deb, fin;
		String niveauStr="";
		int niveauTemp;

		//Instructions//

		deb = "../defis/defi";					//Debut du chemin
		fin = ".xsb";							//Fin du chemin

		niveauTemp = this.niveau;
		this.niveau -= 1;						 //Déincrémente le niveau de 1

		if (this.niveau < 1 && tabFini[20-1]){this.niveau = 20 ;}
		else if (this.niveau < 1 ) {this.niveau=niveauTemp;}			//Si on est au premier niveau on va au dernier
		niveauStr = String.format("%02d", this.niveau);	//Formate le numero de niveau pour avoir deux decimales
		this.path = deb + niveauStr + fin;		//Concaténation du chemin
		this.pas  = 0;
		recommencer();
		

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

		for(int cptLig=0; cptLig<this.nbLig; cptLig++)
		{
			for(int cptCol=0; cptCol<this.nbCol; cptCol++)
			{
				if (this.grille[cptLig][cptCol] == '@' || this.grille[cptLig][cptCol] == '+')
				{
					this.posLig = cptLig;
					this.posCol = cptCol;
				}
			}
		}
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
		int posColA, posLigA, posColB, posLigB;
		boolean colision;

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
		
		if (this.grille[posLigB][posColB] == '#'){colision = false;}										//Il y a un mur
		
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
			else if (this.grille[posLigB][posColB] == '*' && this.grille[posLigA][posColA] == '.')	//Caisse sur depot vers depot
			{
				this.grille[posLigB][posColB] = '+';
				this.grille[posLigA][posColA] = '*';
			}
			else if (this.grille[posLigB][posColB] == '*')											//Caisse sur Depot vers case vide
			{
				this.grille[posLigB][posColB] = '+';
				this.grille[posLigA][posColA] = '$';
			}
			else if (this.grille[posLigB][posColB] == '.'){this.grille[posLigB][posColB] = '+';}	//Pousseur vers depot
			else if (this.grille[posLigB][posColB] == ' '){this.grille[posLigB][posColB] = '@';}	//pousseur vers case vide
			
			//Case d'ou part le pousseur//
			if (this.grille[posLig][posCol] == '+'){this.grille[posLig][posCol] = '.';}				//depot-pousseur devient depot
			else {this.grille[posLig][posCol] = ' ';}												//case vide
		}

	}
	

}
